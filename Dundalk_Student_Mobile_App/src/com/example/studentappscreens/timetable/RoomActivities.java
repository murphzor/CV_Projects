package com.example.studentappscreens.timetable;

import com.example.studentappscreens.R;
import com.example.studentappscreens.R.layout;
import com.example.studentappscreens.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class RoomActivities extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room_activities);
		
		Button btnOnlineActivity = (Button) findViewById(R.id.room_timetables); 
		btnOnlineActivity.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
	
				Intent myIntent = new Intent(RoomActivities.this,RoomTimetables.class); 		
				RoomActivities.this.startActivity(myIntent);
			
			}
		
		});
		
		Button btnLocalActivity = (Button) findViewById(R.id.room_search); 
		btnLocalActivity.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
	
				Intent myIntent = new Intent(RoomActivities.this,FindFreeRooms.class); 		
				RoomActivities.this.startActivity(myIntent);
			
			}
		
		});
	}

}
