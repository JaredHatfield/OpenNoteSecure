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
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
	
	int n = 0;
	
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
        
        // Read in the file
        // TODO: put this in an async task
        this.content.setText(FileManager.Instance().readFile(this.file));
    }
    
    /**
     * 
     * @param view
     */
	@Override
	public void onClick(View view) {
		if(view.equals(this.saveButton)){
			Log.i(OpenNoteSecure.TAG, "The save button has been clicked.");
			FileManager.Instance().writeFile(this.file, this.content.getText().toString());
		}
		else{
			Log.e(OpenNoteSecure.TAG, "An unknown button was clicked.");
		}
	}
}
