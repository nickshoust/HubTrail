package com.example.hubtrail;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends Activity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	
	private GoogleMap mMap;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		// Set up the dropdown list navigation in the action bar.
		SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(actionBar.getThemedContext(), android.R.layout.simple_spinner_dropdown_item,android.R.id.text1, 
				new String[] {
					getString(R.string.title_section1),
					getString(R.string.title_section2),
					getString(R.string.title_section3),
					getString(R.string.title_section4),
					getString(R.string.title_section5),
					getString(R.string.title_section6), });
		
		// Specify a SpinnerAdapter to populate the dropdown list.
		actionBar.setListNavigationCallbacks(spinnerAdapter, this);
			
				 
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		}
		if (id == R.id.action_help) {
			Intent intent = new Intent(this, HelpActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public boolean onNavigationItemSelected(int position, long id) {

		setUpMapIfNeeded(position);
		return true;
	}

	private void setUpMapIfNeeded(int ID) {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) {

	        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            
        }

	     // Check if we were successful in obtaining the map.
        if (mMap != null) {
            // The Map is verified. It is now safe to manipulate the map.
        	// Get a handle to the Map Fragment
        	if (ID == 0) {
        		LatLng city = new LatLng(46.523909, -84.315324);
        		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 13));
        	}
            if (ID == 1){
            	LatLng downtown = new LatLng(46.509408, -84.325066);
   		 		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(downtown, 14));
   		 	
    		}
            if (ID == 2){
            	LatLng eastend = new LatLng(46.512214, -84.278116);
   		 		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eastend, 15));
   		 	
    		}
            if (ID == 3){
            	LatLng westend = new LatLng(46.527216, -84.347897);
   		 		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(westend, 15));
   		 	
    		}
            if (ID == 4){
            	LatLng finhill = new LatLng(46.535297, -84.302882);
   		 		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(finhill, 15));
   		 	
    		}
            if (ID == 5){
            	LatLng fortcreek = new LatLng(46.545097, -84.332343);
   		 		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fortcreek, 15));
   		 	
    		}
            
            
            mMap.getUiSettings().setCompassEnabled(false);
            mMap.getUiSettings().setRotateGesturesEnabled(false);
            mMap.getUiSettings().setTiltGesturesEnabled(false);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.setMyLocationEnabled(true);

	    }
	}

}
