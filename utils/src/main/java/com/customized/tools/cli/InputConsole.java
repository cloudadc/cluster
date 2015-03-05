package com.customized.tools.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * InputConsole
 * 
 * @author kylin
 *
 */
public class InputConsole extends Console {
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public boolean readFromCli(String prompt) {
		
		String msg = "Run " + prompt + " From Command Line\n" +
				 "  [1]. Yes\n" +
			     "  [2]. No\n" +
				 "Default is [1]";
		int a = '1';
		int b = '2';
		int res = readWithDef(msg, a, a, b);
		
		if(res == a) {
			return true ;
		} else {
			return false ;
		}
	}
	
	public boolean isRemoving(String note) {
		
		String msg = "Remove " + note + " ?\n" +
				 "  [1]. Yes\n" +
			     "  [2]. No\n" +
				 "Default is [1]";
		int a = '1';
		int b = '2';
		int res = readWithDef(msg, a, a, b);
		
		if(res == a) {
			return true ;
		} else {
			return false ;
		}
	}
	
	public boolean isSelecting(String note) {
		
		String msg = "You selected " + note + " ?\n" +
				 "  [1]. Yes\n" +
			     "  [2]. No\n" +
				 "Default is [1]";
		int a = '1';
		int b = '2';
		int res = readWithDef(msg, a, a, b);
		
		if(res == a) {
			return true ;
		} else {
			return false ;
		}
	}
	
	public boolean isExit() {
		
		String msg = "Are you sure to exit ?\n" +
				 "  [1]. Yes\n" +
			     "  [2]. No\n" +
				 "Default is [1]";
		int a = '1';
		int b = '2';
		int res = readWithDef(msg, a, a, b);
		
		if(res == a) {
			return true ;
		} else {
			return false ;
		}
	}
	
	public boolean isQuit() {
		
		String msg = "Are you sure to quit ?\n" +
				 "  [1]. Yes\n" +
			     "  [2]. No\n" +
				 "Default is [1]";
		int a = '1';
		int b = '2';
		int res = readWithDef(msg, a, a, b);
		
		if(res == a) {
			return true ;
		} else {
			return false ;
		}
	}
	
