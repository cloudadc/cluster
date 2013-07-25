package org.infinispan.grid.demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;

import com.customized.tools.ui.swt.Column;
import com.customized.tools.ui.swt.DefaultTableImpl;

public class InfinispanTableImpl extends DefaultTableImpl {
		
	private InfinispanConsole console ;
	
	private CacheDelegate delegate ;
	
	public InfinispanTableImpl(String title, String image, InfinispanConsole console, CacheDelegate delegate) {
		super(title, image);
		this.console = console ;
		this.delegate = delegate ;
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
	
	public void insertEntry(CacheEntry e) {

		final TableItem item = new TableItem(getTable(), SWT.NONE);
		item.setText(new String[]{e.getKey(), e.getValue(), String.valueOf(e.getLifespan()), String.valueOf(e.getMaxIdle()), e.getAlias()});
	}

	protected void fillToolBarItem(ToolBar toolBar) {
		
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
		
		for(String key : keys) {
			console.removeTreeNode(key);
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
			System.out.println("Refresh");
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
				stop();
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

	

}
