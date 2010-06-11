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
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

public class OpenNoteSecure extends Activity {
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
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Locate all of the GUI elements
        this.filename = (TextView) this.findViewById(R.id.EditTextFileName);
        this.encryption = (Spinner) this.findViewById(R.id.SpinnerAlgorithm);
        this.password = (TextView) this.findViewById(R.id.EditTextPassword);
        
        // Get the information that was passed into the intent
        Bundle extras = this.getIntent().getExtras();
        this.file = (File) extras.getSerializable("file");
        Log.i(OpenNoteSecure.TAG, file.getPath());
        
        // Display the filename
        this.filename.setText(this.file.getName());
        this.filename.setEnabled(false);
    }
}