	public boolean isSettingComplete() {
		
		String msg = "Setting Complete\n" +
				 "  [1]. Yes\n" +
			     "  [2]. No\n" +
				 "Default is [1]";
		int a = '1';
		int b = '2';
		int res = readWithDef(msg, a, a, b);
		
		if(res == a) {
			return true ;
		} else {
			return false ;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int read(String prompt, int ...params) {
		
		Set set = new HashSet();
		for(int i : params) {
			set.add(i);
		}
		int result = 0;
		
		while(true) {
			
			result = keyPress(prompt);
			
			if(set.contains(result)) {
				break;
			}
		}
		
		return result ;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int readWithDef(String prompt, int def, int ...params ) {
		
		Set set = new HashSet();
		for(int i : params) {
			set.add(i);
		}
		int result = 0;
		
		while(true) {
			
			result = keyPress(prompt);
			
			if(set.contains(result)) {
				break;
			} else if (result == 10) {
				result = def ;
				break;
			}
		}
		
		return result ;
	}
	
	public int keyPress(String msg) {
        
		println(msg);

        try {
			int ret = System.in.read();
            System.in.skip(System.in.available());
            return ret;
        }
        catch(IOException e) {
            return 0;
        }
    }
	
	public int keyPress() {
        
        try {
			int ret = System.in.read();
            System.in.skip(System.in.available());
            return ret;
        }
        catch(IOException e) {
            return 0;
        }
    }
	
	/**
	 * 
	 * @param prompt
	 * @param validation, if true, inputed folder should be existed
	 * @return
	 */
	public String readFolderPath(String prompt, boolean validation) {
		
		String result = "" ;
		
		while(true) {
			
			println(prompt);
			
			String input = "";
			try {
				input = br.readLine();
			} catch (IOException e) {
				throw new IllegalArgumentException("readFolderPath Error", e);
			}
			
			if(validation && new File(input).exists() && new File(input).isDirectory()) {
				result = input;
				break;
			} else if(input.length() > 0){
				result = input;
				break;
			}
		}
		
		return result ;
	}
	
	public String readFolderPath(String prompt, String defaultPath, boolean validation) {
		
		String result = "" ;
		
		while(true) {
			
			println(prompt + " default [" + defaultPath + "]");
			
			String input = "";
			try {
				input = br.readLine();
			} catch (IOException e) {
				throw new IllegalArgumentException("readFolderPath Error", e);
			}
			
			if(input.equals("") || input.trim().equals("")) {
				result = defaultPath ;
				break;
			}
			
			if(validation && new File(input).exists() && new File(input).isDirectory()) {
				result = input;
				break;
			} else if (input.length() > 0) {
				result = input;
				break;
			}
		}
		
		return result ;
	}
	
	/**
	 * 
	 * @param prompt
	 * @param validation if true, file should be exist
	 * @return
	 */
	public String readFilePath(String prompt, boolean validation) {
		
		String result = "" ;
		
		while(true){
			
			println(prompt);
			
			String input = "";
			try {
				input = br.readLine();
			} catch (IOException e) {
				throw new IllegalArgumentException("readFilePath Error", e);
			}
			
			if(validation && new File(input).exists() && new File(input).isFile()) {
				result = input;
				break;
			} else if(input.length() > 0){
				result = input;
				break;
			}
		}
		
		return result;
	} 
	
	/**
	 * 
	 * @param prompt
	 * @param value the default value, press enter will return this value
	 * @param validation is true, null value is not allowed
	 * @return
	 */
	public String readString(String prompt, String value, boolean validation)  {
		
		String result = "" ;
		
		while(true){
			
			println(prompt + " default [" + value + "]");
			
			String input = null;
			try {
				input = br.readLine();
			} catch (IOException e) {
				throw new IllegalArgumentException("readString Error", e);
			}
			
			if(input.equals("") || input.trim().equals("")) {
				result = value ;
			} else {
				result = input;
			}

			if(validation ){
				if (result.length() > 0)
					break;
			} else {
				break ;
			}
		}
		
		return result;
	} 
	
	/**
	 * 
	 * @param prompt
	 * @param value the default value, press enter will return this value
	 * @param validation is true, null value is not allowed
	 * @return
	 */
	public String readString(String prompt, boolean validation)  {
		
		String result = "" ;
		
		while(true){
			
			println(prompt);
			
			String input = null;
			try {
				input = br.readLine();
			} catch (IOException e) {
				throw new IllegalArgumentException("readString Error", e);
			}
			
			result = input;

			if(validation ){
				if (result.length() > 0)
					break;
			} else {
				break ;
			}
		}
		
		return result;
	} 
	
	/**
	 * 
	 * @param prompt
	 * @param value default value, press enter will return this value
	 * @return
	 */
	public String readString(String prompt, String value)  {
		
		String result = "" ;
		
		while(true){
			
			println(prompt + " default [" + value + "]");
			
			String input = null;
			try {
				input = br.readLine();
			} catch (IOException e) {
				throw new IllegalArgumentException("readString Error", e);
			}
			
			if(input.equals("") || input.trim().equals("")) {
				result = value ;
				break;
			}
			
			result = input ;
			break;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param prompt
	 * @param value default value, Press Enter will return default value
	 * @return
	 */
	public int readInteger(String prompt, int value)  {
		
		int result = -1 ;
		
		while(true){
			
			println(prompt + " default [" + value + "]");
			
			String input = null;
			try {
				input = br.readLine();
			} catch (IOException e) {
				throw new IllegalArgumentException("readInteger Error", e);
			}
			
			if(input.equals("") || input.trim().equals("")) {
				result = value ;
				break;
			} 

			try {
				result = Integer.parseInt(input);
				break;
			} catch (NumberFormatException e) {
				prompt("You should input a int type value");
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param prompt
	 * @param value default value, Press Enter will return default value
	 * @return
	 */
	public long readLong(String prompt, long value)  {
		
		long result = -1 ;
		
		while(true){
			
			println(prompt + " default [" + value + "]");
			
			String input = null;
			try {
				input = br.readLine();
			} catch (IOException e) {
				throw new IllegalArgumentException("readLong Error", e);
			}
			
			if(input.equals("") || input.trim().equals("")) {
				result = value ;
				break;
			} 

			try {
				result = Long.parseLong(input);
				break;
			} catch (NumberFormatException e) {
				prompt("You should input a long type value");
			}
		}
		
		return result;
	} 
	
}
