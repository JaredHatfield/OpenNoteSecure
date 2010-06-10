package com.jaredhatfield.opennotesecure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class FileManager {
	public void writeFile(String file){
    	try {
    	    File root = Environment.getExternalStorageDirectory();
    	    
    	    if (root.canWrite()){
    	        File thefile = new File(root, file); // "testfile.txt"
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
    	} 
    	catch (IOException e) {
    	    Log.e(OpenNoteSecure.TAG, "Could not write file " + e.getMessage());
    	}
	}
}
