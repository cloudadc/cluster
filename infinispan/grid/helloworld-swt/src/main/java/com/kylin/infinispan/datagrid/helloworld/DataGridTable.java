package com.kylin.infinispan.datagrid.helloworld;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;

import com.customized.tools.ui.swt.Column;
import com.customized.tools.ui.swt.DefaultTableImpl;

/**
 * 
 * mvn clean install dependency:copy-dependencies
 * 
 * java -cp target/dependency/*:target/grid-helloworld.jar -Djava.net.preferIPv4Stack=true com.kylin.infinispan.datagrid.helloworld.DataGridTable
 * 
 * @author kylin
 *
 */
public class DataGridTable extends DefaultTableImpl {
	
	private CacheDelegate delegate ;

	public DataGridTable(String image, CacheDelegate delegate) {
		super(delegate.getGenericCache().getCacheManager().getAddress().toString(), image);
		this.delegate = delegate;
	}

	protected void fillTableContent(Table table) {
		
		List<Column> columns = new ArrayList<Column>();
	    columns.add(new Column("Key", 100));
	    columns.add(new Column("Value", 200));
	    columns.add(new Column("Lifespan", 100));
	    columns.add(new Column("MaxIdle", 100));
	    columns.add(new Column("Alias", 100));
	    fillTableColumns(columns, table);
	    
	    updateTableItems();
	}
	
	public void updateTableItems() {
		
		getTable().setRedraw(false);
		dispose(getTable().getItems());
		
		List<String[]> items = new ArrayList<String[]>();
		
		String lifespan = delegate.getGenericCache().getCacheConfiguration().expiration().lifespan() + "";
		String maxIdle = delegate.getGenericCache().getCacheConfiguration().expiration().maxIdle() + "";	
		String alias = delegate.getGenericCache().getCacheManager().getAddress().toString();
		
		for(String key : delegate.getGenericCache().keySet()) {
			items.add(new String[]{key, delegate.getGenericCache().get(key), lifespan, maxIdle, alias});
		}
		
		fillTableItems(items, getTable());
		
		getTable().setRedraw(true);
	}
	

	protected void fillToolBarItem(ToolBar toolBar) {
		
		ToolItem add = new ToolItem(toolBar, SWT.PUSH);
		add.setText("Add");
		add.addListener(SWT.Selection, new AddListener());
		
		ToolItem delete = new ToolItem(toolBar, SWT.PUSH);
		delete.setText("Delete");
		delete.addListener(SWT.Selection, new DeleteListener());
		
		ToolItem clear = new ToolItem(toolBar, SWT.PUSH);
		clear.setText("Clear ");
		clear.addListener(SWT.Selection, new ClearListener());
		
		ToolItem refresh = new ToolItem(toolBar, SWT.PUSH);
		refresh.setText("Refresh");
		refresh.addListener(SWT.Selection, new RefreshListener());
		
		ToolItem help = new ToolItem(toolBar, SWT.PUSH);
		help.setText("Help ");
		help.addListener(SWT.Selection, new HelpListener());
		
		ToolItem exit = new ToolItem(toolBar, SWT.PUSH);
		exit.setText("Exit ");
		exit.addListener(SWT.Selection, new ExitListener());
	}
	
	
	private static final int CONFIRM_OK = 1 << 5 ;
	private static final int CONFIRM_CANCEL = 1 << 8 ;
	
	private void dispose(Widget[] widgets) {
		for(Widget widget : widgets) 
			widget.dispose();
	}
	
	private void clearEntities(Set<String> keys) {
		
		delegate.removeAll(keys);
		
	}
	
	private class AddListener implements Listener {

		public void handleEvent(Event event) {
					
			AddDialog dialog = new AddDialog(getShell());
			dialog.open();
			if(dialog.isSet()) {
				CacheEntity entity = dialog.getEntity();
				delegate.getGenericCache().put(entity.getKey(), entity.getValue(), Long.parseLong(entity.getLifespan()), TimeUnit.MILLISECONDS, Long.parseLong(entity.getMaxIdle()), TimeUnit.MILLISECONDS);
				updateTableItems();
			}
		}
		
	}
	
	private class DeleteListener implements Listener {

		public void handleEvent(Event event) {
			
			Set<String> keys = new HashSet<String>();
			
			for(TableItem item : getTable().getSelection()) {
				keys.add(item.getText(0));
			}
			
			if (new ConfirmDialog(getShell(), "Delete Entity", keys.size() == 0 ? "Please select TableItem first" : "Do you really want to clear selected " + keys.size() + (keys.size() > 1 ? " entities" : " entity")).open() == CONFIRM_OK) {
				
				clearEntities(keys );
				
				getTable().setRedraw(false);
				dispose(getTable().getSelection());
				getTable().setRedraw(true);
			}
		}
		
	}
	
