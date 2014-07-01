package com.example.hubtrail;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

public class SettingsActivity extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        CheckBox checkBoxEarthView = (CheckBox)findViewById(R.id.checkbox_earthview);
        CheckBox checkBoxMyLocation = (CheckBox)findViewById(R.id.checkbox_mylocation);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	    if (settings.contains("earthview")){
	    	checkBoxEarthView.setChecked(settings.getBoolean("earthview", false));
	    } 
	    if (settings.contains("mylocation")){
	    	checkBoxMyLocation.setChecked(settings.getBoolean("mylocation", true));
	    } 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_help) {
			Intent intent = new Intent(this, HelpActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_settings,
					container, false);
			return rootView;
		}
	}

	public void onCheckboxClicked(View view) {
		CheckBox checkBoxEarthView = (CheckBox)findViewById(R.id.checkbox_earthview);
		CheckBox checkBoxMyLocation = (CheckBox)findViewById(R.id.checkbox_mylocation);
		boolean earthViewChecked = checkBoxEarthView.isChecked();
		boolean myLocationChecked = checkBoxMyLocation.isChecked();

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		if (earthViewChecked){
			editor.putBoolean("earthview", true);
		} else {
			editor.putBoolean("earthview", false);
		}
		if (myLocationChecked){
			editor.putBoolean("mylocation", true);
		} else {
			editor.putBoolean("mylocation", false);
		}
		
		editor.commit();

	    
	}

}
