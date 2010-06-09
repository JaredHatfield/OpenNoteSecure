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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class OpenNoteSecure extends Activity {
	public static String TAG = "OpenNoteSecure";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Log.i(OpenNoteSecure.TAG, "Main application launch");
        
        // NOTE: This is just some test code
        Button b = (Button) this.findViewById(R.id.SaveButton);
        b.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
            	Log.i(OpenNoteSecure.TAG, "The button was clicked.");
            	
            	try {
            	    File root = Environment.getExternalStorageDirectory();
            	    if (root.canWrite()){
            	        File thefile = new File(root, "testfile.txt");
            	        Log.i(OpenNoteSecure.TAG, "Writing to: " + thefile.getAbsolutePath());
            	        FileWriter filewriter = new FileWriter(thefile);
            	        BufferedWriter output = new BufferedWriter(filewriter);
            	        output.write("Hello world");
            	        output.close();
            	        filewriter.close();
            	    }
            	    else{
            	    	Log.e(OpenNoteSecure.TAG, "The directory is not writable.");
            	    }
            	} catch (IOException e) {
            	    Log.e(OpenNoteSecure.TAG, "Could not write file " + e.getMessage());
            	}
            }
        });
    }
}