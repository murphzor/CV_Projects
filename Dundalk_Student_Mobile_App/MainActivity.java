package com.example.studentappscreens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.studentappscreens.timetable.DisplayLocalTimetable;
import com.example.studentappscreens.timetable.FindFreeRooms;
import com.example.studentappscreens.timetable.RoomTimetables;
import com.example.studentappscreens.timetable.RoomActivities;
import com.example.studentappscreens.timetable.Timetable1;
import com.example.studentappscreens.timetable.Timetable2;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnOnlineTimetable= (Button) findViewById(R.id.online_timetables); 
		btnOnlineTimetable.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
	
				Intent myIntent = new Intent(MainActivity.this,Timetable2.class); 		
				MainActivity.this.startActivity(myIntent);
			
			}
		
		});
		
		Button btnLocalTimetable = (Button) findViewById(R.id.local_timetables); 
		btnLocalTimetable.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
	
				Intent myIntent = new Intent(MainActivity.this,DisplayLocalTimetable.class); 		
				MainActivity.this.startActivity(myIntent);
			
			}
		
		});			
		
		Button btnRoomTimetable = (Button) findViewById(R.id.room_timetables); 
		btnRoomTimetable.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
	
				Intent myIntent = new Intent(MainActivity.this,RoomTimetables.class); 		
				MainActivity.this.startActivity(myIntent);
			
			}
		
		});
		
		Button btnFindFreeRoom = (Button) findViewById(R.id.room_search); 
		btnFindFreeRoom.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
	
				Intent myIntent = new Intent(MainActivity.this,FindFreeRooms.class); 		
				MainActivity.this.startActivity(myIntent);
			
			}
		
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


}
