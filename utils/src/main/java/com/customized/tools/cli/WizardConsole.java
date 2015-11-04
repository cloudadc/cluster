package com.customized.tools.cli;


public class WizardConsole extends InputConsole {

	private Wizard wizard ;

	public WizardConsole() {
		super();
		
	}
	
	int s1 = '1', s2 = '2', s3 = '3', s4 = '4', s5 = '5', s6 = '6', s7 = '7', s8 = '8', s9 = '9', sp = 'p', sn = 'n', so = 'o';
	
	public Wizard popWizard(Wizard wizard ) {
		
		this.wizard = wizard;
		
		while (true){
			prompt(wizard.getPrompt());
			int res = keyPress();
			
			if(res == so) {
				if (wizard.doOk(this))
					break;
			} else if(res == sp) {
				wizard.doPre();
			} else if(res == sn) {
				wizard.doNext();
			} else if(res == s1) {
				updateContent(0);
			} else if(res == s2) {
				updateContent(1);
			} else if(res == s3) {
				updateContent(2);
			} else if(res == s4) {
				updateContent(3);
			} else if(res == s5) {
				updateContent(4);
			} else if(res == s6) {
				updateContent(5);
			} else if(res == s7) {
				updateContent(6);
			} else if(res == s8) {
				updateContent(7);
			} else if(res == s9) {
				updateContent(8);
			} 
		}
		
		return wizard ;
	}

	private void updateContent(int id) {
		String result = readString("Input " + wizard.getKey(id) + ":", false);
		wizard.update(id, result);
	}
}
