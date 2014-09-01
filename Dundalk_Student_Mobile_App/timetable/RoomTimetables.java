//todo
//populate list
//user picks timetable to generate

package com.example.studentappscreens.timetable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studentappscreens.R;

public class RoomTimetables extends ListActivity 
{
	String[] studsetarray = new String[324];
	private ListView mainListView ;  
	private ArrayAdapter<TimetableRoom> adapter;
	ArrayList<TimetableRoom> listOfRooms;
//	ArrayList<String> stringFilter;
//	ArrayList<DepartmentFilter> dp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room1);
//
//		stringFilter = new ArrayList<String>();
//		stringFilter.add("ICOM - Computing & Mathematics");
//		stringFilter.add("ICOM");
//		stringFilter.add("NNUR - Nursing, Midwifery and Health Studies");
//		stringFilter.add("NNUR");
//		dp = new ArrayList<DepartmentFilter>();
//		for(int i = 0; i < stringFilter.size();i=i+2)
//		{
//			DepartmentFilter d = new DepartmentFilter(stringFilter.get(i), stringFilter.get(i+1));
//		}
		
//		PopulateList task=new PopulateList();
//		task.execute();
		
		fillList();
		this.runOnUiThread(new Runnable() {
			  public void run() {
			    Toast.makeText(RoomTimetables.this, "Retrieving", Toast.LENGTH_SHORT).show();
			  }
			});
		
