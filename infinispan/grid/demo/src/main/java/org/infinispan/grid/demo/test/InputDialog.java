package org.infinispan.grid.demo.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.infinispan.grid.demo.CacheEntry;


public class InputDialog extends Dialog {

	public InputDialog(Shell parent) {
		super(parent);
	}
	
	String key = "Key", value = "Value", lifespan = "-1", maxIdle = "-1";
	
	CacheEntry entity;
	
	public CacheEntry getEntity() {
		return entity;
	}


	public void open() {
	    Shell parent = getParent();
	    final Shell shell =
	      new Shell(parent, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL);
	    shell.setText("InputDialog");

	    shell.setLayout(new GridLayout(2, true));

	    Label keylabel = new Label(shell, SWT.NULL);
	    keylabel.setText("Key:");
	    final Text keytext = new Text(shell, SWT.BORDER);
	    keytext.setText(key);
//	    keytext.setSize(100, 20);
	    
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
				
				entity = new CacheEntry(keytext.getText(), valuetext.getText(), lifespantext.getText(), maxIdletext.getText(), "-");
				
				shell.dispose();
			}
		});

	    buttonCancel.addListener(SWT.Selection, new Listener() {
	      public void handleEvent(Event event) {
	        shell.dispose();
	      }
	    });
	    
	    shell.addListener(SWT.Traverse, new Listener() {
	      public void handleEvent(Event event) {
	        if(event.detail == SWT.TRAVERSE_ESCAPE)
	          event.doit = false;
	      }
	    });

//	    text.setText("");
	    shell.pack();
	    shell.open();

	    Display display = parent.getDisplay();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }

	  }


	public static void main(String[] args) {

		Shell shell = new Shell();
		InputDialog dialog = new InputDialog(shell);
		dialog.open();
		
		System.out.println(dialog.getEntity());
	}

}
