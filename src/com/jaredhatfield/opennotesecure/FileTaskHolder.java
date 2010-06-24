package com.jaredhatfield.opennotesecure;

import java.io.File;

import android.app.ProgressDialog;
import android.widget.EditText;

public class FileTaskHolder {
	/**
	 * 
	 */
	private File file;
	
	/**
	 * 
	 */
	private String encryption;
	
	/**
	 * 
	 */
	private String password;
	
	/**
	 * 
	 */
	private ProgressDialog dialog;
	
	/**
	 * 
	 */
	private EditText content;
	
	/**
	 * 
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
