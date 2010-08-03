/*
 * OpenNoteSecure is an Android application for reading and writing
 * encrypted text files to an SD card.
 * Copyright (C) 2010 Jared Hatfield
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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The activity for displaying the text edit window.
 * @author Jared Hatfield
 */
public class TextEditorDisplay extends Activity implements OnClickListener {
	
	/**
	 * The file that is being edited.
	 */
	private File file;
	
	/**
	 * The encryption algorithm that is being used.
	 */
	private String encryption;
	
	/**
	 * The password for the file
	 */
	private String password;
	
	/**
	 * The EditText that contains the decrypted text.
	 */
	private EditText content;
	
	/**
	 * The button for saving the content.
	 */
	private Button saveButton;
	
	/**
	 * The button for closing the window.
	 */
	private Button closeButton;
	
	/**
	 * Called when the activity is first created.
	 * @param savedInstanceState The bundle that contains the saved instance.
	 */
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
     * Deal with a button click.
     * @param view The view that was clicked.
     */
	@Override
	public void onClick(View view) {
		if(view.equals(this.saveButton)){
			// The save button was clicked.
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
			// The close button was clicked.
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
		// Clean up the sensitive information before closing the activity.
	    Log.i(OpenNoteSecure.TAG, "The back button was pressed.");
	    this.PerformCleanupAndClose();
	    return;
	}
	
	/**
	 * Add the menu items to this activity.
	 * @param menu The menu to create.
	 * @return True if the menu was created.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}
	
	/**
	 * Process the clicks from the menu.
	 * @param item The item that was clicked.
	 * @return True if the item was clicked.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
	    switch (item.getItemId()) {
	    case R.id.menu_item_help:
	    	// Display the help menu
	    	try{
				Intent i = new Intent(TextEditorDisplay.this, HelpView.class);
				TextEditorDisplay.this.startActivity(i);
			}
			catch(Exception e){
				Log.e(OpenNoteSecure.TAG, "Intent could not be created." , e);
			}
			
	        return true;
	    default:
	    	// No clue what happens
	        return super.onOptionsItemSelected(item);
	    }
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
