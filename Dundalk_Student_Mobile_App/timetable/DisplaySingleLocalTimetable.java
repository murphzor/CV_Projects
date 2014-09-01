package com.example.studentappscreens.timetable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.studentappscreens.R;
import com.example.studentappscreens.R.id;
import com.example.studentappscreens.R.layout;
import com.example.studentappscreens.R.menu;
import com.example.studentappscreens.R.string;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DisplaySingleLocalTimetable extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	private DbController dbController;
	FullTimetable week;
	
	private Day day1 = new Day("Monday");
	private Day day2 = new Day("Tuesday");
	private Day day3 = new Day("Wednesday");
	private Day day4 = new Day("Thursday");
	private Day day5 = new Day("Friday");
	
	int value;
	String title;
	Document doc = null;
	
	static ArrayList<SingleClass> monClasses = new ArrayList<SingleClass>();
	static ArrayList<SingleClass> tuesClasses = new ArrayList<SingleClass>();
	static ArrayList<SingleClass> wedClasses = new ArrayList<SingleClass>();
	static ArrayList<SingleClass> thurClasses = new ArrayList<SingleClass>();
	static ArrayList<SingleClass> friClasses = new ArrayList<SingleClass>();
	
	static ArrayAdapter<String> adapter;
	static ArrayAdapter<String> adapter2;
	static ArrayAdapter<String> adapter3;
	static ArrayAdapter<String> adapter4;
	static ArrayAdapter<String> adapter5;
	
	String[] mon;
	String[] tue;
	String[] wed;
	String[] thur;
	String[] fri;
	
	FullTimetable thisWeek;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_single_local_timetable);

		Intent intent = getIntent();
		value = intent.getIntExtra("position", 0);	
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		dbController = new DbController(this);
		dbController.open();
		
		new CreateTimetablesTask().execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_single_local_timetable, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 5 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);	
			}
			
			return null;
		}
		
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends ListFragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_display_single_local_timetable_dummy,
					container, false);
			

			
		    
		    
		    int i = getArguments().getInt(ARG_SECTION_NUMBER);
		    String s = Integer.toString(i);
			Log.d("ONCREATEVIEW", s);
		    if(i==1)
		    {
				setListAdapter(adapter);
		    }
		    else if(i==2)
		    {
		    	setListAdapter(adapter2);
		    }
		    else if(i==3)
		    {
		    	setListAdapter(adapter3);
		    }
		    else if(i==4)
		    {
		    	setListAdapter(adapter4);
		    }
		    else if(i==5)
		    {
		    	setListAdapter(adapter5);
		    }
		    //setListAdapter(adapter);
			return rootView;
		}
		
		
	
	
		
	}

	private class CreateTimetablesTask extends AsyncTask<URL, Integer, Long> 
	{
	    protected Long doInBackground(URL... urls) 
	    {
	
			ArrayList<FullTimetable> entries = dbController.getEntries();
			week = entries.get(value);
						
			return null;
	    }


    protected void onPostExecute(Long result) 
    {
    	
    	ArrayList<String> a = new ArrayList<String>();
    	mon = week.getMonClasses().split(",");
    	
    	for(int i = 0;i<mon.length;i++)
    	{
    		a.add(mon[i]);
    	}

    	tidyArrayList(a);
    	
    	ArrayList<String> b = new ArrayList<String>();
      	tue = week.getTuesClasses().split(",");
    	
      	for(int i = 0;i<tue.length;i++)
    	{
    		b.add(tue[i]);
    	}
      	tidyArrayList(b);
    	ArrayList<String> c = new ArrayList<String>();
      	wed = week.getWedClasses().split(",");
    	
      	for(int i = 0;i<wed.length;i++)
    	{
    		c.add(wed[i]);
    	}
      	tidyArrayList(c);
    	
    	ArrayList<String> d = new ArrayList<String>();
      	thur = week.getThurClasses().split(",");
    	for(int i = 0;i<thur.length;i++)
    	{
    		d.add(thur[i]);
    	}
    	tidyArrayList(d);
    	
    	ArrayList<String> e = new ArrayList<String>();
      	fri = week.getFriClasses().split(",");
    	for(int i = 0;i<fri.length;i++)
    	{
    		e.add(fri[i]);
    	}
    	tidyArrayList(e);
    	
    	
	    adapter = new ArrayAdapter<String>(DisplaySingleLocalTimetable.this,
	            android.R.layout.simple_list_item_1, a);
	    adapter2 = new ArrayAdapter<String>(DisplaySingleLocalTimetable.this,
	            android.R.layout.simple_list_item_1, b);
	    adapter3 = new ArrayAdapter<String>(DisplaySingleLocalTimetable.this,
	            android.R.layout.simple_list_item_1, c);
	    adapter4 = new ArrayAdapter<String>(DisplaySingleLocalTimetable.this,
	            android.R.layout.simple_list_item_1, d);
	    adapter5 = new ArrayAdapter<String>(DisplaySingleLocalTimetable.this,
	            android.R.layout.simple_list_item_1, e);
	    
	    adapter.notifyDataSetChanged();
	    adapter2.notifyDataSetChanged();
	    adapter3.notifyDataSetChanged();
	    adapter4.notifyDataSetChanged();
	    adapter5.notifyDataSetChanged();
	    
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	           
    }
}
	public void tidyArrayList(ArrayList<String> list)
	{
    	if(!list.isEmpty())
    	{
    		String s = list.get(0).substring(6);
    		list.set(0, s);
    		for(int i = 0; i < list.size();i++)
    		{
    			list.set(i,list.get(i).replace("\"", ""));
    			list.set(i,list.get(i).replace("}", ""));
    			if(i+1 ==list.size()&&list.get(i).length()>0)
    			{
    				list.set(i,list.get(i).substring(0, list.get(i).length() - 1));
    			}
    		}  			
    	}			
	}

}

