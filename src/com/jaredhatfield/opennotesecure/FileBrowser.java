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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
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
		//this.setListAdapter(this.content);
		this.getListView().setAdapter(this.content);
		
		// Add the listener that detects when the button was pressed
		this.getListView().setOnItemClickListener(this);
		
		// Register all list items for the context menu
		registerForContextMenu(getListView());
	}
	
	/**
	 * 
	 */
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
	 * Add the menu items to this activity.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}
	
	/**
	 * Process the clicks from the menu.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
	    switch (item.getItemId()) {
	    case R.id.menu_item_help:
	    	try{
				Intent i = new Intent(FileBrowser.this, HelpView.class);
				FileBrowser.this.startActivity(i);
			}
			catch(Exception e){
				Log.e(OpenNoteSecure.TAG, "Intent could not be created." , e);
			}
			
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * Display the context menu for a specific item.
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.edit_menu, menu);
	  Log.i(OpenNoteSecure.TAG, "onCreateContextMenu");
	}
	
	/**
	 * Perform the action specified in the context menu.
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Get the selected file
		AdapterView.AdapterContextMenuInfo cmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo ();
	    File f = (File)getListView ().getItemAtPosition (cmi.position);
	    
	    // Determine which button was clicked
		switch (item.getItemId()) {
			case R.id.menu_item_delete:
				new DeleteFileTask().execute(f);
				Log.i(OpenNoteSecure.TAG, "Delete button pressed");
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}
	
	/**
	 * Perform a file deletion.
	 */
	private class DeleteFileTask extends AsyncTask<File, Void, String>{

		/**
		 * Delete the file.
		 */
		@Override
		protected String doInBackground(File... params) {
			Log.i(OpenNoteSecure.TAG, "Delete doInBackground");
			File file = params[0];
			if(file != null && file.exists()){
				Log.i(OpenNoteSecure.TAG, "Deleting " + file.getName());
				file.delete();
				return file.getName();
			}
			else{
				return null;
			}
		}
		
		/**
	      * Notify the user the file was deleted and update the ArrayAdapter.
	      */
	     protected void onPostExecute(String filename) {
	    	 Log.i(OpenNoteSecure.TAG, "Delete onPostExecute");
	    	 if(filename != null){
	    		 Toast.makeText(getApplicationContext(), "Deleted " + filename, Toast.LENGTH_SHORT).show();
	    		 FileManager.Instance().updateFileList(content);
	    	 }
	    	 else{
	    		 Toast.makeText(getApplicationContext(), "Error: " + filename + " not deleted.", Toast.LENGTH_SHORT).show();
	    	 }
	     }

	}
	
	/**
	 * Creates a new file.
	 */
	private class CreateNewFile extends AsyncTask<String, Boolean, String> {
		/**
		 * Create the new file with the specified name.
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
	      * Notify the user the file was created and update the ArrayAdapter.
	      */
	     protected void onPostExecute(String filename) {
	    	 newFileButton.requestFocus();
	    	 newFileEditText.getText().clear();
	    	 
	    	 Toast.makeText(getApplicationContext(), "Created " + filename, Toast.LENGTH_SHORT).show();
	    	 FileManager.Instance().updateFileList(content);
	     }
	 }
}