	private class ClearListener implements Listener {

		public void handleEvent(Event event) {
			
			Set<String> keys = new HashSet<String>();
			
			for(TableItem item : getTable().getItems()) {
				keys.add(item.getText(0));
			}
			
			if(new ConfirmDialog(getShell(), "Clear Entity", "Do you really want to clear all entities").open() == CONFIRM_OK) {
				
				clearEntities(keys );
				
				getTable().setRedraw(false);
				dispose(getTable().getItems());
				getTable().setRedraw(true);
			}
			
		}
		
	}
	
	private class RefreshListener implements Listener {

		public void handleEvent(Event event) {
			updateTableItems();
		}
		
	}
	
	private class HelpListener implements Listener {

		public void handleEvent(Event event) {
			MessageBox msgBox = new MessageBox(getShell(), SWT.OK);
			msgBox.setText("Help");
			msgBox.setMessage("https://github.com/kylinsoong \n" + 
							  "https://github.com/kylinsoong/cluster \n" + 
							  "https://github.com/kylinsoong/CustomizedTools");
			msgBox.open();
		}
		
	}
	
	private class ExitListener implements Listener {

		public void handleEvent(Event event) {
			if(new ConfirmDialog(getShell(), "Exit", "Do you really want to exit").open() == CONFIRM_OK) {
				delegate.destory();
				stop();
			}
		}
		
	}
	
	private class AddDialog extends Dialog {

		public AddDialog(Shell parent) {
			super(parent);
			setText("Add Cache Entity");
		}
		String key = "Key", value = "Value", lifespan = "-1", maxIdle = "-1";
		
		CacheEntity entity;
		
		public CacheEntity getEntity() {
			return entity;
		}

		boolean isSet = false;

		public boolean isSet() {
			return isSet;
		}

		public void setSet(boolean isSet) {
			this.isSet = isSet;
		}

		public void open() {
		    Shell parent = getParent();
		    final Shell shell = new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);

		    shell.setLayout(new GridLayout(2, true));

		    Label keylabel = new Label(shell, SWT.NULL);
		    keylabel.setText("Key:");
		    final Text keytext = new Text(shell, SWT.BORDER);
		    keytext.setText(key);
		    
		    Label valuelabel = new Label(shell, SWT.NULL);
		    valuelabel.setText("Value:");
		    final Text valuetext = new Text(shell, SWT.BORDER);
		    valuetext.setText(value);
		    
		    Label lifespanlabel = new Label(shell, SWT.NULL);
		    lifespanlabel.setText("Lifespan:");
		    final Text lifespantext = new Text(shell, SWT.BORDER);
		    lifespantext.setText(lifespan);
		    
		    Label maxIdlelabel = new Label(shell, SWT.NULL);
		    maxIdlelabel.setText("MaxIdle:");
		    final Text maxIdletext = new Text(shell, SWT.BORDER);
		    maxIdletext.setText(maxIdle);

		    final Button buttonOK = new Button(shell, SWT.PUSH);
		    buttonOK.setText(" Ok ");
		    buttonOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		    Button buttonCancel = new Button(shell, SWT.PUSH);
		    buttonCancel.setText("Cancel");

			buttonOK.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					
					entity = new CacheEntity(keytext.getText(), valuetext.getText(), lifespantext.getText(), maxIdletext.getText(), "-");
					setSet(true);
					shell.dispose();
				}
			});

			buttonCancel.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					setSet(false);
					shell.dispose();
				}
			});
		    
		    shell.addListener(SWT.Traverse, new Listener() {
		      public void handleEvent(Event event) {
		        if(event.detail == SWT.TRAVERSE_ESCAPE)
		          event.doit = false;
		      }
		    });

		    shell.pack();
		    shell.open();

		    Display display = parent.getDisplay();
		    while (!shell.isDisposed()) {
		      if (!display.readAndDispatch())
		        display.sleep();
		    }

		  }
		
	}
	
	
	private class ConfirmDialog {
		
		MessageBox msgBox ;

		public ConfirmDialog(Shell shell, String name, String msg) {
			msgBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK| SWT.CANCEL);
			msgBox.setText(name);
			msgBox.setMessage(msg);
		}
		
		public int open() {
			return msgBox.open();
		}
	}
	
	public static void main(String[] args) {
		
		new DataGridTable("infinispan_icon_32px.gif", new CacheDelegateImpl()).start();
	}

}
