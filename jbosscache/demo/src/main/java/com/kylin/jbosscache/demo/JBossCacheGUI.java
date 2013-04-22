package com.kylin.jbosscache.demo;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.util.JConsole;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.cache.Cache;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;
import org.jboss.cache.lock.TimeoutException;
import org.jboss.cache.notifications.annotation.CacheListener;
import org.jboss.cache.notifications.annotation.CacheStarted;
import org.jboss.cache.notifications.annotation.CacheStopped;
import org.jboss.cache.notifications.annotation.NodeCreated;
import org.jboss.cache.notifications.annotation.NodeEvicted;
import org.jboss.cache.notifications.annotation.NodeModified;
import org.jboss.cache.notifications.annotation.NodeRemoved;
import org.jboss.cache.notifications.annotation.ViewChanged;
import org.jboss.cache.notifications.event.Event;
import org.jboss.cache.notifications.event.NodeEvent;
import org.jboss.cache.notifications.event.NodeModifiedEvent;
import org.jboss.cache.notifications.event.ViewChangedEvent;
import org.jboss.cache.util.CachePrinter;
import org.jgroups.Address;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@CacheListener
public class JBossCacheGUI extends JFrame implements WindowListener, TreeSelectionListener, TableModelListener {
	
	private static final long serialVersionUID = -1242167331988194987L;
	
	private static final int KEY_COL_WIDTH = 20;
	private static final int VAL_COL_WIDTH = 300;

	private transient JBossCacheDelegate cacheModelDelegate;
	private transient Cache cache;
	private JBossCacheLogger cacheLogger;
   
	private transient Log log = LogFactory.getLog(getClass());
   
	private DefaultTreeModel tree_model = null;
	private JTree jtree = null;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private JTable table = new JTable(tableModel);
	private JPanel tablePanel = null;
	private JPopupMenu operationsPopup = null, dataModificationsPopup = null;
	private JPanel mainPanel;
   
	private JBossCacheGUI.DisplayNode myNodeRoot = new JBossCacheGUI.DisplayNode(Fqn.SEPARATOR);
  
	private transient Node root;
	private transient Node selected_node = null;
	private List<Address> membership = new LinkedList<Address>();
	private Address coordinator = null;
   
	private TransactionManager tx_mgr = null;
	private transient Transaction tx = null;
   
   
	private boolean useConsole = false;
	JConsole bshConsole;

   /**
    * Run any work that happens in this interface in a separate thread.  This is good practise. Unless there's a
    * resource-managemenet requirement, such as in an application server, newCachedThreadPool is a good option as it
    * provides better queuing performance.
    */
	private transient Executor executor;

	public JBossCacheGUI(JBossCacheDelegate cacheDelegate, boolean useConsole, boolean debugCache) throws Exception {
	   
		executor = Executors.newCachedThreadPool();

		addNotify();
		this.useConsole = useConsole;
		tree_model = new DefaultTreeModel(new JBossCacheGUI.DisplayNode(Fqn.ROOT.toString()));
		jtree = new JTree(tree_model);
		
		Icon closedIcon = new ImageIcon(getClass().getClassLoader().getResource("close.gif"));
		Icon openedIcon = new ImageIcon(getClass().getClassLoader().getResource("close.gif"));
		Icon leafIcon = new ImageIcon(getClass().getClassLoader().getResource("leaf.gif"));
		
		DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();
	    render.setClosedIcon(closedIcon);
	    render.setOpenIcon(openedIcon);
	    render.setLeafIcon(leafIcon);
	    jtree.setCellRenderer(render);
		jtree.setDoubleBuffered(true);
		jtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		JScrollPane scroll_pane = new JScrollPane(jtree);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(scroll_pane, BorderLayout.CENTER);

		addWindowListener(this);

		tableModel.setColumnIdentifiers(new String[] { "Name", "Value" });
		tableModel.addTableModelListener(this);

		// add a mouse listener to the table model for delete and insert rows.
		MouseListener dataMouseListener = new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				if (rightClick(e.getModifiers())) {
					dataModificationsPopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		};

		setTableColumnWidths();

		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
		tablePanel.add(table, BorderLayout.CENTER);

		table.addMouseListener(dataMouseListener);

		mainPanel.add(tablePanel, BorderLayout.SOUTH);
		JSplitPane contentPanel = null;
		if (useConsole) {
			String welcomeMessage = getWelcomeMessage();

			bshConsole = new JConsole();
			Interpreter interpreter = new Interpreter(bshConsole);

			configureInterpreter(interpreter, cacheDelegate);

			interpreter.println(welcomeMessage);
        
			interpreter.setShowResults(!interpreter.getShowResults());// show() in beanShell
			System.setOut(bshConsole.getOut());
			System.setErr(bshConsole.getErr());
			Thread t = new Thread(interpreter);
			t.start();

			contentPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainPanel, bshConsole);
			getContentPane().add(contentPanel);
		} else {
			getContentPane().add(mainPanel);
		}

