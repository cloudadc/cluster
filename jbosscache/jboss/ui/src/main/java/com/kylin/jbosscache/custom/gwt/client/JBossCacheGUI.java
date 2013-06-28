package com.kylin.jbosscache.custom.gwt.client;

import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.kylin.jbosscache.custom.gwt.shared.NodeEntity;

public class JBossCacheGUI implements EntryPoint {
	
	private final JBossCacheServiceAsync jbossCacheService = GWT.create(JBossCacheService.class);
	
	final Button sendButton = new Button("Send");
	final TextBox nameField = new TextBox();
	final Label errorLabel = new Label();
	final Tree tree = new Tree();

	public void onModuleLoad() {
		
		System.out.println("JBossCacheGUI onModuleLoad");
		
		nameField.setText("/a/b/c");
		sendButton.addStyleName("sendButton");

		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		
		RootPanel.get("errorLabelContainer").add(errorLabel);
		
		RootPanel.get("rootTreeContainer").add(tree);

		nameField.setFocus(true);
		nameField.selectAll();
		
		jbossCacheService.initTree(new AsyncCallback<NodeEntity>() {

			public void onFailure(Throwable t) {
				throw new JBossCacheGUIException("JBossCache Service init root tree error", t);
			}

			public void onSuccess(NodeEntity result) {
				updateRootTree(result);
			}});
				
	}
	
	private void updateRootTree(NodeEntity result) {
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
	
	

}
