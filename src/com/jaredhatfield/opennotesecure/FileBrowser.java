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
import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public class FileBrowser extends ListActivity 
	implements OnItemClickListener, OnClickListener  {
	/**
	 * 
	 */
	private FileArrayAdapter content;
	
	/**
	 * 
	 */
	private LayoutInflater mInflater;
	
	/**
	 * 
	 */
	private EditText newFileEditText;
	
	/**
	 * 
	 */
	private Button newFileButton;
	
	/**
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.content = new FileArrayAdapter(this, android.R.layout.simple_list_item_1,
				new ArrayList<File>());
		FileManager.Instance().updateFileList(this.content);
		
		super.onCreate(savedInstanceState);
		
		// Get the layout inflater
		mInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		// Add the header for adding new files
		View v = mInflater.inflate(R.layout.newfile, null);
		this.getListView().addHeaderView(v);
		this.newFileEditText = (EditText)v.findViewById(R.id.EditTextNewFile);
		this.newFileButton = (Button)v.findViewById(R.id.ButtonNewFile);
		this.newFileButton.setOnClickListener(this);
		
		// Add the ArrayAdapter to the ListView
		this.setListAdapter(this.content);
		
		// Add the listener that detects when the button was pressed
		this.getListView().setOnItemClickListener(this);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Deal with the click
		File f = (File) parent.getItemAtPosition(position);
		
		try{
			Intent i = new Intent(FileBrowser.this, OpenNoteSecure.class);
			i.putExtra("file", f);
			FileBrowser.this.startActivity(i);
		}
		catch(Exception e){
			Log.e(OpenNoteSecure.TAG, "Intent could not be created." , e);
		}
    }
	
	/**
     * 
     * @param view
     */
	@Override
	public void onClick(View view) {
		if(view.equals(this.newFileButton)){
			Log.i(OpenNoteSecure.TAG, "The new file button has been clicked.");
			String filename = this.newFileEditText.getText().toString();
			if(!filename.endsWith(".txt")){
				filename += ".txt";
			}
			
			Toast.makeText(getApplicationContext(), "Creating file " + filename, Toast.LENGTH_SHORT).show();
			new CreateNewFile().execute(filename);
		}
		else{
			Log.e(OpenNoteSecure.TAG, "An unknown button was clicked.");
		}
	}
	
	/**
	 * 
	 */
	private class CreateNewFile extends AsyncTask<String, Boolean, String> {
		/**
		 * 
		 */
	     protected String doInBackground(String... files) {
	         String filename  = files[0];
	         try{
	        	 FileManager.Instance().writeNewFile(filename);
	         }
	         catch(Exception e){
	        	 Log.e(OpenNoteSecure.TAG, e.getMessage(), e);
	         }
	         
	    	 return filename;
	     }
	     
	     /**
	      * 
	      */
	     protected void onPostExecute(String filename) {
	    	 newFileButton.requestFocus();
	    	 newFileEditText.getText().clear();
	    	 
	    	 Toast.makeText(getApplicationContext(), "Created " + filename, Toast.LENGTH_SHORT).show();
	    	 FileManager.Instance().updateFileList(content);
	     }
	 }
}