		jtree.addTreeSelectionListener(this);// REVISIT

		MouseListener ml = new MouseAdapter() {
			
			public void mouseClicked(final MouseEvent e) {
				
				if (log.isTraceEnabled()) {
					log.trace("clicked GUI");
				}

				final int selRow = jtree.getRowForLocation(e.getX(), e.getY());
				final TreePath selPath = jtree.getPathForLocation(e.getX(), e.getY());

				if (selRow != -1) {
					if (log.isTraceEnabled()) {
						log.trace("clicked on node in GUI");
					}

					Runnable r = new Runnable() {
						public void run() {
							try {
								selected_node = getNode(selPath.getPath());
								jtree.setSelectionPath(selPath);

								if (rightClick(e.getModifiers())) {
									operationsPopup.show(e.getComponent(), e.getX(), e.getY());
								}
							} catch (TimeoutException te) {
								String message = "Unable to update GUI due to a timeout trying to acquire lock on a node."
										+ "This might be due to clicking on a node which is currently locked by an ongoing "
										+ "transaction: ";
								printAndLogStacktrace(message, te);
							} catch (Exception e) {
								printAndLogStacktrace("Updating GUI failed: ", e);
							}
						}
					};
					executor.execute(r);
					
//					System.out.println("jtree mouselistener, selected_node = " + selected_node);
				}
			}
		};

		jtree.addMouseListener(ml);

		createMenus();
		setLocation(50, 50);
		setSize(800, 600);

		// make sure we set the cache BEFORE initialising this!!
		setCacheModelDelegate(cacheDelegate);
		
		// add JBossCacheGUILogger
		cacheLogger = new JBossCacheLogger(cache, debugCache);
		
		init();
		setVisible(true);
		setResizable(true);
		tree_model.setRoot(myNodeRoot);
		tree_model.reload();

