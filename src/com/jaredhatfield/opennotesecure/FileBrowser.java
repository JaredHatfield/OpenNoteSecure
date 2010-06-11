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
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class FileBrowser extends ListActivity implements OnItemClickListener  {
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
		
		// FileViewHolder holder = (FileViewHolder)view.getTag();
		// Toast.makeText(getApplicationContext(), holder.getText().getText(), Toast.LENGTH_SHORT).show();
    }
}
