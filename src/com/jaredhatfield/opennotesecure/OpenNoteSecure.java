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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Implements the activity for choosing an encryption algorithm and entering the password.
 * @author Jared Hatfield
 */
public class OpenNoteSecure extends Activity implements OnClickListener, OnItemSelectedListener {
	
	/**
	 * The tag that is used by the logger.
	 */
	public static String TAG = "OpenNoteSecure";

	/**
	 * The file that will be used.
	 */
	private File file;
	
	/**
	 * The TextView for the file name.
	 */
	private TextView filename;
	
	/**
	 * The Spinner for choosing the encryption algorithm.
	 */
	private Spinner encryption;
	
	/**
	 * The TextView for the password.
	 */
	private TextView password;
	
	/**
	 * The button for opening the file.
	 */
	private Button open;
	
    /**
     * Called when the activity is first created.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Locate all of the GUI elements
        this.filename = (TextView) this.findViewById(R.id.EditTextFileName);
        this.encryption = (Spinner) this.findViewById(R.id.SpinnerAlgorithm);
        this.password = (TextView) this.findViewById(R.id.EditTextPassword);
        this.open = (Button) this.findViewById(R.id.ButtonOpen);
        
        // Get the information that was passed into the intent
        Bundle extras = this.getIntent().getExtras();
        this.file = (File) extras.getSerializable("file");
        Log.i(OpenNoteSecure.TAG, "File: " + file.getPath());
        
        // Display the filename
        this.filename.setText(this.file.getName());
        this.filename.setEnabled(false);
        
        // Set up the spinners
        ArrayAdapter<CharSequence> sadapter = ArrayAdapter.createFromResource(
                this, R.array.encrytion_options, android.R.layout.simple_spinner_item);
        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.encryption.setAdapter(sadapter);
        this.encryption.setOnItemSelectedListener(this);
        
        // Subscribe the listen event to the open button
        this.open.setOnClickListener(this);
    }

    /**
     * A button was clicked.
     * @param view The view for the button that was clicked.
     */
	@Override
	public void onClick(View view) {
		if(view.equals(this.open)){
			// The open button was clicked.
			Log.i(OpenNoteSecure.TAG, "The open button was clicked.");
			
			// Display the intent that will be used to edit the file
			Intent i = new Intent(OpenNoteSecure.this, TextEditorDisplay.class);
			i.putExtra("file", this.file);
			i.putExtra("encryption", (String)this.encryption.getSelectedItem());
			i.putExtra("password", this.password.getText().toString());
			OpenNoteSecure.this.startActivity(i);
		}
		else{
			Log.e(OpenNoteSecure.TAG, "An unknown button was clicked.");
		}
	}

	/**
	 * The selected item for the spinner.
	 * @param parent The adapter view.
	 * @param view The view that was clicked.
	 * @param pos The position.
	 * @param id The unique identifier.
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		if(pos == 0){
			// If "none" was selected, disable the password field
			this.password.setEnabled(false);
		}
		else{
			// If any other item was selected enable the password field
			this.password.setEnabled(true);
		}
	}

	/**
	 * If nothing was selected for the spinner.
	 * @param parent The adapter view.
	 */
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// Disable the password field since no algorithm was selected
		this.password.setEnabled(false);
	}
	
	/**
	 * Customize the back button action.
	 */
	@Override
	public void onBackPressed() {
		// The view will close, but sensitive information is removed from memory first.
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
				Intent i = new Intent(OpenNoteSecure.this, HelpView.class);
				OpenNoteSecure.this.startActivity(i);
			}
			catch(Exception e){
				Log.e(OpenNoteSecure.TAG, "Intent could not be created." , e);
			}
			
	        return true;
	    default:
	    	// No clue what happens.
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * Remove references to sensitive information and suggest the 
	 * garbage collector runs before finishing the activity.
	 */
	private void PerformCleanupAndClose(){
		// Remove all of the references to the sensitive variables
		this.password.setText("");
		
		// Tell the system we want to run the garbage collector
		System.gc();
		
		// Close this activity
		this.finish();
	}
}