		if (useConsole) {
         //has to be called after setVisible() otherwise no effect
         contentPanel.setDividerLocation(0.65);
		}
	}

	public void setCacheModelDelegate(final JBossCacheDelegate cacheModelDelegate) {
		
		this.cacheModelDelegate = cacheModelDelegate;
		
		if (this.cacheModelDelegate != null) {
			
			this.cache = this.cacheModelDelegate.getGenericCache();
			
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					cache.stop();
				}
			});

			cache.addCacheListener(this);
			root = cache.getRoot();
			setTitle("JBoss Cache GUI: Local Address=" + getLocalAddress());
			tx_mgr = cache.getConfiguration().getRuntimeConfig().getTransactionManager();
			populateTree();
			
		} else {
			
			setTitle("Cache undefined");
			
			if (tree_model != null) {
				myNodeRoot = new JBossCacheGUI.DisplayNode(Fqn.SEPARATOR);
				tree_model.setRoot(myNodeRoot);
				tree_model.reload();

			}
			
			if (tableModel != null) {
				clearTable();
			}
		}
	}

   /**
    * Checks whether a click event is considered a "right-click"
    *
    * @param modifiers mouse event mods
    * @return true if deemed a right click
    */
	private boolean rightClick(int modifiers) {
		// the simple right click case
		if (modifiers == InputEvent.BUTTON3_MASK)
			return true;
		// more complex on Mac OS X where a ctrl-click is deemed the same as a
		// right click
		return modifiers == InputEvent.BUTTON1_MASK + InputEvent.CTRL_MASK;
	}

	public void windowClosed(WindowEvent event) {
	}

	public void windowDeiconified(WindowEvent event) {
	}

	public void windowIconified(WindowEvent event) {
	}

	public void windowActivated(WindowEvent event) {
	}

	public void windowDeactivated(WindowEvent event) {
	}

	public void windowOpened(WindowEvent event) {
	}

	public void windowClosing(WindowEvent event) {
		stopGui();
	}


	public void tableChanged(TableModelEvent evt) {
		
		if (log.isTraceEnabled()) {
			log.trace("table contents changed, event type is: " + evt.getType());
		}

		int row, col;
		String key, val;

		if (evt.getType() == TableModelEvent.UPDATE) {
			
			row = evt.getFirstRow();
			col = evt.getColumn();

			key = (String) tableModel.getValueAt(row, col == 0 ? 0 : col - 1);
			val = (String) tableModel.getValueAt(row, col == 0 ? 1 : col);

			if (key != null && val != null && !isPlaceHolder(key) && !isPlaceHolder(val)) {
				
				try {
					if (log.isTraceEnabled()) {
						log.trace("updating node: " + selected_node + " with new values [k=" + key + ",v=" + val + "]");
					}

					selected_node.put(key, val);
					Map<String, String> data = selected_node.getData();
					populateTable(data);
				} catch (Exception e) {
					printAndLogStacktrace("Changing table failed: ", e);
				}
			}
		}
	}


	public void valueChanged(final TreeSelectionEvent evt) {
		
		if (log.isTraceEnabled()) {
			log.trace("node was selected in GUI: " + evt.getPath());
		}

		Runnable r = new Runnable() {
			
			public void run() {
				try {
					Map<String, String> data;
					selected_node = getNode(evt.getPath().getPath());
					if (selected_node != null) {
						data = selected_node.getData();
						if (data != null) {
							mainPanel.add(tablePanel, BorderLayout.SOUTH);
							populateTable(data);
							validate();
						} else {
							clearTable();
							mainPanel.remove(tablePanel);
							validate();
						}
					}
				} catch (TimeoutException te) {
					String message = "Unable to update GUI due to a timeout trying to acquire lock on a node."
							+ "This might be due to clicking on a node which is currently locked by an ongoing "
							+ "transaction: ";
					printAndLogStacktrace(message, te);
				} catch (Exception e) {
					printAndLogStacktrace("Updating GUI failed: ", e);
				}
			}
		};
		executor.execute(r);
	}

	protected DefaultTableModel getTableModel() {
		return tableModel;
	}

	protected String getWelcomeMessage() {
		
		return "Welcome to the BeanShell console.\n\n" +
            "This console gives you a direct shell interface to the GUI above and allows you to manipulate the cache directly. " +
            "Some of the variables initialised in this shell session are:\n\n" +
            "// an instance of org.jboss.cache\n" +
            "    Cache<String, String> cache;\n" +
            "// a reference to the root node\n" +
            "    Node<String, String> root;\n" +
            "// the transaction manager registered with the cache\n" +
            "    TransactionManager transactionManager;\n";
	}

	protected void configureInterpreter(Interpreter interpreter, JBossCacheDelegate cacheDelegate) throws EvalError {
		interpreter.getNameSpace().importPackage("org.jboss.cache");
		interpreter.getNameSpace().importPackage("org.jboss.cache.transaction");
		interpreter.set("cache", cacheDelegate.getGenericCache());
		interpreter.set("root", cacheDelegate.getGenericCache().getRoot());
		interpreter.set("transactionManager", cacheDelegate.getGenericCache().getConfiguration().getRuntimeConfig().getTransactionManager());
	}

   /* ------------------ CacheListener interface ------------ */

	@CacheStarted
	@CacheStopped
	public void cacheStartStopEvent(Event e) {
		cacheLogger.log(e);
	}
	
	@NodeCreated
