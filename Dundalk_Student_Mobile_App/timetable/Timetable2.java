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

public class Timetable2 extends ListActivity 
{
	String[] studsetarray = new String[324];
	private ListView mainListView ;  
	private ArrayAdapter<TimetableClass> adapter;
	ArrayList<TimetableClass> listOfTimetables;
	private boolean proceed = false;
//	ArrayList<String> stringFilter;
//	ArrayList<DepartmentFilter> dp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable2);
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
			    Toast.makeText(Timetable2.this, "Retrieving", Toast.LENGTH_SHORT).show();
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
				TimetableClass tc = (TimetableClass) l.getItemAtPosition(position);
		        String oURL = "http://timetables.dkit.ie/process.php?url=http%3A//timetable-01.ad.dkit.ie%3A8080/reporting/textspreadsheet%3Bstudent+set%3Bid%3B"+changeURL(tc.getValue())+"%250D%250A%3Ft%3Dstudent+set+textspreadsheet%26days%3D1-5%26weeks%3D%26periods%3D1-24%26template%3Dstudent+set+textspreadsheet";
			    Intent displayTimetable = new Intent(Timetable2.this, DisplayOnlineTimetable.class);
			    displayTimetable.putExtra("url", oURL); 
			    displayTimetable.putExtra("title", tc.getTitle());
			    Timetable2.this.startActivity(displayTimetable);
			  }
			});
		
	}
	private class PopulateList extends AsyncTask<Void, Void, ArrayList<TimetableClass>>
	{
        ArrayList<TimetableClass> listOfTimetables = new ArrayList<TimetableClass>();
    protected ArrayList<TimetableClass> doInBackground(Void... urls) {
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
            	if(inputLine.contains("studsetarray[i] = new Array(2)") || addToList >= 2)
            	{
            		addToList = addToList+1;
        	        	if(inputLine.contains("studsetarray["))
        	        	{
        	        		list.add(inputLine);
        	            	//Log.d("reading", inputLine);
        	        	}
            	}
            }
            in.close();
            
            
            for(int i = 0; i<list.size();i++)
            {
            	
            	if(list.get(i).contains("studsetarray[i]"))
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
            	TimetableClass tc = new TimetableClass();
            	//Log.d("print", list.get(i));
            	listOfTimetables.add(tc);
            }
            
            for(int i = 0, j = 0; i<list.size();i=i+3, j++)
            {
            	//Log.d("print", list.get(i));
            	listOfTimetables.get(j).setTitle(list.get(i));
            	listOfTimetables.get(j).setIdentifier(list.get(i+1));
            	listOfTimetables.get(j).setValue(list.get(i+2));
            }
            
            
            //the next 3 methods remove all the unwanted characters from each of the 3 field in a TimeTableClass object
            
            for(int i = 0; i<listOfTimetables.size();i++)
            {
            	//Log.d("ttttt", listOfTimetables.get(i).getTitle());
            	String s = listOfTimetables.get(i).getTitle();
            	boolean parse = false;
            	for(int j =0;j<s.length();j++)
            	{
            		if(s.subSequence(j, j+1).equals("="))
            		{
            			parse = true;
            			if(parse == true)
            			{
            				listOfTimetables.get(i).setTitle(s.substring(j+3, s.length()-2));
            				parse = false;
            			}
            		}
            		
            	}
            }
//            for(int i = 0; i < listOfTimetables.size();i++)
//            {
//            	Log.d("adasdad", (listOfTimetables.get(i).toString()));
//            }
            
            for(int i = 0; i<listOfTimetables.size();i++)
            {
            	String s = listOfTimetables.get(i).getIdentifier();
            	boolean parse = false;
            	for(int j =0;j<s.length();j++)
            	{
            		if(s.subSequence(j, j+1).equals("="))
            		{
            			parse = true;
            			if(parse == true)
            			{
            				listOfTimetables.get(i).setIdentifier(s.substring(j+3, s.length()-2));
            				parse = false;
            			}
            		}
            		
            	}
            }
            
            for(int i = 0; i<listOfTimetables.size();i++)
            {
            	String s = listOfTimetables.get(i).getValue();
            	boolean parse = false;
            	for(int j =0;j<s.length();j++)
            	{
            		if(s.subSequence(j, j+1).equals("="))
            		{
            			parse = true;
            			if(parse == true)
            			{
            				listOfTimetables.get(i).setValue(s.substring(j+3, s.length()-2));
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
            Timetable2.this.runOnUiThread(new Runnable() {
                public void run() {
                  Toast.makeText(getApplicationContext(), "Couldn't fetch tiemtables, try again later.",
                  Toast.LENGTH_SHORT).show();
                }
              });
        }     

        return listOfTimetables;
    }

    @Override
    protected void onPostExecute(ArrayList<TimetableClass> listOfTimetables) {
    	Collections.sort(listOfTimetables, new Comparator<TimetableClass>() {
    	    public int compare(TimetableClass result1, TimetableClass result2) {
    	        return result1.getTitle().compareTo(result2.getTitle());
    	    }
    	});
		adapter = new ArrayAdapter<TimetableClass>(Timetable2.this,
		  android.R.layout.simple_list_item_1, android.R.id.text1, listOfTimetables);
    	setListAdapter(adapter);

    }
}

private void fillList() {
	PopulateList popList = new PopulateList();
    popList.execute();
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