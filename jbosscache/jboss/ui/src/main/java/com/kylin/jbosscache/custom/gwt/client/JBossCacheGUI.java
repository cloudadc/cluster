package com.kylin.jbosscache.custom.gwt.client;

import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.kylin.jbosscache.custom.gwt.shared.CacheEntity;
import com.kylin.jbosscache.custom.gwt.shared.NodeEntity;

public class JBossCacheGUI implements EntryPoint {
	
	private final JBossCacheServiceAsync jbossCacheService = GWT.create(JBossCacheService.class);
	
	final Button sendButton = new Button("Add");
	final TextBox nameField = new TextBox();
	final Label errorLabel = new Label();
	final Tree tree = new Tree();
	final CellTable<CacheEntity> table = new CellTable<CacheEntity>();

	public void onModuleLoad() {
				
		sendButton.addStyleName("sendButton");

		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		
		RootPanel.get("errorLabelContainer").add(errorLabel);
		
		RootPanel.get("rootTreeContainer").add(tree);
		RootPanel.get("rootTableContainer").add(table);

		nameField.setFocus(true);
		nameField.selectAll();
		
		initTableColumns();
		
		tree.addSelectionHandler(new SelectionHandler<TreeItem>(){

			public void onSelection(SelectionEvent<TreeItem> event) {	
				String path = getPath(event.getSelectedItem().getText(), event.getSelectedItem());
				if(path.startsWith("//")) {
					path = path.substring(1);
				}
				nameField.setText(path);
				updateTable(path);
			}	
		});
		
		sendButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if(nameField.getText().length() <= 0) {
					errorLabel.setText("Please select or input a Fqn path");
				}else {
					errorLabel.setText("");
					sendButton.setEnabled(false);
					addCacheEntiry();
				}
			}
		});
		
		
		jbossCacheService.initTree(new AsyncCallback<NodeEntity>() {

			public void onFailure(Throwable t) {
				throw new JBossCacheGUIException("JBossCache Service init root tree error", t);
			}

			public void onSuccess(NodeEntity result) {
				updateRootTree(result);
			}});
				
	}
	
	private void addCacheEntiry() {

		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Add Cache Entity to " + nameField.getText());
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("OK");
		closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Enter key:</b>"));
		final TextBox key = new TextBox();
		key.setText("key");
		key.setFocus(true);
		key.selectAll();
		dialogVPanel.add(key);
		dialogVPanel.add(new HTML("<b>Enter Value:</b>"));
		final TextBox value = new TextBox();
		value.setText("value");
		value.setFocus(true);
		value.selectAll();
		dialogVPanel.add(value);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		dialogBox.center();
		
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				jbossCacheService.addCacheContent(nameField.getText(), key.getText(), value.getText(), new AsyncCallback<Integer>(){

					public void onFailure(Throwable t) {
						throw new JBossCacheGUIException("add entity to " + nameField.getText() + " error", t);
					}

							public void onSuccess(Integer result) {
								if(result != 1) {
									throw new JBossCacheGUIException("add entity to " + nameField.getText() + " error");
								}
							}
						});
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});
	}
	
	private void updateTable(String path) {
		
		final String fqn = path;
		
		
		jbossCacheService.getCacheContent(path, new AsyncCallback<List<CacheEntity>>(){

					public void onFailure(Throwable t) {
						throw new JBossCacheGUIException("get " + fqn + " contents error", t);
					}

					public void onSuccess(List<CacheEntity> result) {
						updateTableItems(result);
					}

					private void updateTableItems(List<CacheEntity> result) {
						
						final List<CacheEntity> list = result;

						AsyncDataProvider<CacheEntity> provider = new AsyncDataProvider<CacheEntity>(){

							protected void onRangeChanged(HasData<CacheEntity> display) {
								int start = display.getVisibleRange().getStart();
								int end = start + display.getVisibleRange().getLength();
						        end = end >= list.size() ? list.size() : end;
						        List<CacheEntity> sub = list.subList(start, end);
								updateRowData(start, sub);
							}
						};
						
						provider.addDataDisplay(table);
					    provider.updateRowCount(list.size(), true);
					    SimplePager pager = new SimplePager();
					    pager.setDisplay(table);						
					}
				});
		
	}
	
	private void initTableColumns() {

		table.setAutoHeaderRefreshDisabled(true);
		table.setAutoFooterRefreshDisabled(true);
		TextColumn<CacheEntity> keyColumn = new TextColumn<CacheEntity>(){

			public String getValue(CacheEntity entity) {
				return entity.getKey();
			}
		};
		table.addColumn(keyColumn, "Key");
		TextColumn<CacheEntity> valueColumn = new TextColumn<CacheEntity>(){

			public String getValue(CacheEntity entity) {
				return entity.getValue();
			}
		};
		table.addColumn(valueColumn, "Value");	
	}

	private void updateRootTree(NodeEntity result) {
//		HorizontalPanel hc = new HorizontalPanel();
//	    hc.add(new Image("http://tueffel.net/images/icons/icon13.gif"));
//	    hc.add(new Label("/"));
//	    TreeItem root = new TreeItem(hc);
//	    tree.addItem(root);
		TreeItem root = tree.addTextItem(result.getName());
		updateTreeNode(root, result.getChilds());
	}
	
	private void updateTreeNode(TreeItem parent, Set<NodeEntity> childs) {
		for(NodeEntity entity : childs){
			TreeItem item = parent.addTextItem(entity.getName());
			if(entity.getChilds().size() > 0) {
				updateTreeNode(item, entity.getChilds());
			}
		}
	}

	private String getPath(String leaf, TreeItem item) {
		
		if(item.getParentItem() != null) {
			leaf = item.getParentItem().getText() + "/" + leaf;
			return getPath(leaf, item.getParentItem());
		}

		return leaf;
	}
	
}
