package com.example.studentappscreens.timetable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentappscreens.R;


public class FindFreeRooms extends Activity 
{
	ArrayList<Zone> listOfZones;
	ArrayList<String> listOfDays;
	ArrayList<String> listOfHours;
	Spinner timeSpinner;
	Spinner zoneSpinner;
	Spinner daySpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_free_rooms);
		//Log.d("AAAAAAAAAA", Integer.toString(listOfZones.size()));
		
		listOfZones = new ArrayList<Zone>();
		listOfDays = new ArrayList<String>();
		listOfHours = new ArrayList<String>();

		Log.d("CREATE", Integer.toString(listOfZones.size()));
		populateDayList();
		populateHourList();
		
		fillList();
		Button btnOnlineActivity = (Button) findViewById(R.id.findroombutton); 
		btnOnlineActivity.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 

				String zone="";
				//PROBLEM HERE LIST OF ZONES BECOMES EMPTY FOR SOME REASON
				String day = daySpinner.getSelectedItem().toString();
				String time = timeSpinner.getSelectedItem().toString();				
				String[] timeToSend = time.split(":");
				String zo = zoneSpinner.getSelectedItem().toString();
				Log.d("CLICK", Integer.toString(listOfZones.size()));
				for(int i = 0;i<listOfZones.size();i++)
				{
					Log.d("LOOP", listOfZones.toString());
					System.out.println("LIST");
					if(listOfZones.get(i).getName().equalsIgnoreCase(zo))
					{
						zone = listOfZones.get(i).getIdentifier();
						System.out.println(zone);
					}
				}

				System.out.println(zone);
				Log.d("ZONE", zone);
				
				Intent myIntent = new Intent(FindFreeRooms.this,DisplayFreeRooms.class); 
				myIntent.putExtra("day", day);
				myIntent.putExtra("time", Integer.parseInt(timeToSend[0]));
				myIntent.putExtra("zone", zone);
				FindFreeRooms.this.startActivity(myIntent);
			
			}
		
		});

	}
	
	public void populateDayList()
	{
		listOfDays.add("Monday");
		listOfDays.add("Tuesday");
		listOfDays.add("Wednesday");
		listOfDays.add("Thursday");
		listOfDays.add("Friday");
	}
	
	public void populateHourList()
	{
		listOfHours.add("9:00 - 10:00");
		listOfHours.add("10:00 - 11:00");
		listOfHours.add("11:00 - 12:00");
		listOfHours.add("12:00 - 13:00");
		listOfHours.add("13:00 - 14:00");
		listOfHours.add("14:00 - 15:00");
		listOfHours.add("15:00 - 16:00");
		listOfHours.add("16:00 - 17:00");
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_free_rooms, menu);
		return true;
	}
	
	private class PopulateList extends AsyncTask<Void, Void, ArrayList<Zone>>
	{
    protected ArrayList<Zone> doInBackground(Void... urls) {
        try {
            URL url = new URL("http://timetables.dkit.ie/js/filter.js");

            BufferedReader in = new BufferedReader(
            new InputStreamReader(url.openStream()));

            ArrayList<String> list = new ArrayList<String>();
            
            String inputLine;
            int addToList = 0;
            while ((inputLine = in.readLine()) != null)
            {
            	//Log.d("while", inputLine);
            	if(inputLine.contains("zonearray[i] = new Array(1)")|| addToList >= 1)
            	{
            		addToList = addToList+1;
        	        	if(inputLine.contains("zonearray["))
        	        	{
        	        		list.add(inputLine);
        	            	Log.d("reading", inputLine);
        	        	}
            	}
            }
            in.close();
            
            
            for(int i = 0; i<list.size();i++)
            {
            	
            	if(list.get(i).contains("zonearray[i]"))
            	{
            		list.remove(i);
            		i--;
            	}
            }
            
            
            
            for(int i = 0, j = 0; i<list.size();i=i+2, j++)
            {
            	//TERMINATES HERE??
            	Zone z = new Zone();
            	//Log.d("print", list.get(i));
            	listOfZones.add(z);
            }
            
            for(int i = 0, j = 0; i<list.size();i=i+2, j++)
            {
            	//Log.d("print", list.get(i));
            	listOfZones.get(j).setName(list.get(i));
            	listOfZones.get(j).setIdentifier(list.get(i+1));
            }
            
            
            //the next 3 methods remove all the unwanted characters from each of the 3 field in a TimeTableClass object
            
            for(int i = 0; i<listOfZones.size();i++)
            {
            	String s = listOfZones.get(i).getName();
            	boolean parse = false;
            	for(int j =0;j<s.length();j++)
            	{
            		if(s.subSequence(j, j+1).equals("="))
            		{
            			parse = true;
            			if(parse == true)
            			{
            				listOfZones.get(i).setName(s.substring(j+3, s.length()-2));
            				parse = false;
            			}
            		}
            		
            	}
            }
            
            for(int i = 0; i<listOfZones.size();i++)
            {
            	String s = listOfZones.get(i).getIdentifier();
            	boolean parse = false;
            	for(int j =0;j<s.length();j++)
            	{
            		if(s.subSequence(j, j+1).equals("="))
            		{
            			parse = true;
            			if(parse == true)
            			{
            				listOfZones.get(i).setIdentifier(s.substring(j+3, s.length()-2));
            				parse = false;
            			}
            		}
            		
            	}
            }
       
//            	String fdsf  = Integer.toString(listOfTimetables.size());
//            	Log.d("size", fdsf);
//        	
                for(int i = 0; i < listOfZones.size();i++)
                {
                	Log.d("adasdad", listOfZones.get(i).toString());
                }
            
                for(int i =0;i<listOfZones.size();i++)
                {
                Log.d("aaa",listOfZones.get(i).toString());
                }
                
        } catch (Exception e) {        	
            FindFreeRooms.this.runOnUiThread(new Runnable() {
                public void run() {
                  Toast.makeText(getApplicationContext(), "The timetable website is currently unreachable",
                  Toast.LENGTH_SHORT).show();
                }
              });
        }     


        for(int i =0;i<listOfZones.size();i++)
        {
        Log.d("vvvvvvvv",listOfZones.get(i).toString());
        }
        
        return listOfZones;
    }

    @Override
    protected void onPostExecute(ArrayList<Zone> listOfZones) {
    	Collections.sort(listOfZones, new Comparator<Zone>() {
    	    public int compare(Zone result1, Zone result2) {
    	        return result1.getName().compareTo(result2.getName());
    	    }
    	});

    	

		
		timeSpinner = (Spinner)findViewById(R.id.timespinner);
		zoneSpinner = (Spinner)findViewById(R.id.zonespinner);
    	daySpinner = (Spinner)findViewById(R.id.dayspinner);
    	
    	ArrayAdapter<Zone> adapter1 = new ArrayAdapter<Zone>(FindFreeRooms.this, android.R.layout.simple_spinner_item, listOfZones);
    	ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(FindFreeRooms.this, android.R.layout.simple_spinner_item, listOfDays);
    	ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(FindFreeRooms.this, android.R.layout.simple_spinner_item, listOfHours);
    	

    	zoneSpinner.setAdapter(adapter1);
    	daySpinner.setAdapter(adapter2);
    	timeSpinner.setAdapter(adapter3);
    	Log.d("ADAPATERED", Integer.toString(listOfZones.size()));
    }
	}
	
	private void fillList() {
		Log.d("ccc","asdasd");
		PopulateList getNews = new PopulateList();
	    getNews.execute();
	}	

}	
	

