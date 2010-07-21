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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

/**
 * Provides an interface for performing common file manipulations.
 * @author Jared Hatfield
 */
public class FileManager {
	
	/**
	 * The singleton instance of the FileManager
	 */
	private static FileManager instance;
	
	/**
	 * The root pointer to the file system.
	 */
	private File root;
	
	/**
	 * Gets the singleton instance of FileManager.
	 * @return
	 */
	public static FileManager Instance(){
		if(FileManager.instance == null){
			FileManager.instance = new FileManager();
		}
		
		return FileManager.instance;
	}
	
	/**
	 * Initializes a new instance of the FileManager class.
	 */
	private FileManager(){
		this.root = Environment.getExternalStorageDirectory();
	}
	
	/**
	 * Updates the list of files that can be accessed.
	 * @param currentList The current list of files.
	 */
	public void updateFileList(FileArrayAdapter currentList){
		// Replace the list of files
		currentList.clear();
		if(this.root.canRead()){
			FilenameFilter filter = new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			    	return name.endsWith(".txt");
			    }
			};
			
			File[] f = this.root.listFiles(filter);
			for(int i = 0; i < f.length; i++){
				String file = f[i].getName();
				Log.i(OpenNoteSecure.TAG, "Found: " + file);
				currentList.add(f[i]);
			}
			
			currentList.notifyDataSetChanged();
		}
	}
	
	/**
	 * Writes a string to a file.
	 * @param file The file.
	 * @param content The content to write.
	 */
	public void writeFile(File file, String content){
    	try {
    	    if (file.canWrite()){
    	        Log.i(OpenNoteSecure.TAG, "Writing to: " + file.getAbsolutePath());
    	        
    	        FileWriter filewriter = new FileWriter(file);
    	        BufferedWriter output = new BufferedWriter(filewriter);
    	        output.write(content);
    	        output.close();
    	        filewriter.close();
    	    }
    	    else {
    	    	Log.e(OpenNoteSecure.TAG, "The file is not writable.");
    	    }
    	} 
    	catch (IOException e) {
    	    Log.e(OpenNoteSecure.TAG, "Could not write file ", e);
    	}
	}
	
	/**
	 * Creates a new file.
	 * @param filename The file to create.
	 */
	public Boolean writeNewFile(String filename){
		Log.i(OpenNoteSecure.TAG, "Will be creating " + filename);
		File file = new File(this.root, filename);
		try {
			if(!file.exists()){
				file.createNewFile();
				return true;
			}
		} catch (IOException e) {
			Log.e(OpenNoteSecure.TAG, "File could not be created");
		}
		
		return false;
	}
	
	/**
	 * Reads the contents of a file.
	 * @param file The file to read.
	 * @return The contents of the file as a string.
	 */
	public String readFile(File file){
		String result = "";
		
		try {
			if(file.canRead()){
				FileReader filereader = new FileReader(file);
				BufferedReader input = new BufferedReader(filereader);
				String in = input.readLine();
				while(in != null){
					result += in + "\n";
					in = input.readLine();
				}
				
				input.close();
				filereader.close();
			}
			else{
				Log.e(OpenNoteSecure.TAG, "The file is not readable.");
			}
		}
		catch(IOException e){
			Log.e(OpenNoteSecure.TAG, "Could not read the file", e);
		}
		
		return result;
	}
}
