
package com.teamAndappers.womensafety;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.teamAndappers.womensafety.dto.UserDTO;

@SuppressLint("NewApi")
public class ControllerActivity extends FragmentActivity {
	private TextView mLatLng;
	private TextView mAddress;
	private TextView mLastUp;
	
	private LocationManager mLocationMgr;
	private Handler mHandler;
	private String ip;
	private Location mLastLocation;
	private boolean mGeocoderAvailable, isSent = false;
	private InputStream is;
	private HttpEntity entity;
	private String result = "";
	private UserDTO userDTO;
	private SharedPreferences sharedPreferences;
	private static final int UPDATE_LASTLATLNG = 4;
	private static final int LAST_UP = 3;
	private static final int UPDATE_LATLNG = 2;
	private static final int UPDATE_ADDRESS = 1;
	
	private static final int SECONDS_TO_UP = 10000;
	private static final int METERS_TO_UP = 10;
	private static final int MINUTES_TO_STALE = 1000 * 60 * 2;
	private String adress, postalCode;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Gson gson = new Gson();
        userDTO = gson.fromJson(sharedPreferences.getString("user", null), UserDTO.class);
        ResourceBundle rb = ResourceBundle.getBundle("network");
		ip = rb.getString("ip");
    	mLatLng = (TextView) findViewById(R.id.latlng);
    	mLastUp = (TextView) findViewById(R.id.lastup);
    	mAddress = (TextView) findViewById(R.id.address);
    	
    	mHandler = new Handler() {
    		public void handleMessage(Message msg) {
    			switch (msg.what) {
    			case UPDATE_ADDRESS:
    				mAddress.setText((String) msg.obj);
    				break;
    			case UPDATE_LATLNG:
    				mLatLng.setText((String) msg.obj);
    				break;
    			case LAST_UP:
    				mLastUp.setText((String) msg.obj);
    				break;
    			}
    		}
    	};
    	
        mGeocoderAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && Geocoder.isPresent();
    	mLocationMgr = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }
	    
	@Override
	protected void onResume() {
		super.onResume();
		setup();
	}
	
    public void onStart() {
    	super.onStart();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	mLocationMgr.removeUpdates(listener);
    }
        
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    }

    private void setup() {
    	Location newLocation = null;
    	mLocationMgr.removeUpdates(listener);
    	mLatLng.setText(R.string.unknown);
    	newLocation = requestUpdatesFromProvider(LocationManager.GPS_PROVIDER, R.string.no_gps_support);

    	if (newLocation == null) {
    		newLocation = requestUpdatesFromProvider(LocationManager.NETWORK_PROVIDER, R.string.no_network_support);
    	}
    	
    	if (newLocation != null) {
    		updateUILocation(getBestLocation(newLocation, mLastLocation));
    		if(isSent == true){
    			new UpdateLocationAsynTask().execute(newLocation);
    		}
        }
    }

   
    protected Location getBestLocation(Location newLocation, Location currentBestLocation) {
        if (currentBestLocation == null) {
            return newLocation;
        }
        
        long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
        boolean isNewerThanStale = timeDelta > MINUTES_TO_STALE;
        boolean isOlderThanStale = timeDelta < -MINUTES_TO_STALE;
        boolean isNewer = timeDelta > 0;

        if (isNewerThanStale) {
            return newLocation;
        } else if (isOlderThanStale) {
            return currentBestLocation;
        }

        int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        boolean isFromSameProvider = isSameProvider(newLocation.getProvider(),
                currentBestLocation.getProvider());

        if (isMoreAccurate) {
            return newLocation;
        } else if (isNewer && !isLessAccurate) {
            return newLocation;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return newLocation;
        }
        return currentBestLocation;
    }

    private Location requestUpdatesFromProvider(final String provider, final int errorResId) {
    	Location location = null;
    	if (mLocationMgr.isProviderEnabled(provider)) {
    		mLocationMgr.requestLocationUpdates(provider, SECONDS_TO_UP, METERS_TO_UP, listener);
    		location = mLocationMgr.getLastKnownLocation(provider);
    		
    		if(isSent == false){
    			isSent = true;
    			new SendLocationAsynTask().execute(location);
    		}
    	} else {
    		Toast.makeText(this, errorResId, Toast.LENGTH_LONG).show();
    	}
    	return location;
    }
    
    private void doReverseGeocoding(Location location) {
        (new ReverseGeocode(this)).execute(new Location[] {location});
    }
    
    private void updateUILocation(Location location) {
    	Message.obtain(mHandler, UPDATE_LATLNG, location.getLatitude() + ", " + location.getLongitude()).sendToTarget();
    	if (mLastLocation != null) {
    		Message.obtain(mHandler, UPDATE_LASTLATLNG, mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude()).sendToTarget();        	
    	}
    	mLastLocation = location;
    	Date now = new Date();
    	Message.obtain(mHandler, LAST_UP, now.toString()).sendToTarget();
    	
        if (mGeocoderAvailable) doReverseGeocoding(location);
    }
    
    private final LocationListener listener = new LocationListener() {
    	@Override
    	public void onLocationChanged(Location location) {
    		updateUILocation(location);
    	}
    	
    	@Override
    	public void onProviderDisabled(String provider) {}
    	
    	public void onProviderEnabled(String provider) {}
    	
    	@Override
    	public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    

   private boolean isSameProvider(String provider1, String provider2) {
       if (provider1 == null) {
         return provider2 == null;
       }
       return provider1.equals(provider2);
   }

   private class ReverseGeocode extends AsyncTask<Location, Void, Void> {

       public ReverseGeocode(Context context) {
           super();
       }

       @Override
       protected Void doInBackground(Location... params) {
           Geocoder geocoder = new Geocoder(ControllerActivity.this, Locale.getDefault());

           Location loc = params[0];
           List<Address> addresses = null;
           try {
               addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
           } catch (IOException e) {
               e.printStackTrace();
               Message.obtain(mHandler, UPDATE_ADDRESS, e.toString()).sendToTarget();
           }
           if (addresses != null && addresses.size() > 0) {
               Address address = addresses.get(0);
               adress = String.format("%s, %s, %s",
                       address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                       address.getLocality(),
                       address.getCountryName()
                      );
               postalCode =  address.getPostalCode();
               Message.obtain(mHandler, UPDATE_ADDRESS, adress).sendToTarget();
               new UpdateAddressAsynTask().execute();
           }
           return null;
       }
   }
   
   private class SendLocationAsynTask extends AsyncTask<Location, Void, Void> {


       @Override
       protected Void doInBackground(Location... params) {
    	Location location = params[0];
    	List<NameValuePair> list = new ArrayList<NameValuePair>(1);
    	String sql = "insert into request (imei, phone_no, latitude, longitude) values ('" + userDTO.getImei() 
    			+"','" + userDTO.getPhoneno() + "','" + location.getLatitude() + "','" + location.getLongitude() + "');";
   		list.add(new BasicNameValuePair("sql", sql));
   		try {
   			HttpClient httpClient = new DefaultHttpClient();
   			HttpPost httpPost = new HttpPost(ip+"/e_safety/register.php");
   			httpPost.setEntity(new UrlEncodedFormEntity(list));
   			HttpResponse httpResponse = httpClient.execute(httpPost);
   			entity = httpResponse.getEntity();
   			is = entity.getContent();
   			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
   			StringBuilder stringBuilder = new StringBuilder();
   			String line = "";
   			while ((line = bufferedReader.readLine()) != null) {
   				stringBuilder.append(line);
   			}
   			is.close();
   			result = stringBuilder.toString();
           return null;
       }
   		catch (Exception e) {
   			SendLocationAsynTask.this.cancel(true);
			Toast.makeText(getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
   		Log.d("log", sql + result);
   		return null;
   }
   
   }

   private class UpdateLocationAsynTask extends AsyncTask<Location, Void, Void> {


       @Override
       protected Void doInBackground(Location... params) {
    	Location location = params[0];
    	List<NameValuePair> list = new ArrayList<NameValuePair>(1);
    	String sql = "update `request` set latitude = '" + location.getLatitude() + "', longitude = " 
    	+ location.getLongitude() + "' where imei = '" + userDTO.getImei() + "';";
   		list.add(new BasicNameValuePair("sql", sql));
   		try {
   			HttpClient httpClient = new DefaultHttpClient();
   			HttpPost httpPost = new HttpPost(ip+"/e_safety/register.php");
   			httpPost.setEntity(new UrlEncodedFormEntity(list));
   			HttpResponse httpResponse = httpClient.execute(httpPost);
   			entity = httpResponse.getEntity();
   			is = entity.getContent();
   			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
   			StringBuilder stringBuilder = new StringBuilder();
   			String line = "";
   			while ((line = bufferedReader.readLine()) != null) {
   				stringBuilder.append(line);
   			}
   			is.close();
   			result = stringBuilder.toString();
           return null;
       }
   		catch (Exception e) {
   			UpdateLocationAsynTask.this.cancel(true);
			Toast.makeText(getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
   		Log.d("log", sql + result);
   		return null;
   }
   
   }
   
   private class UpdateAddressAsynTask extends AsyncTask<String, Void, Void> {
       @Override
       protected Void doInBackground(String... params) {
    	List<NameValuePair> list = new ArrayList<NameValuePair>(1);
    	String sql = "update request set address = '" + adress +"' where imei = '" + userDTO.getImei() + "'";
   		list.add(new BasicNameValuePair("sql", sql));
   		try {
   			HttpClient httpClient = new DefaultHttpClient();
   			HttpPost httpPost = new HttpPost(ip+"/e_safety/register.php");
   			httpPost.setEntity(new UrlEncodedFormEntity(list));
   			HttpResponse httpResponse = httpClient.execute(httpPost);
   			entity = httpResponse.getEntity();
   			is = entity.getContent();
   			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
   			StringBuilder stringBuilder = new StringBuilder();
   			String line = "";
   			while ((line = bufferedReader.readLine()) != null) {
   				stringBuilder.append(line);
   			}
   			is.close();
   			result = stringBuilder.toString();
           return null;
       }
   		catch (Exception e) {
   			UpdateAddressAsynTask.this.cancel(true);
			Toast.makeText(getApplicationContext(),"Exception " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
   		return null;
   }
   
   }
}
   
