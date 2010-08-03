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

import java.io.File;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * The activity that lists all of the files.
 * 
 * @author Jared Hatfield
 */
public class FileBrowser extends ListActivity implements OnItemClickListener,
        OnClickListener {

    /**
     * The array adapter that lists all of the files
     */
    private FileArrayAdapter content;

    /**
     * The layout inflater.
     */
    private LayoutInflater mInflater;

    /**
     * The EditText that contains the name for new files.
     */
    private EditText newFileEditText;

    /**
     * The Button that is used to create new files.
     */
    private Button newFileButton;

    /**
     * Initializes the FileBrowser.
     * 
     * @param savedInstanceState
     *            The saved state of the application.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.content = new FileArrayAdapter(this,
                android.R.layout.simple_list_item_1, new ArrayList<File>());
        FileManager.Instance().updateFileList(this.content);
        super.onCreate(savedInstanceState);

        // Get the layout inflater
        mInflater = (LayoutInflater) getApplicationContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        // Add the header for adding new files
        View v = mInflater.inflate(R.layout.newfile, null);
        this.getListView().addHeaderView(v);
        this.newFileEditText = (EditText) v.findViewById(R.id.EditTextNewFile);
        this.newFileButton = (Button) v.findViewById(R.id.ButtonNewFile);
        this.newFileButton.setOnClickListener(this);

        // Add the ArrayAdapter to the ListView
        this.getListView().setAdapter(this.content);

        // Add the listener that detects when the button was pressed
        this.getListView().setOnItemClickListener(this);

        // Register all list items for the context menu
        registerForContextMenu(getListView());
    }

    /**
     * Called when an item in the list view is clicked.
     * 
     * @param parent
     *            The adapter view.
     * @param view
     *            The view that was clicked.
     * @param position
     *            The position of the item.
     * @param id
     *            The id of the item.
     */
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // Deal with the click
        File f = (File) parent.getItemAtPosition(position);

        try {
            // Display the interface for picking an algorithm and providing a
            // password
            Intent i = new Intent(FileBrowser.this, OpenNoteSecure.class);
            i.putExtra("file", f);
            FileBrowser.this.startActivity(i);
        } catch (Exception e) {
            Log.e(OpenNoteSecure.TAG, "Intent could not be created.", e);
        }
    }

    /**
     * Deal with a button click.
     * 
     * @param view
     *            The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if (view.equals(this.newFileButton)) {
            // The new file button was clicked
            Log.i(OpenNoteSecure.TAG, "The new file button has been clicked.");
            String filename = this.newFileEditText.getText().toString();
            if (!filename.endsWith(".txt")) {
                filename += ".txt";
            }

            // Create a new file
            new CreateNewFile().execute(filename);
        } else {
            // No idea what button was clicked!
            Log.e(OpenNoteSecure.TAG, "An unknown button was clicked.");
        }
    }

    /**
     * Add the menu items to this activity.
     * 
     * @param menu
     *            The menu to create.
     * @return True if the menu was created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Process the clicks from the menu.
     * 
     * @param item
     *            The menu item that was clicked.
     * @return True if the item was selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_item_help:
            // The help menu that was clicked
            try {
                Intent i = new Intent(FileBrowser.this, HelpView.class);
                FileBrowser.this.startActivity(i);
            } catch (Exception e) {
                Log.e(OpenNoteSecure.TAG, "Intent could not be created.", e);
            }

            return true;
        default:
            // No clue which item was clicked
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Display the context menu for a specific item.
     * 
     * @param menu
     *            The context menu.
     * @param v
     *            The view.
     * @param menuInfo
     *            THe context menu info.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        Log.i(OpenNoteSecure.TAG, "onCreateContextMenu");
    }

    /**
     * Perform the action specified in the context menu.
     * 
     * @param item
     *            The menu item.
     * @return True if the item was selected
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Get the selected file
        AdapterView.AdapterContextMenuInfo cmi = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        File f = (File) getListView().getItemAtPosition(cmi.position);

        // Determine which button was clicked
        switch (item.getItemId()) {
        case R.id.menu_item_delete:
            // Delete the selected file
            new DeleteFileTask().execute(f);
            Log.i(OpenNoteSecure.TAG, "Delete button pressed");
            return true;
        default:
            // No clue what was clicked
            return super.onContextItemSelected(item);
        }
    }

    /**
     * Performs the task of deleting a file.
     * 
     * @author Jared Hatfield
     */
    private class DeleteFileTask extends AsyncTask<File, Void, String> {

        /**
         * Delete the file.
         * 
         * @param params
         *            The file.
         * @return The file name if it was created; otherwise null.
         */
        @Override
        protected String doInBackground(File... params) {
            Log.i(OpenNoteSecure.TAG, "Delete doInBackground");
            File file = params[0];
            if (file != null && file.exists()) {
                Log.i(OpenNoteSecure.TAG, "Deleting " + file.getName());
                file.delete();
                return file.getName();
            } else {
                return null;
            }
        }

        /**
         * Notify the user the file was deleted and update the ArrayAdapter.
         * 
         * @param filename
         *            The file name; null if file was not created.
         */
        protected void onPostExecute(String filename) {
            Log.i(OpenNoteSecure.TAG, "Delete onPostExecute");
            if (filename != null) {
                // The file was created, notify the user and update the list
                Toast.makeText(getApplicationContext(), "Deleted " + filename,
                        Toast.LENGTH_SHORT).show();
                FileManager.Instance().updateFileList(content);
            } else {
                // The file was not created successfully
                Toast.makeText(getApplicationContext(),
                        "Error: " + filename + " not deleted.",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * Performs the task of creating a new file.
     * 
     * @author Jared Hatfield
     */
    private class CreateNewFile extends AsyncTask<String, Boolean, String> {
        /**
         * Create the new file with the specified name.
         * 
         * @param files
         *            The name of the file to create.
         * @return The file name that was created.
         */
        protected String doInBackground(String... files) {
            String filename = files[0];
            try {
                if (!filename.equals(".txt")) {
                    Boolean result = FileManager.Instance().writeNewFile(
                            filename);
                    if (!result) {
                        // If the file was not created we mark this down
                        Log.i(OpenNoteSecure.TAG, "File creation failed.");
                        filename = null;
                    }
                } else {
                    // Do not create a file that has no name
                    Log.i(OpenNoteSecure.TAG, "File name left blank.");
                    filename = null;
                }
            } catch (Exception e) {
                Log.e(OpenNoteSecure.TAG, e.getMessage(), e);
                Log.i(OpenNoteSecure.TAG, e.getMessage());
                filename = null;
            }

            return filename;
        }

        /**
         * Notify the user the file was created and update the ArrayAdapter.
         * 
         * @param filename
         *            The name of the file that was created or null on failure.
         */
        protected void onPostExecute(String filename) {
            if (filename == null) {
                // The file was not created
                Toast.makeText(getApplicationContext(),
                        "File was not created.", Toast.LENGTH_SHORT).show();
            } else {
                // The file was created
                newFileButton.requestFocus();
                newFileEditText.getText().clear();

                // Notify the user the file was created
                Toast.makeText(getApplicationContext(), "Created " + filename,
                        Toast.LENGTH_SHORT).show();

                // Update the array array adapter
                FileManager.Instance().updateFileList(content);
            }
        }
    }
}
