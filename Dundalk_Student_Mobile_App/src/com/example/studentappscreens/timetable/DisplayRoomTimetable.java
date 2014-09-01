package com.example.studentappscreens.timetable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.studentappscreens.R;
import com.example.studentappscreens.R.layout;
import com.example.studentappscreens.R.menu;
import com.example.studentappscreens.timetable.DisplayOnlineTimetable.DummySectionFragment;
import com.example.studentappscreens.timetable.DisplayOnlineTimetable.SectionsPagerAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

	public class DisplayRoomTimetable extends FragmentActivity {

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
		
		
		
		private Day day1 = new Day("Monday");
		private Day day2 = new Day("Tuesday");
		private Day day3 = new Day("Wednesday");
		private Day day4 = new Day("Thursday");
		private Day day5 = new Day("Friday");
		
		String value;	
		Document doc = null;
		
		static ArrayList<SingleRoom> monClasses = new ArrayList<SingleRoom>();
		static ArrayList<SingleRoom> tuesClasses = new ArrayList<SingleRoom>();
		static ArrayList<SingleRoom> wedClasses = new ArrayList<SingleRoom>();
		static ArrayList<SingleRoom> thurClasses = new ArrayList<SingleRoom>();
		static ArrayList<SingleRoom> friClasses = new ArrayList<SingleRoom>();
		
		static ArrayAdapter<SingleRoom> adapter;
		static ArrayAdapter<SingleRoom> adapter2;
		static ArrayAdapter<SingleRoom> adapter3;
		static ArrayAdapter<SingleRoom> adapter4;
		static ArrayAdapter<SingleRoom> adapter5;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_display_online_timetable);

			Intent intent = getIntent();
			value = intent.getStringExtra("url");
			
			// Create the adapter that will return a fragment for each of the three
			// primary sections of the app.
			mSectionsPagerAdapter = new SectionsPagerAdapter(
					getSupportFragmentManager());

			new CreateTimetablesTask().execute();
			// Set up the ViewPager with the sections adapter.


		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.display_online_timetable, menu);
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
				View rootView = inflater.inflate(R.layout.fragment_display_online_timetable_dummy,
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
		
		
		
		private class CreateTimetablesTask extends AsyncTask<URL, Integer, Long> 
		{
	    protected Long doInBackground(URL... urls) {
				try {
					doc = Jsoup.connect(value).get();
					Log.d("URL",value);
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
				return null;		

	    }


	    protected void onPostExecute(Long result) 
	    {
//			  for(int i = 0; i<monClasses.size();i++)
//			  {
//				  Log.d("monday", monClasses.get(i).toString());
//			  }
//			  for(int i = 0; i<tuesClasses.size();i++)
//			  {
//				  Log.d("tuesday", tuesClasses.get(i).toString());
//			  }
//			  for(int i = 0; i<wedClasses.size();i++)
//			  {
//				  Log.d("wednesday", wedClasses.get(i).toString());
//			  }
//			  for(int i = 0; i<thurClasses.size();i++)
//			  {
//				  Log.d("thursday1", thurClasses.get(i).toString());
//			  }
//			  for(int i = 0; i<friClasses.size();i++)
//			  {
//				  Log.d("friday", friClasses.get(i).toString());
//			  }	

//	    	String d;
//	    	d = Integer.toString(friClasses.size());
//	    	Log.d("size",d);
	    	
	    	
		    adapter = new ArrayAdapter<SingleRoom>(DisplayRoomTimetable.this,
		            android.R.layout.simple_list_item_1, monClasses);
		    adapter2 = new ArrayAdapter<SingleRoom>(DisplayRoomTimetable.this,
		            android.R.layout.simple_list_item_1, tuesClasses);
		    adapter3 = new ArrayAdapter<SingleRoom>(DisplayRoomTimetable.this,
		            android.R.layout.simple_list_item_1, wedClasses);
		    adapter4 = new ArrayAdapter<SingleRoom>(DisplayRoomTimetable.this,
		            android.R.layout.simple_list_item_1, thurClasses);
		    adapter5 = new ArrayAdapter<SingleRoom>(DisplayRoomTimetable.this,
		            android.R.layout.simple_list_item_1, friClasses);
		    
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
	        
	    }
	}

	}
