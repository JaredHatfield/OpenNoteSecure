package com.jaredhatfield.opennotesecure;

import java.io.File;

import android.app.ProgressDialog;
import android.widget.EditText;

public class FileTaskHolder {
	/**
	 * The file that is being manipulated.
	 */
	private File file;
	
	/**
	 * The encryption that is being used.
	 */
	private String encryption;
	
	/**
	 * The password for the file.
	 */
	private String password;
	
	/**
	 * The dialog box that is currently displayed while the task is being performed.
	 */
	private ProgressDialog dialog;
	
	/**
	 * The EditText that contains the text being manipulated.
	 */
	private EditText content;
	
	/**
	 * Temporary storage for the result of the decryption.
	 */
	private String result;
	
	/**
	 * 
	 * @param file
	 * @param encryption
	 * @param password
	 * @param dialog
	 * @param content
	 */
	public FileTaskHolder(File file, String encryption, String password, ProgressDialog dialog, EditText content){
		this.file = file;
		this.encryption = encryption;
		this.password = password;
		this.dialog = dialog;
		this.content = content;
		this.result = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public File getFile(){
		return this.file;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEncryption(){
		return this.encryption;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPassword(){
		return this.password;
	}
	
	/**
	 * 
	 * @return
	 */
	public ProgressDialog getDialog(){
		return this.dialog;
	}
	
	/**
	 * 
	 * @return
	 */
	public EditText getEditTextContent(){
		return this.content;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getResult(){
		return this.result;
	}
	
	/**
	 * 
	 * @param result
	 */
	public void setResult(String result){
		this.result = result;
	}
}
