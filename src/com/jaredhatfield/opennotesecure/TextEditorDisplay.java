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
package com.jaredhatfield.opennotesecure;

import java.io.File;

import com.jaredhatfield.opennotesecure.Tasks.DecryptionTask;
import com.jaredhatfield.opennotesecure.Tasks.EncryptionTask;
import com.jaredhatfield.opennotesecure.Tasks.FileTaskHolder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TextEditorDisplay extends Activity implements OnClickListener {
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
	private EditText content;
	
	/**
	 * 
	 */
	private Button saveButton;
	
	/**
	 * 
	 */
	private Button closeButton;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textedit);
        
        // Get the information that was passed into the intent
        Bundle extras = this.getIntent().getExtras();
        this.file = (File) extras.getSerializable("file");
        this.encryption = (String) extras.getString("encryption");
        this.password = (String) extras.getString("password");
        Log.i(OpenNoteSecure.TAG, "File: " + this.file.getPath());
        Log.i(OpenNoteSecure.TAG, "Encryption: " + this.encryption);
        Log.i(OpenNoteSecure.TAG, "Password: " + this.password); // TODO: Remove this line!
        
        // Get all of the local GUI elements
        this.content = (EditText) this.findViewById(R.id.EditTextContent);
        this.saveButton = (Button) this.findViewById(R.id.ButtonSaveContent);
        this.saveButton.setOnClickListener(this);
        this.closeButton = (Button) this.findViewById(R.id.ButtonCloseContent);
        this.closeButton.setOnClickListener(this);
        
        // Display the progress dialog
        ProgressDialog dialog = ProgressDialog.show(TextEditorDisplay.this, "", "Decrypting. Please wait...", true);
        dialog.setCancelable(false);
        dialog.show();
        
        // Start the AsyncTask to decrypt the file
        FileTaskHolder holder = new FileTaskHolder(this.file, this.encryption, this.password, dialog, this.content);
        new DecryptionTask().execute(holder);
    }
    
    /**
     * 
     * @param view
     */
	@Override
	public void onClick(View view) {
		if(view.equals(this.saveButton)){
			Log.i(OpenNoteSecure.TAG, "The save button has been clicked.");
			
			if(this.content.isEnabled()){
				// Display the progress dialog.
		        ProgressDialog dialog = ProgressDialog.show(TextEditorDisplay.this, "", "Saving. Please wait...", true);
		        dialog.setCancelable(false);
		        dialog.show();
		        
				// Start the AsyncTask to save the file
		        FileTaskHolder holder = new FileTaskHolder(this.file, this.encryption, this.password, dialog, this.content);
		        new EncryptionTask().execute(holder);
			}
			else{
				Context context = getApplicationContext();
				CharSequence text = "Save disabled.";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		}
		else if(view.equals(this.closeButton)){
			Log.i(OpenNoteSecure.TAG, "The close button has been clicked.");
			this.PerformCleanupAndClose();
		}
		else{
			Log.e(OpenNoteSecure.TAG, "An unknown button was clicked.");
		}
	}
	
	/**
	 * Customize the back button action.
	 */
	@Override
	public void onBackPressed() {
	    Log.i(OpenNoteSecure.TAG, "The back button was pressed.");
	    this.PerformCleanupAndClose();
	    return;
	}
	
	/**
	 * Remove references to sensitive information and suggest the 
	 * garbage collector runs before finishing the activity.
	 */
	private void PerformCleanupAndClose(){
		// Remove all of the references to the sensitive variables
		this.content.setText("");
		this.password = "";
		
		// Tell the system we want to run the garbage collector
		System.gc();
		
		// Close this activity
		this.finish();
	}
}
