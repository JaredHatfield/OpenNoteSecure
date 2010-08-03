/*
 * OpenNoteSecure is an Android application for reading and writing
 * encrypted text files to an SD card.
 * Copyright (C) 2010 Jared Hatfield
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

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * The view that displays the help information.
 * @author Jared Hatfield
 */
public class HelpView extends Activity {
	
	/**
	 * Called when the activity is first created.
	 * @param savedInstanceState The saved instance state.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        
        // Load the HTML from the local html file
        WebView wv = (WebView) findViewById(R.id.webview_help);	    
	    wv.loadUrl("file:///android_asset/help.html");
    }
}