//	@NodeLoaded
	public void nodeCreated(NodeEvent e) {
		
		cacheLogger.log(e);
		
      /* Updating the GUI upon node creation should be done sequentially to avoid concurrency issues when
      adding multiple nodes at the same time, for example for PojoCache, where attaching a pojo creates several nodes.
      Creating several visual nodes in paralell and trying to scroll to them is not a thread safe operation */
		if (e.isPre()) {
			
			JBossCacheGUI.DisplayNode n = myNodeRoot.add(e.getFqn());
			
			if (n != null) {
				tree_model.setRoot(myNodeRoot);
				tree_model.reload();
				jtree.scrollPathToVisible(new TreePath(n.getPath()));
			}
		}
	}

	@NodeModified
	public void nodeModified(final NodeModifiedEvent e) {
		
		cacheLogger.log(e);
		
		Runnable r = new Runnable() {
			
			public void run() {
				if (e.isPre() && selected_node != null && selected_node.getFqn().equals(e.getFqn())) {
					
					clearTable();
					populateTable(selected_node.getData());
				}
			}
		};
      executor.execute(r);
	}

	@NodeRemoved
	@NodeEvicted
	public void nodeRemoved(final NodeEvent e) {
		
		cacheLogger.log(e);
		
		if (log.isTraceEnabled()) {
			log.trace("node removed, updating GUI");
		}

		Runnable r = new Runnable() {
			
			public void run() {
				if (e.isPre()) {
					JBossCacheGUI.DisplayNode n;

					n = myNodeRoot.findNode(e.getFqn());
					if (n != null) {
						n.removeAllChildren();
						n.removeFromParent();
						tree_model.setRoot(myNodeRoot);
						tree_model.reload();
					}
				}
			}
		};
		executor.execute(r);
	}

	@ViewChanged
	public void viewChange(final ViewChangedEvent e) {
		
		cacheLogger.log(e);
		
		Runnable r = new Runnable() {
			public void run() {
				List<Address> mbrship;
				if (e.getNewView() != null && (mbrship = e.getNewView().getMembers()) != null) {
					membership.clear();
					membership.addAll(mbrship);
					coordinator = mbrship.get(0);
				}
			}
		};
		
		executor.execute(r);
	}

   /* ---------------- End of CacheListener interface -------- */

   /*----------------- Runnable implementation to make View change calles in AWT Thread ---*/

	public void run() {
	}

   /* ----------------------------- Private Methods ---------------------------------- */

   /**
    * Fetches all data from underlying cache model and display it graphically
    */
	private void init() {
		
		List<Address> mbrship;

		mbrship = getMembers();
		if (mbrship != null && mbrship.size() > 0) {
			membership.clear();
			membership.addAll(mbrship);
			coordinator = mbrship.get(0);
		}
   }


   /**
    * Fetches all data from underlying cache model and display it graphically
    */
	private void populateTree() {
		addGuiNode(Fqn.ROOT);
	}


   /**
    * Recursively adds GUI nodes starting from fqn
    */
	private void addGuiNode(Fqn fqn) {
		
		Set<Object> children;

		if (fqn == null) {
			return;
		}

		// 1 . Add myself
		myNodeRoot.add(fqn);

		// 2. Then add my children
		children = cache.getRoot().getChild(fqn).getChildrenNames();
		if (children != null) {
			for (Object child_name : children) {
				addGuiNode(Fqn.fromRelativeElements(fqn, (String) child_name));
			}
		}
	}

	private Node getNode(Object[] path) {
		
		Fqn fqnToPath;
		
		if (path.length == 0) {
			fqnToPath = Fqn.root();
		}
		
		List<Object> elements = Arrays.asList(path);
		StringBuffer sb  = new StringBuffer();
		for(Object obj : elements) {
			JBossCacheGUI.DisplayNode node = (JBossCacheGUI.DisplayNode) obj;
			if(node.toString().equals(Fqn.SEPARATOR)) {
				sb.append(Fqn.SEPARATOR);
			} else {
				sb.append(node.toString());
				sb.append(Fqn.SEPARATOR);
			}
		}
		fqnToPath = Fqn.fromString(sb.toString());
		if (root.hasChild(fqnToPath)) {
			return root.getChild(fqnToPath);
		} else {
			/*
			 * No implicit creation, otherwise removing GUI selected nodes will
			 * be recreated
			 */
			return null;
		}
	}

	protected void clearTable() {
		
		int num_rows = table.getRowCount();

		if (num_rows > 0) {
			
			for (int i = 0; i < num_rows; i++) {
				tableModel.removeRow(0);
			}
			
			tableModel.fireTableRowsDeleted(0, num_rows - 1);
         
			repaint();
		}
	}


	protected void populateTable(Map<String, String> data) {
		
		String key;
		String val;
		int num_rows;
		clearTable();
		
		if (data == null){
			return;
		}
		
		num_rows = data.size();

		if (num_rows > 0) {
         // sort this according to the natural sort order of keys
			TreeMap<String, String> sortedMap = new TreeMap<String, String>(data);

			for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
				key = entry.getKey();
				val = entry.getValue();
				tableModel.addRow(new Object[]{key, val});
			}
			tableModel.fireTableRowsInserted(0, num_rows - 1);
			validate();
		}
	}

	private void setTableColumnWidths() {
		table.sizeColumnsToFit(JTable.AUTO_RESIZE_NEXT_COLUMN);
		TableColumn column;
		column = table.getColumnModel().getColumn(0);
		column.setMinWidth(KEY_COL_WIDTH);
		column.setPreferredWidth(KEY_COL_WIDTH);
		column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(VAL_COL_WIDTH);
   }

	private void createMenus() {
		JMenuBar menubar = new JMenuBar();
		JMenu operationsMenu = new JMenu("Operations");
		JBossCacheGUI.AddNodeAction addNode = new JBossCacheGUI.AddNodeAction();
		addNode.putValue(AbstractAction.NAME, "Add to this node");
		JBossCacheGUI.LoadAction load_action = new JBossCacheGUI.LoadAction();
		load_action.putValue(AbstractAction.NAME, "Load from the CacheLoader");
		JBossCacheGUI.RemoveNodeAction removeNode = new JBossCacheGUI.RemoveNodeAction();
		removeNode.putValue(AbstractAction.NAME, "Remove this node");
		JBossCacheGUI.EvictAction evict_action = new JBossCacheGUI.EvictAction();
		evict_action.putValue(AbstractAction.NAME, "Evict from the Cache");
		JBossCacheGUI.AddModifyDataForNodeAction addModAction = new JBossCacheGUI.AddModifyDataForNodeAction();
		addModAction.putValue(AbstractAction.NAME, "Add/Modify data");
		JBossCacheGUI.PrintLockInfoAction print_locks = new JBossCacheGUI.PrintLockInfoAction();
		print_locks.putValue(AbstractAction.NAME, "Print lock information" + (useConsole ? "" : " (stdout)"));
		JBossCacheGUI.ExitAction exitAction = new JBossCacheGUI.ExitAction();
		exitAction.putValue(AbstractAction.NAME, "Exit");
		JBossCacheGUI.StartTransaction start_tx = new JBossCacheGUI.StartTransaction();
		start_tx.putValue(AbstractAction.NAME, "Start TX");
		JBossCacheGUI.CommitTransaction commit_tx = new JBossCacheGUI.CommitTransaction();
		commit_tx.putValue(AbstractAction.NAME, "Commit TX");
		JBossCacheGUI.RollbackTransaction rollback_tx = new JBossCacheGUI.RollbackTransaction();
		rollback_tx.putValue(AbstractAction.NAME, "Rollback TX");

		operationsMenu.add(addNode);
		operationsMenu.add(load_action);
		operationsMenu.add(removeNode);
		operationsMenu.add(evict_action);
		operationsMenu.add(addModAction);
		operationsMenu.add(print_locks);
		operationsMenu.add(start_tx);
		operationsMenu.add(commit_tx);
		operationsMenu.add(rollback_tx);
		operationsMenu.add(exitAction);
		menubar.add(operationsMenu);
		setJMenuBar(menubar);

		operationsPopup = new JPopupMenu();
		operationsPopup.add(addNode);
		operationsPopup.add(load_action);
		operationsPopup.add(evict_action);
		operationsPopup.add(removeNode);
		operationsPopup.add(addModAction);

		JBossCacheGUI.InsertRowAction insertRow = new JBossCacheGUI.InsertRowAction();
		insertRow.putValue(AbstractAction.NAME, "Insert New Row");
		JBossCacheGUI.RemoveLastRowAction removeLastRow = new JBossCacheGUI.RemoveLastRowAction();
		removeLastRow.putValue(AbstractAction.NAME, "Remove Last Row");

		dataModificationsPopup = new JPopupMenu();
		dataModificationsPopup.add(insertRow);
		dataModificationsPopup.add(removeLastRow);
	}

	private Object getLocalAddress() {
		try {
			return cache.getLocalAddress();
		} catch (Throwable t) {
			log.error("JBossCacheGUI.getLocalAddress(): ", t);
			return null;
		}
	}

	private void load(Fqn fqn) {
		try {
			// this will cause the cache to load the relevant node from a cache
			// loader.
			cache.getRoot().getChild(fqn);
		} catch (Throwable t) {
			log.error("JBossCacheGUI.load(): " + t);
		}
	}

	private List<Address> getMembers() {
		try {
			return new ArrayList<Address>(cache.getMembers());
		} catch (Throwable t) {
			log.error("TreeCacheGui.getMembers(): ", t);
			return null;
		}
	}

	private String[] getRowPlaceHolderData() {
		
		Collection keys = extractKeys(tableModel.getDataVector());
		// try all place holders

		int count = 0;
		String placeHolderKey = "{ --- Add Key --- }";
		
		while (keys.contains(placeHolderKey)) {
			count++;
			placeHolderKey = "{ --- Add Key " + count + " --- }";
		}

		String placeHolderValue = "{ --- Add Value " + (count == 0 ? "" : count) + " --- }";
		return new String[] { placeHolderKey, placeHolderValue };
	}

	private boolean isPlaceHolder(String s) {
		if (s.startsWith("{ --- Add Key ") && s.endsWith(" --- }"))
			return true;
		if (s.startsWith("{ --- Add Value ") && s.endsWith(" --- }"))
			return true;
		return false;
	}

	private Collection extractKeys(Vector<Vector> v) {
		// very odd data structure. Entire table is represented as a Vector.
		// Each row (element in the Vector) is a Vector of 2 elements (key and
		// value)
		List l = new LinkedList();
		for (Vector row : v) {
			// just add keys
			l.add(row.get(0));
		}
		return l;
	}

	private void printAndLogStacktrace(String message, Throwable t) {
		t.printStackTrace();
		log.error(message, t);
	}

   /* -------------------------- End of Private Methods ------------------------------ */

   /*----------------------- Actions ---------------------------*/

	public void stopGui() {
		if (cache != null) {
			try {
				cache.stop();
				cache.destroy();
				cache = null;
			} catch (Throwable t) {
				printAndLogStacktrace("Stopping and destroying cache failed: ", t);
			}
		}
		dispose();
		System.exit(0);
	}

	class ExitAction extends AbstractAction {
		
		private static final long serialVersionUID = -5364163916172148038L;

		public void actionPerformed(ActionEvent e) {
			stopGui();
		}
	}

	class InsertRowAction extends AbstractAction {
		
		private static final long serialVersionUID = 7084928639244438800L;

		public void actionPerformed(ActionEvent e) {
			tableModel.addRow(getRowPlaceHolderData());
		}
	}

	class RemoveLastRowAction extends AbstractAction {
		
		private static final long serialVersionUID = 7084928639244438800L;

		public void actionPerformed(ActionEvent e) {
			int lastRow = tableModel.getRowCount() - 1;
			if (lastRow > -1) {
				String keyToRemove = (String) tableModel.getValueAt(lastRow, 0);
				tableModel.removeRow(lastRow);
				selected_node.remove(keyToRemove);
			}
		}
	}

	class AddNodeAction extends AbstractAction {
		
		private static final long serialVersionUID = 7084928639244438800L;

		public void actionPerformed(ActionEvent e) {
			JTextField fqnTextField = new JTextField();
			if (selected_node != null) {
				fqnTextField.setText(selected_node.getFqn().toString());
			} else {
				fqnTextField.setText(Fqn.SEPARATOR);
			}
			Object[] information = { "Enter fully qualified name", fqnTextField };
			final String btnString1 = "OK";
			final String btnString2 = "Cancel";
			Object[] options = { btnString1, btnString2 };
			int userChoice = JOptionPane.showOptionDialog(null, information,
					"Add DataNode", JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (userChoice == 0) {
				String userInput = fqnTextField.getText();
				cache.put(Fqn.fromString(userInput), null);
			}
		}
	}

	class LoadAction extends AbstractAction {
		
		private static final long serialVersionUID = -6998760732995584428L;

		public void actionPerformed(ActionEvent e) {
			JTextField fqnTextField = new JTextField();
			if (selected_node != null) {
				fqnTextField.setText(selected_node.getFqn().toString());
			} else {
				fqnTextField.setText(Fqn.SEPARATOR);
			}

			Object[] information = { "Enter fully qualified name", fqnTextField };
			final String btnString1 = "OK";
			final String btnString2 = "Cancel";
			Object[] options = { btnString1, btnString2 };
			int userChoice = JOptionPane.showOptionDialog(null, information,
					"Load DataNode", JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (userChoice == 0) {
				String userInput = fqnTextField.getText();
				load(Fqn.fromString(userInput));
			}
		}
	}

	class EvictAction extends AbstractAction {
		
		private static final long serialVersionUID = 6007500908549034215L;

		public void actionPerformed(ActionEvent e) {
			JTextField fqnTextField = new JTextField();
			if (selected_node != null) {
				fqnTextField.setText(selected_node.getFqn().toString());
			} else {
				fqnTextField.setText(Fqn.SEPARATOR);
			}
			Object[] information = { "Enter fully qualified name", fqnTextField };
			final String btnString1 = "OK";
			final String btnString2 = "Cancel";
			Object[] options = { btnString1, btnString2 };
			int userChoice = JOptionPane.showOptionDialog(null, information,
					"Evict DataNode", JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (userChoice == 0) {
				String userInput = fqnTextField.getText();
				cache.evict(Fqn.fromString(userInput), true);
			}
		}
	}

	class StartTransaction extends AbstractAction {
		
		private static final long serialVersionUID = 7059131008813144857L;

		public void actionPerformed(ActionEvent e) {
			if (tx_mgr == null) {
				log.error("no TransactionManager specified");
				return;
			}
			if (tx != null) {
				log.error("transaction is already running: " + tx);
				return;
			}
			try {
				tx_mgr.begin();
				tx = tx_mgr.getTransaction();
			} catch (Throwable t) {
				printAndLogStacktrace("Creating transaction failed: ", t);
			}
		}
	}

	class CommitTransaction extends AbstractAction {
		
		private static final long serialVersionUID = 5426108920883879873L;

		public void actionPerformed(ActionEvent e) {
			if (tx == null) {
				log.error("transaction is not running");
				return;
			}
			try {
				tx.commit();
			} catch (Throwable t) {
				printAndLogStacktrace("Commiting transaction failed: ", t);
			} finally {
				tx = null;
			}
		}
	}

	class RollbackTransaction extends AbstractAction {
		
		private static final long serialVersionUID = -4836748411400541430L;

		public void actionPerformed(ActionEvent e) {
			if (tx == null) {
				log.error("transaction is not running");
				return;
			}
			try {
				tx.rollback();
			} catch (Throwable t) {
				printAndLogStacktrace("Transaction rollback failed: ", t);
			} finally {
				tx = null;
			}
		}
	}

	class PrintLockInfoAction extends AbstractAction {
		
		private static final long serialVersionUID = -2171307516592250436L;

		public void actionPerformed(ActionEvent e) {
			if (bshConsole != null) {
				new Thread() {
					public void run() {
						bshConsole.getOut().println("\n*** lock information ****\n" + CachePrinter.printCacheLockingInfo(cache));
					}
				}.start();

			} else {
				new Thread() {
					public void run() {
						System.out.println("\n*** lock information ****\n" + CachePrinter.printCacheLockingInfo(cache));
					}
				}.start();
			}
		}
	}

	class RemoveNodeAction extends AbstractAction {
		
		private static final long serialVersionUID = 3746013603940497991L;

		public void actionPerformed(ActionEvent e) {
			try {
				cache.removeNode(selected_node.getFqn());
			} catch (Throwable t) {
				log.error("RemoveNodeAction.actionPerformed(): " + t);
			}
		}
	}

	class AddModifyDataForNodeAction extends AbstractAction {
		
		private static final long serialVersionUID = -7656592171312920825L;

		public void actionPerformed(ActionEvent e) {
						
			if (log.isTraceEnabled()) {
				log.trace("node added/modified, updating GUI: " + e);
			}
			
			if (selected_node == null){
				return;
			}

			try {
				clearTable();
				Map<String, String> data = selected_node.getData();
//			if (data == null || data.isEmpty()) {
//				data = new HashMap<String, String>();
//				String[] placeHolder = getRowPlaceHolderData();
//				data.put(placeHolder[0], placeHolder[1]);
//			}
				
				JTextField keyField = new JTextField("key");
				JTextField valueField = new JTextField("value");
				Object[] information = { "Enter Key", keyField, "Enter Value", valueField };
				final String btnOk = "OK";
				final String btnCancel = "Cancel";
				Object[] options = { btnOk, btnCancel };
				int userChoice = JOptionPane.showOptionDialog(null, information,
						"Add Key/Value to Node", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				
				if (userChoice == 0) {
					String key = keyField.getText();
					String value = valueField.getText();
//					data.put(key, value);
					selected_node.put(key, value);
				}

				populateTable(selected_node.getData());

				mainPanel.add(tablePanel, BorderLayout.SOUTH);
				validate();
			} catch (Exception e1) {
				log.error("AddModifyDataForNodeAction Error", e1);
				throw new RuntimeException("", e1);
			}
		}
	}

	public class DisplayNode extends DefaultMutableTreeNode {
		
		private static final long serialVersionUID = 4882445905140460053L;
		String name = "<unnamed>";


		public DisplayNode(String name) {
			this.name = name;
		}

		/**
		 * Adds a new node to the view. Intermediary nodes will be created if
		 * they don't yet exist. Returns the first node that was created or null
		 * if node already existed
		 */
		public JBossCacheGUI.DisplayNode add(Fqn fqn) {
         
			JBossCacheGUI.DisplayNode curr, n, ret = null;

			if (fqn == null){
				return null;
			}
			curr = this;

			for (Object childName : fqn.peekElements()) {
				n = curr.findChild((String) childName);
				if (n == null) {
					n = new JBossCacheGUI.DisplayNode((String) childName);
					if (ret == null)
						ret = n;
					curr.add(n);
				}
				curr = n;
			}
			return ret;
		}


      /**
       * Removes a node from the view. Child nodes will be removed as well
       */
		public void remove() {
			removeFromParent();
		}


		private JBossCacheGUI.DisplayNode findNode(Fqn fqn) {
			
			JBossCacheGUI.DisplayNode curr, n;

			if (fqn == null)
				return null;
			curr = this;

			for (Object childName : fqn.peekElements()) {
				n = curr.findChild((String) childName);
				if (n == null) {
					return null;
				}
				curr = n;
			}
			return curr;
		}


		private JBossCacheGUI.DisplayNode findChild(String relative_name) {
			
			JBossCacheGUI.DisplayNode child;

			if (relative_name == null || getChildCount() == 0) {
				return null;
			}
			
			for (int i = 0; i < getChildCount(); i++) {
				
				child = (JBossCacheGUI.DisplayNode) getChildAt(i);
				
				if (child.name == null) {
					continue;
				}

				if (child.name.equals(relative_name)) {
					return child;
				}
			}
			return null;
		}

		public String toString() {
			return name;
		}
	}
}
