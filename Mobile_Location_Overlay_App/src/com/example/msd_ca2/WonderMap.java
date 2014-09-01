package com.example.msd_ca2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class WonderMap extends FragmentActivity implements LocationListener
{

	GoogleMap googleMap;
	ArrayList<Wonder> wonders;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		wonders = new ArrayList<Wonder>();
		
		new PopulateMap().execute(
						"https://dl.dropboxusercontent.com/sh/3sngmsvglhpl84f/psYMY1ihF_/seven_wonders.xml");
		
		showMyPosition();
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	//method for displaying my current position on the map
	private void showMyPosition() {
		SupportMapFragment af = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);

		googleMap = af.getMap();
		googleMap.setMyLocationEnabled(true);
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

	}

	//map_menu is a custom menu for switching between map types
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map_menu, menu);
		return true;
	}

	//for switching between different map types
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.normal_map:
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;

		case R.id.satellite_map:
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;

		case R.id.hybrid_map:
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;

		case R.id.terrain_map:
			googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		}
		return false;
	}

	class PopulateMap extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg) {

			//Log.d("HIIIIIIII", "ASYNCTASK");
			StringBuilder stringbuilder = new StringBuilder();
			try {

				//Log.d("BASDFASF", "TRYING");
				URL url = new URL(arg[0]);
				//Log.d("URL", url.toString());
				URLConnection connection = url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					//Log.d("INPUTLINE", inputLine);
					stringbuilder.append(inputLine + "\n");
				}
				XMLParser xmlmarker = new XMLParser();
				Wonder[] wons = xmlmarker.getXmlTagInfo(stringbuilder
						.toString());
				
				Log.d("WONDERS", Integer.toString(wons.length));
				
				for(int i =0;i<wons.length;i++)
				{
					Log.d("WONDERS", wons[i].getTitle());
					wonders.add(wons[i]);
				}
				

			} catch (Exception e) {
				e.printStackTrace();
				//Log.d("ASDASDASDDS", "FAILING");
			}
			return stringbuilder.toString();
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			//Log.d("POSTEXECUTE", "AAAAAAAAAAAAAAAA");
			for (int i = 0; i < wonders.size(); i++) {

				String[] coords = wonders.get(i).getCoordinates().split(", ");
				LatLng lt = new LatLng(Double.valueOf(coords[0]),
						Double.valueOf(coords[1]));

				int icon = R.drawable.wicon;			

				googleMap.addMarker(new MarkerOptions()
						.title(wonders.get(i).getTitle())
						.snippet(wonders.get(i).getFrom())
						.icon(BitmapDescriptorFactory.fromResource(icon))
						.position(lt));
			}
		}
	}

}