		mainListView = getListView();
		
//	    listView.setOnItemClickListener(new OnItemClickListener() {
//	    	  @Override
//	    	  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//	    		  String s = (String) listView.getItemAtPosition(position);
//	    		  changeURL(s);
//	    		  getTimetable(s);
//	    	  }
//	    	}); 	           
		  
	}
	
	protected void onListItemClick(final ListView l, View arg1, final int position, final long id)
	{
		this.runOnUiThread(new Runnable() {
			  public void run() {
				  //TimetableClass tc =  (TimetableClass) l.getItemAtPosition(position);
				  //Object  o = l.getItemAtPosition(position);
				  TimetableRoom tr = (TimetableRoom) l.getItemAtPosition(position);
				  Log.d("VALUE",tr.getValue());
		        String oURL = "http://timetables.dkit.ie/process.php?url=http%3A//timetable-01.ad.dkit.ie%3A8080/reporting/textspreadsheet%3Blocation%3Bid%3B"+changeURL(tr.getValue())+"%250D%250A%3Ft%3Dlocation+textspreadsheet%26days%3D1-5%26weeks%3D25%26periods%3D1-24%26template%3Dlocation+textspreadsheet";
//				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(oURL));
//				startActivity(browserIntent);
			    Intent displayTimetable = new Intent(RoomTimetables.this, DisplayRoomTimetable.class);
			    displayTimetable.putExtra("url", oURL); //Optional parameters
			    RoomTimetables.this.startActivity(displayTimetable);

			  }
			});
		
	}
	private class PopulateList extends AsyncTask<Void, Void, ArrayList<TimetableRoom>>
	{
        ArrayList<TimetableRoom> listOfRooms = new ArrayList<TimetableRoom>();
    protected ArrayList<TimetableRoom> doInBackground(Void... urls) {
        try {
            URL url = new URL("http://timetables.dkit.ie/js/filter.js");

            Log.d("url", url.toString());
            
            BufferedReader in = new BufferedReader(
            new InputStreamReader(url.openStream()));

            ArrayList<String> list = new ArrayList<String>();
            
            String inputLine;
            int addToList = 0;
            while ((inputLine = in.readLine()) != null)
            {
            	//Log.d("while", inputLine);
            	if(inputLine.contains("roomarray[i] = new Array(2)") || addToList >= 1)
            	{
            		addToList = addToList+1;
        	        	if(inputLine.contains("roomarray["))
        	        	{
        	        		list.add(inputLine);
        	            	//Log.d("reading", inputLine);
        	        	}
            	}
            }
            in.close();
            
            
            for(int i = 0; i<list.size();i++)
            {
            	
            	if(list.get(i).contains("roomarray[i]"))
            	{
            		list.remove(i);
            		i--;
            	}
            }
            
            
            //for every 3 elements in the list
            //create 1 TimeTableClass object
            
            for(int i = 0, j = 0; i<list.size();i=i+3, j++)
            {
            	//TERMINATES HERE??
            	TimetableRoom tr = new TimetableRoom();
            	//Log.d("print", list.get(i));
            	listOfRooms.add(tr);
            }
            
            for(int i = 0, j = 0; i<list.size();i=i+3, j++)
            {
            	//Log.d("print", list.get(i));
            	listOfRooms.get(j).setTitle(list.get(i));
            	listOfRooms.get(j).setIdentifier(list.get(i+1));
            	listOfRooms.get(j).setValue(list.get(i+2));
            }
            
            
            //the next 3 methods remove all the unwanted characters from each of the 3 field in a TimeTableClass object
            
            for(int i = 0; i<listOfRooms.size();i++)
            {
            	String s = listOfRooms.get(i).getTitle();
            	boolean parse = false;
            	for(int j =0;j<s.length();j++)
            	{
            		if(s.subSequence(j, j+1).equals("="))
            		{
            			parse = true;
            			if(parse == true)
            			{
            				listOfRooms.get(i).setTitle(s.substring(j+3, s.length()-2));
            				parse = false;
            			}
            		}
            		
            	}
            }
            
            for(int i = 0; i<listOfRooms.size();i++)
            {
            	String s = listOfRooms.get(i).getIdentifier();
            	boolean parse = false;
            	for(int j =0;j<s.length();j++)
            	{
            		if(s.subSequence(j, j+1).equals("="))
            		{
            			parse = true;
            			if(parse == true)
            			{
            				listOfRooms.get(i).setIdentifier(s.substring(j+3, s.length()-2));
            				parse = false;
            			}
            		}
            		
            	}
            }
            
            for(int i = 0; i<listOfRooms.size();i++)
            {
            	String s = listOfRooms.get(i).getValue();
            	boolean parse = false;
            	for(int j =0;j<s.length();j++)
            	{
            		if(s.subSequence(j, j+1).equals("="))
            		{
            			parse = true;
            			if(parse == true)
            			{
            				listOfRooms.get(i).setValue(s.substring(j+3, s.length()-2));
            				parse = false;
            			}
            		}
            		
            	}
            }            
//            	String fdsf  = Integer.toString(listOfTimetables.size());
//            	Log.d("size", fdsf);
//        	
//                for(int i = 0; i < listOfTimetables.size();i++)
//                {
//                	Log.d("adasdad", (listOfTimetables.get(i).getTitle()));
//                }
            
        } catch (Exception e) {        	
            RoomTimetables.this.runOnUiThread(new Runnable() {
                public void run() {
                  Toast.makeText(getApplicationContext(), "Couldn't fetch tiemtables, try again later.",
                  Toast.LENGTH_SHORT).show();
                }
              });
        }     

//        for(int i =0;i<listOfRooms.size();i++)
//        {
//        Log.d("aaa",listOfRooms.get(i).getTitle());
//        }
        
        return listOfRooms;
    }

    @Override
    protected void onPostExecute(ArrayList<TimetableRoom> listOfTimetables) {
    	Collections.sort(listOfTimetables, new Comparator<TimetableRoom>() {
    	    public int compare(TimetableRoom result1, TimetableRoom result2) {
    	        return result1.getTitle().compareTo(result2.getTitle());
    	    }
    	});
    	for(int i = 0;i<listOfTimetables.size();i++)
    	{
    		            	    	Log.d("value",listOfRooms.get(i).getValue());
    	}
    	
		adapter = new ArrayAdapter<TimetableRoom>(RoomTimetables.this,
		  android.R.layout.simple_list_item_1, android.R.id.text1, listOfTimetables);
    	setListAdapter(adapter);

    }
}

private void fillList() {
	Log.d("ccc","asdasd");
	PopulateList getNews = new PopulateList();
    getNews.execute();
}
	

public static String changeURL(String s)
{
	String g="";
	for (int i=0; i<s.length(); i++) 
	{
		  String c = s.substring(i,i+1);

		  if(c.equals("_"))
		  {
			  g=g+"5F";
		  }
		  else if(c.equals("%"))
		  {
			  g=g+"%25";
		  }
		  else
		  {
			  g=g+c;
		  }
	}
	System.out.println(g);
	return g;
}



}