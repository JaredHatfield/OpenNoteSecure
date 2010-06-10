package com.jaredhatfield.opennotesecure;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.view.View;

public class FileBrowser extends ListActivity  {
	
	private ArrayList<String> content;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.content = new ArrayList<String>();
		
		
		super.onCreate(savedInstanceState);
		
		
		// Add the header for adding new files
		LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.newfile, null);
		this.getListView().addHeaderView(v);
		
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, content));
		// getListView().setTextFilterEnabled(true);
		
		
		// Some dummy code that will show which button was pressed
		this.getListView().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
		    }
		});
	}
}
