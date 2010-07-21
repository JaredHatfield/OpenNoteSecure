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

import com.jaredhatfield.opennotesecure.FileManager;
import com.jaredhatfield.opennotesecure.OpenNoteSecure;
import com.jaredhatfield.opennotesecure.EncryptionProviders.AESEncryptionProvider;
import com.jaredhatfield.opennotesecure.EncryptionProviders.DESEncryptionProvider;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

/**
 * Performs decryption using an AsyncTask.
 * @author Jared Hatfield
 */
public class DecryptionTask extends AsyncTask<FileTaskHolder, Void, FileTaskHolder>{

	/**
	 * Perform the task of decrypting the file.
	 */
	@Override
	protected FileTaskHolder doInBackground(FileTaskHolder... arg0) {
		FileTaskHolder holder = arg0[0];
		
		// Read in and decrypt the file
		if(holder.getEncryption().equals("None")){
			Log.i(OpenNoteSecure.TAG, "Decrypting with Algorithm: None");
			holder.setResult(FileManager.Instance().readFile(holder.getFile()));
		}
		else if(holder.getEncryption().equals("AES")){
			Log.i(OpenNoteSecure.TAG, "Decrypting with Algorithm: AES");
			String ciphertext = FileManager.Instance().readFile(holder.getFile());
			if(ciphertext.length() > 0){
				try {
					AESEncryptionProvider aes = new AESEncryptionProvider(holder.getPassword());
					String plaintext = aes.decryptAsBase64(ciphertext);
					holder.setResult(plaintext);
				} 
				catch (Exception e) {
					// The decryption failed so we will not set the result
					Log.e(OpenNoteSecure.TAG, e.getMessage());
				}
			}
			else{
				// The file is blank so we set the result to indicate the decryption didn't fail.
				holder.setResult("");
			}
		}
		else if(holder.getEncryption().equals("DES")){
			Log.i(OpenNoteSecure.TAG, "Decrypting with Algorithm: DES");
			String ciphertext = FileManager.Instance().readFile(holder.getFile());
			if(ciphertext.length() > 0){
				try {
					DESEncryptionProvider des = new DESEncryptionProvider(holder.getPassword());
					String plaintext = des.decryptAsBase64(ciphertext);
					holder.setResult(plaintext);
				} 
				catch (Exception e) {
					// The decryption failed so we will not set the result
					Log.e(OpenNoteSecure.TAG, e.getMessage());
				}
			}
			else{
				// The file is blank so we set the result to indicate the decryption didn't fail.
				holder.setResult("");
			}
		}
		
		return holder;
	}
	
	/**
	 * Load the decrypted content into the EditText box and dismiss the dialog.
	 */
	@Override
    protected void onPostExecute(FileTaskHolder holder)
    {
		// Add the plain text to the EditText
		EditText content = holder.getEditTextContent();
		String result = holder.getResult();
		if(result != null){
			content.setText(result);
		}
		else{
			content.setEnabled(false);
			content.setText("Decryption failed.");
		}
		
		// Dismiss the dialog
		holder.getDialog().dismiss();
    }
}

