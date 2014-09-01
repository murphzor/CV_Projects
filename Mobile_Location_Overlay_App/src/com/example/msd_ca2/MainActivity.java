package com.example.msd_ca2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	
		Button btnWonder= (Button) findViewById(R.id.wonder); 
		btnWonder.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
	
				Intent myIntent = new Intent(MainActivity.this,WonderMap.class); 		
				MainActivity.this.startActivity(myIntent);
			
			}
		
		});
		
		Button btnIrish = (Button) findViewById(R.id.irish); 
		btnIrish.setOnClickListener(new View.OnClickListener() 
		{ 
			public void onClick(View v) 
			{ 
	
				Intent myIntent = new Intent(MainActivity.this,IrishMap.class); 		
				MainActivity.this.startActivity(myIntent);
			
			}
		
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
