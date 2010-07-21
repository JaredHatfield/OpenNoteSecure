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

import android.widget.TextView;

/**
 * The holder for a file.
 * @author Jared Hatfield
 */
public class FileViewHolder {
	
	/**
	 * The text representation of the file.
	 */
	private TextView text;
	
	/**
	 * Initializes a new instance of FileViewHolder.
	 */
	public FileViewHolder(){
		this.text = null;
	}
	
	/**
	 * Gets the text.
	 * @return The text.
	 */
	public TextView getText(){
		return this.text;
	}
	
	/**
	 * Sets the text.
	 * @param text The new text.
	 */
	public void setText(TextView text){
		this.text = text;
	}
}
