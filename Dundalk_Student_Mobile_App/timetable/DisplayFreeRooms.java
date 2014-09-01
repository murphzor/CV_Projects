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
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.studentappscreens.R;

public class DisplayFreeRooms extends ListActivity {

	private ArrayList<Integer> times;
	private ArrayList<TimetableRoom> searchableList;
	
	private Day day1 = new Day("Monday");
	private Day day2 = new Day("Tuesday");
	private Day day3 = new Day("Wednesday");
	private Day day4 = new Day("Thursday");
	private Day day5 = new Day("Friday");
	
	private ArrayList<Day> days;
	private ArrayAdapter<String> adapter;
	
	String zone;
	int time;
	String selectedDay;
	Document doc = null;
	private ArrayList<String> freeRoomList;
	
	static ArrayList<SingleRoom> monClasses = new ArrayList<SingleRoom>();
	static ArrayList<SingleRoom> tuesClasses = new ArrayList<SingleRoom>();
	static ArrayList<SingleRoom> wedClasses = new ArrayList<SingleRoom>();
	static ArrayList<SingleRoom> thurClasses = new ArrayList<SingleRoom>();
	static ArrayList<SingleRoom> friClasses = new ArrayList<SingleRoom>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_free_rooms);
		searchableList = new ArrayList<TimetableRoom>();

		days = new ArrayList<Day>();
		freeRoomList = new ArrayList<String>();
		day1 = new Day("Monday");
		day2 = new Day("Tuesday");
		day3 = new Day("Wednesday");
		day4 = new Day("Thursday");
		day5 = new Day("Friday");
		days.add(day1);
		days.add(day2);
		days.add(day3);
		days.add(day4);
		days.add(day5);
		
		Intent intent = getIntent();
		selectedDay = intent.getStringExtra("day");
		time = intent.getIntExtra("time", time);
		zone = intent.getStringExtra("zone");
		System.out.println(zone);
		
		Log.d("TIME",Integer.toString(time));
		Log.d("DAY",selectedDay);
		
		new PopulateList().execute();
		this.runOnUiThread(new Runnable() {
			  public void run() {
			    Toast.makeText(DisplayFreeRooms.this, "This may take some time", Toast.LENGTH_LONG).show();
			  }
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_free_rooms, menu);
		return true;
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

        } 
        catch (Exception e) {        	
            DisplayFreeRooms.this.runOnUiThread(new Runnable() {
                public void run() {
                  Toast.makeText(getApplicationContext(), "Couldn't fetch timetables, try again later.",
                  Toast.LENGTH_SHORT).show();
                }
              });
        } 

        Log.d("AAAAAAAA", "goodbye");
        return listOfRooms;
    }

    @Override
    protected void onPostExecute(ArrayList<TimetableRoom> listOfTimetables) {
//    	Collections.sort(listOfTimetables, new Comparator<TimetableRoom>() {
//    	    public int compare(TimetableRoom result1, TimetableRoom result2) {
//    	        return result1.getTitle().compareTo(result2.getTitle());
//    	    }
//    	});
    	System.out.println(listOfRooms.size());
        for(int i =0;i<listOfRooms.size();i++)
        {
            String size = Integer.toString(listOfRooms.size());
           //Log.d("SIZE", size);
            String a = Integer.toString(i);
            Log.d("zone", zone);
        	if(listOfRooms.get(i).getIdentifier().equals(zone))
        	{
        		//Log.d("IDENT", listOfRooms.get(i).getIdentifier());
        		searchableList.add(listOfRooms.get(i));
                //Log.d("searchableList", searchableList.get(i).getTitle());
        	}
        }
    	
    	new CreateTimetablesTask().execute();
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
		
		public void createTimetable(ArrayList<SingleRoom> list, Elements elements, Day day)
		{
			list.clear();
			//Log.d("createTimetable","sdsddssd");
			for(int i=0;i<elements.size();i=i+7)
			{
				SingleRoom sr = new SingleRoom();
				list.add(sr);
			}
			
			for(int i=0, j=0;i<elements.size();i=i+7,j++)
			{
				list.get(j).setStartTime(elements.get(i).text());
				list.get(j).setEndTime(elements.get(i+1).text());
				list.get(j).setWeeks(elements.get(i+2).text());
				list.get(j).setClassTitle(elements.get(i+3).text());
				list.get(j).setType(elements.get(i+4).text());
				list.get(j).setLecturer(elements.get(i+5).text());
				list.get(j).setGroups(elements.get(i+6).text());
			}
			
			
			day.setRooms(list);
			//for(int i = 0;i<list.size();i++)
			//Log.d("createTimetable",day.getDay()+ list.get(i).toString());
		}	
		
		public void findFreeTimes(Day day, String roomName)
		{
			Log.d("SIZE",Integer.toString(freeRoomList.size()));
			//Log.d("TIME",time);
			Log.d("DAY",selectedDay);
			ArrayList<SingleRoom> srs = day.getRooms();
			System.out.println(time);
			Log.d("day",roomName);
			times = new ArrayList<Integer>();
//			times.add(9);
//			times.add(10);
//			times.add(11);
//			times.add(12);
//			times.add(13);
//			times.add(14);
//			times.add(15);
//			times.add(16);
			if(!srs.isEmpty())
			{
				System.out.println(time);
				for(int i = 0; i < srs.size();i++)
				{
					System.out.println(time);
					Log.d("SRS" +	"", srs.get(i).toString());
					int colonIndex =0;
					for(int j = 0; j<srs.get(i).getStartTime().length()-1;j++)
					{
						if(srs.get(i).getStartTime().substring(j,j+1).equals(":"))
						{
							colonIndex = j;
						}
					}
					int start = Integer.parseInt(srs.get(i).getStartTime().substring(0, colonIndex));
					int end = Integer.parseInt(srs.get(i).getEndTime().substring(0, 2));
					int difference = end - start;
					
					if(difference ==1)
					{
						times.add(start);
						times.add(end);
					}
					else if(difference ==2)
					{
						times.add(start);
						times.add(start+1);
						times.add(end);
					}
					else if(difference ==3)
					{		
						times.add(start);
						times.add(start+1);
						times.add(end);

					}

					

			}

				

//				for(int j = 0; j < times.size();j++)
//				{
//					if(!Integer.toString(times.get(j)).contains(time))
//					{				
//						freeRoomList.add(roomName);
//					}
//				}

					if(!times.contains(time))
					{				
						freeRoomList.add(roomName);
					}

			}
		}
		private class CreateTimetablesTask extends AsyncTask<URL, Integer, Long> 
		{
		protected Long doInBackground(URL... urls) {
			System.out.println(searchableList.size());
			for(int i = 0;i<searchableList.size();i++)
			{
				try {
					//Log.d("IIIIIII","GENERATING");
					doc = Jsoup.connect("http://timetables.dkit.ie/process.php?url=http%3A//timetable-01.ad.dkit.ie%3A8080/reporting/textspreadsheet%3Blocation%3Bid%3B"+changeURL(searchableList.get(i).getValue())+"%250D%250A%3Ft%3Dlocation+textspreadsheet%26days%3D1-5%26weeks%3D25%26periods%3D1-24%26template%3Dlocation+textspreadsheet").get();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Element monday = doc.select("p ~ table").first();
				Element tuesday = doc.select("p ~ table").get(1);
				Element wednesday = doc.select("p ~ table").get(2);
				Element thursday = doc.select("p ~ table").get(3);
				Element friday = doc.select("p ~ table").get(4);
				
				monday.select("tr:eq(0)").remove();
				tuesday.select("tr:eq(0)").remove();
				wednesday.select("tr:eq(0)").remove();
				thursday.select("tr:eq(0)").remove();
				friday.select("tr:eq(0)").remove();
		
				
				final Elements mon = monday.select("td");
				final Elements tues = tuesday.select("td");
				final Elements wed = wednesday.select("td");
				final Elements thurs = thursday.select("td");
				final Elements fri = friday.select("td");	
				
				createTimetable(monClasses, mon, day1);
				createTimetable(tuesClasses, tues, day2);
				createTimetable(wedClasses, wed, day3);
				createTimetable(thurClasses, thurs, day4);
				createTimetable(friClasses, fri, day5);
				
				//Log.d("SADASD","GENERATED");
				for(int j =0;j<days.size();j++)
				{
					if(selectedDay.equalsIgnoreCase(days.get(j).getDay()))
					{
						findFreeTimes(days.get(j),searchableList.get(i).getTitle());
					}
				}
			}
			return null;
			
		
		}
		
		
		protected void onPostExecute(Long result) 
		{
			Log.d("POST EXECUTE",Integer.toString(freeRoomList.size()));
			for(int i =0;i<freeRoomList.size();i++)
			{
				Log.d("FREE",freeRoomList.get(i));
			}
			
			adapter = new ArrayAdapter<String>(DisplayFreeRooms.this,
					  android.R.layout.simple_list_item_1, freeRoomList);
			    	setListAdapter(adapter);
		}
	}
}
	
	

