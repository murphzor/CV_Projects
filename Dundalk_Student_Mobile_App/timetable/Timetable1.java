package com.example.studentappscreens.timetable;

import com.example.studentappscreens.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Timetable1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timetable1);
		
		Button btnOnlineActivity = (Button) findViewById(R.id.online_timetables); 
		btnOnlineActivity.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
	
				Intent myIntent = new Intent(Timetable1.this,Timetable2.class); 		
				Timetable1.this.startActivity(myIntent);
			
			}
		
		});
		
		Button btnLocalActivity = (Button) findViewById(R.id.local_timetables); 
		btnLocalActivity.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
	
				Intent myIntent = new Intent(Timetable1.this,DisplayLocalTimetable.class); 		
				Timetable1.this.startActivity(myIntent);
			
			}
		
		});
	}

}
