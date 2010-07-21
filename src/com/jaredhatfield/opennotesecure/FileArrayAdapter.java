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
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * The array adapter that is used to list files.
 * @author Jared Hatfield
 */
public class FileArrayAdapter extends ArrayAdapter<File> {
	
	/**
	 * The layout inflater.
	 */
	private LayoutInflater mInflater;
	
	/**
	 * The list of files.
	 */
	private List<File> data;
	
	/**
	 * Creates a new instance of a FileArrayAdapter
	 * @param context The context for the array adapter.
	 * @param textViewResourceId The resource identifier.
	 * @param objects The list that will be used.
	 */
	public FileArrayAdapter(Context context, int textViewResourceId, List<File> objects) {
		super(context, textViewResourceId, objects);
		this.data = objects;
		
		// Get the layout inflater
		mInflater = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	/**
	 * Gets the view for a specific position in the list view.
	 * @param position The position that is being requested.
	 * @param currentView The current view that can be reused.
	 * @param parent The parent of the calling function.
	 * @return The view that will be displayed.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(OpenNoteSecure.TAG, "getView was called!");
		FileViewHolder holder;
		if (convertView == null) {
			// No view was passed in so we need to inflate a new one
			convertView = mInflater.inflate(R.layout.filerow, parent, false);
			
			// Create a new holder for the view
			holder = new FileViewHolder();
			holder.setText((TextView) convertView.findViewById(R.id.FileRowText));
			convertView.setTag(holder);
		} 
		else {
			// Retrieve the existing holder for the view
			holder = (FileViewHolder) convertView.getTag();
		}
		
		// Sets the text on the view to show the file name
		File f = this.data.get(position);
		holder.getText().setText(f.getName());
		
		// Returns the view
		return convertView;
	}
}
