package com.example.msd_ca2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class IrishMap extends FragmentActivity implements LocationListener,
		OnInfoWindowClickListener {

	GoogleMap googlemap;
	ArrayList<IrishSite> sites;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new RetreiveFeedTask()
				.execute(
						"https://dl.dropboxusercontent.com/sh/3sngmsvglhpl84f/5bh2_vzgc4/irishlandmarks.xml");
		showMyPosition();

		sites = new ArrayList<IrishSite>();

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

		googlemap = af.getMap();
		googlemap.setMyLocationEnabled(true);
		googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googlemap.setOnInfoWindowClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.normal_map:
			googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;

		case R.id.satellite_map:
			googlemap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;

		case R.id.hybrid_map:
			googlemap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;

		case R.id.terrain_map:
			googlemap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		}
		return false;
	}

	class RetreiveFeedTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg) {

			//Log.d("HIIIIIIII", "ASYNCTASK");
			StringBuilder stringbuilder = new StringBuilder();
			try {

				//Log.d("BASDFASF", "TRYING");
				URL url = new URL(arg[0]);
				//Log.d("URL", url.toString());
				URLConnection connection = url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					//Log.d("INPUTLINE", inputLine);
					stringbuilder.append(inputLine + "\n");
					//Log.d("SBBBBBB", sb.toString());
				}
				IrishParser irishParser = new IrishParser();
				IrishSite[] iSites = irishParser.getXmlTagInfo(stringbuilder
						.toString());
									
				for(int i =0;i<iSites.length;i++)
				{
					//Log.d("WONDERS", iSites[i].getTitle());
					sites.add(iSites[i]);
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
			for (int i = 0; i < sites.size(); i++) {

				String[] coords = sites.get(i).getCoordinates().split(", ");
				LatLng lt = new LatLng(Double.valueOf(coords[0]),
						Double.valueOf(coords[1]));

				int icon = R.drawable.shamrock;
				

				googlemap.addMarker(new MarkerOptions()
						.title(sites.get(i).getTitle())
						.snippet(sites.get(i).getInfo())
						.icon(BitmapDescriptorFactory.fromResource(icon))
						.position(lt));
			}
		}
	}

	public void onInfoWindowClick(Marker mark) {

		LatLng location = mark.getPosition();

		String loc = location.toString();
		final String lat = loc.substring((loc.lastIndexOf("(") + 1), (loc.lastIndexOf(",")));
		final String lon = loc.substring((loc.lastIndexOf(",") + 1), (loc.lastIndexOf(")")));

		AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
		aBuilder.setTitle("Show route to " + mark.getTitle() + " ?");
		aBuilder.setCancelable(false);
		aBuilder.setPositiveButton("Navigate", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("google.navigation:q=" + lat + ","+ lon));
						startActivity(intent);
					}
				});

		aBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		AlertDialog alert = aBuilder.create();
		alert.show();
	}


}
