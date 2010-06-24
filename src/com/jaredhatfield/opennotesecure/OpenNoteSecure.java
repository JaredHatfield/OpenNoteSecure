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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

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
	 * 
	 */
	private TextView filename;
	
	/**
	 * 
	 */
	private Spinner encryption;
	
	/**
	 * 
	 */
	private TextView password;
	
	/**
	 * 
	 */
	private Button open;
	
    /** Called when the activity is first created. */
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
     * 
     * @param view
     */
	@Override
	public void onClick(View view) {
		if(view.equals(this.open)){
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
	 * 
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		if(pos == 0){
			this.password.setEnabled(false);
		}
		else{
			this.password.setEnabled(true);
		}
	}

	/**
	 * 
	 */
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		this.password.setEnabled(false);
	}
}