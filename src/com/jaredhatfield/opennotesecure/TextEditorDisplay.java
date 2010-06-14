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

public class TextEditorDisplay extends Activity {
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
        
    }
}
