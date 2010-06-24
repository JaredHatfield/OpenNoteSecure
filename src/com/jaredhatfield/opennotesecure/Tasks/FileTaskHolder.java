/*
 * OpenNoteSecure is an Android application for reading and writing encrypted
 * text files to an SD card.
 * Copyright (C) 2010  Jared Hatfield
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaredhatfield.opennotesecure.Tasks;

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
