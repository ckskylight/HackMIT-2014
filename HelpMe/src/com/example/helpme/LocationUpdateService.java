package com.example.helpme;

import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

public class LocationUpdateService extends Service implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	
	private static String TAG = "LOCATION";
	
	public static LocationUpdateService mLocationUpdateService;
	
	private LocationClient mLocationClient;
	private static Location mCurrentLocation;
	TelephonyManager mTelephonyMgr;    
	
    // These settings are the same as the settings for the map. They will in fact give you updates
    // at the maximal rates currently possible.
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(15000)         // 5 seconds
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
    public static double[] getCurrentLocation() {
    	return new double[] {mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()};
    }
    
	@Override
	public void onCreate() {
		super.onCreate();
    	Log.d(TAG, "onCreate");
    	setUpLocationClientIfNeeded();
    	mLocationClient.connect();
    	mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
    	mLocationUpdateService = this;
	}
	
	

	@Override
	public void onDestroy() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onDestroy();
	}
	
    private void setUpLocationClientIfNeeded() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(
                    getApplicationContext(),
                    this,  // ConnectionCallbacks
                    this); // OnConnectionFailedListener
        }
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d(TAG, location.toString());
		mCurrentLocation = location;
		updateServer();
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void onConnected(Bundle connectionHint) {
        mLocationClient.requestLocationUpdates(
                REQUEST,
                this);  // LocationListener
    }

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	public void updateServer() {
		final String phoneNumber = mTelephonyMgr.getLine1Number();
       	final ParseObject userInput = new ParseObject("Person");
       	
       	// Store the location as a GeoPoint
       	final ParseGeoPoint point = new ParseGeoPoint(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
       	
       	ParseQuery<ParseObject> query = ParseQuery.getQuery("Person");
       	query.whereEqualTo("Number", phoneNumber);
       	query.findInBackground(new FindCallback<ParseObject>() {
       		@Override
       		public void done(List<ParseObject> entries, ParseException e) {
       			if (e == null) {
       				try {
       					ParseObject entry = entries.get(0);
       					entry.put("Number", phoneNumber);
           		    	entry.put("Coordinates", point);
           		    	entry.saveInBackground();
       				} catch (Exception exc) {
       					userInput.put("Number", phoneNumber);
           		    	userInput.put("Coordinates", point);
           		    	userInput.saveInBackground();
       				}
       		       	
       			}
       			else {
       				Log.e(TAG, "query error");
       			}
       		}
       	});
    	
	}

}
