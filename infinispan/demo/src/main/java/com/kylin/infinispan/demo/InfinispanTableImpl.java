package com.kylin.infinispan.demo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.customized.tools.ui.swt.Column;
import com.customized.tools.ui.swt.DefaultTableImpl;

public class InfinispanTableImpl extends DefaultTableImpl {

	public InfinispanTableImpl(String title, String image) {
		super(title, image);
	}

	protected void fillTableContent(Table table) {
		
		List<Column> columns = new ArrayList<Column>();
	    columns.add(new Column("Key", 100));
	    columns.add(new Column("Value", 200));
	    columns.add(new Column("Lifespan", 100));
	    columns.add(new Column("MaxIdle", 100));
	    columns.add(new Column("Alias", 100));
	    fillTableColumns(columns, table);
	    
	    List<String[]> items = new ArrayList<String[]>();
	    items.add(new String[]{"key", "value", "-1", "-1", "localhost"});
	    items.add(new String[]{"key", "value", "-1", "-1", "localhost"});
	    items.add(new String[]{"key", "value", "-1", "-1", "localhost"});
	    fillTableItems(items, table);
	}

	protected void fillToolBarItem(ToolBar toolBar) {
		
		ToolItem delete = new ToolItem(toolBar, SWT.PUSH);
		delete.setText("Delete");
		delete.addListener(SWT.SELECTED, new Listener() {

			public void handleEvent(Event event) {

			}
		});
		
		ToolItem clear = new ToolItem(toolBar, SWT.PUSH);
		clear.setText(" Clear ");
		clear.addListener(SWT.SELECTED, new Listener() {

			public void handleEvent(Event event) {

			}
		});
		
		ToolItem refresh = new ToolItem(toolBar, SWT.PUSH);
		refresh.setText("Refresh");
		refresh.addListener(SWT.SELECTED, new Listener() {

			public void handleEvent(Event event) {

			}
		});
	}

}
