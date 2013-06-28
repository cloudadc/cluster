package com.kylin.jbosscache.custom.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;

public class JBossCacheGUI implements EntryPoint {
	
	private final JBossCacheServiceAsync jbossCacheService = GWT.create(JBossCacheService.class);

	public void onModuleLoad() {
		
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("/a/b/c");
		final Label errorLabel = new Label();

		sendButton.addStyleName("sendButton");

		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		nameField.setFocus(true);
		nameField.selectAll();
		
//		jbossCacheService.initTree(new AsyncCallback() {});
		
		Tree staticTree = new Tree();
//		staticTree.add
		
	}

}
