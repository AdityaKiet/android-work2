package com.test;

import com.example.test.R;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	LocationManager locManager;
	private TextView tv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView)findViewById(R.id.txt);
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L,500.0f, locationListener);
		Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (location != null) {
			double fromLat = location.getLatitude();
			double fromLong = location.getLongitude();
			tv.setText("Latitude : " + fromLat);
		}

	}

	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			if(location != null){
				double fromLat = location.getLatitude();
				double fromLong = location.getLongitude();
				tv.setText("Latitude : " + fromLat);
			}
		}

		public void onProviderDisabled(String provider) {
			Toast.makeText(getApplicationContext(),"GPS is Disabled.Please enable GPS", Toast.LENGTH_SHORT).show();
		}

		public void onProviderEnabled(String provider) {
			Toast.makeText(getApplicationContext(), "GPS is Enabled",Toast.LENGTH_SHORT).show();
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
		
	};
}
