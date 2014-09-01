package com.example.studentappscreens.timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.studentappscreens.R;
import com.example.studentappscreens.R.layout;
import com.example.studentappscreens.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//http://stackoverflow.com/questions/5703330/saving-arraylists-in-sqlite-databases
public class DisplayLocalTimetable extends ListActivity {

	private FullTimetable ftt;
	private ArrayAdapter<FullTimetable> adapter;
	private DbController dbController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_local_timetable);
		
		dbController = new DbController(this);
		dbController.open();

		ArrayList<FullTimetable> entries = dbController.getEntries();
		ArrayList<SingleClass> mon;
		
//		if(!entries.isEmpty())
//		{
//		String a = entries.get(0).getFriClasses();
//		 JSONObject json = null;
//
//			try {
//				json = new JSONObject(a);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			
			//Log.d("JSONSTRING", json.toString());
			//Log.d("AAAAAAAAAA", entries.get(0).getFriClasses());
			//String size = Integer.toString(entries.size());
			//Log.d("SIZE", size);
//		  ArrayList<SingleClass> items = json.optJSONArray(a);
//		  json.op
		
//		 JSONObject json = new JSONObject(dbController);
//		  ArrayList items = json.optJSONArray("monList");

		adapter = new ArrayAdapter<FullTimetable>(this, android.R.layout.simple_list_item_1, entries);

		setListAdapter(adapter);

		adapter.notifyDataSetChanged();
		//}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_local_timetable, menu);
		return true;
	}
	
//	protected void onListItemClick(final ListView l, View arg1, final int position, final long id)
//	{
//		this.runOnUiThread(new Runnable() {
//			  public void run() {
//
//				//FullTimetable ftt = (FullTimetable) l.getItemAtPosition(position);
//			    Intent displayTimetable = new Intent(DisplayLocalTimetable.this, DisplaySingleLocalTimetable.class);
//			    displayTimetable.putExtra("position", position);
//			    DisplayLocalTimetable.this.startActivity(displayTimetable);
//
//			  }
//			});
//
//}

	@Override
	protected void onListItemClick(ListView l, View v, final int position, long id) 
	{
		super.onListItemClick(l, v, position, id);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Select an option").setTitle("Options");

		builder.setPositiveButton("Show", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int id)
			{
				if (getListAdapter().getCount() > 0) 
				{
				    Intent displayTimetable = new Intent(DisplayLocalTimetable.this, DisplaySingleLocalTimetable.class);
				    displayTimetable.putExtra("position", position);
				    DisplayLocalTimetable.this.startActivity(displayTimetable);
				}
			}
			});
		
		builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int id) 
			{

				if (getListAdapter().getCount() > 0) {
					ftt = (FullTimetable) getListAdapter().getItem(
							position);
					String p = Integer.toString(position);
					Log.d("DELETE", p);
					Log.d("AAAAAA", Long.toString(ftt.getId()));
					//ftt.setId(id);
					dbController.deleteEntry(position);
					adapter.remove(ftt);
				}

				adapter.notifyDataSetChanged();
			}
			});

			AlertDialog dialog = builder.create();
			dialog.show();

	}
	
}