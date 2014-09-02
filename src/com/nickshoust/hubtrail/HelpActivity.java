package com.nickshoust.hubtrail;

import java.util.Calendar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;


public class HelpActivity extends Activity {
	long startTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
		TextView textView = (TextView) findViewById(R.id.help);
		textView.setText (Html.fromHtml (getString (R.string.help)));
		Button startBtn = (Button) findViewById(R.id.sendEmail);
		startBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				sendEmail();
			}
		});
	}
	protected void sendEmail() {
		Context context = getApplicationContext(); 
		String[] TO = {"hubtrail@nickshoust.com"};
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setData(Uri.parse("mailto:"));
		emailIntent.setType("text/plain");

		emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hub Trail Feedback");
		Context appcontext = getApplicationContext(); 
		PackageManager packageManager = appcontext.getPackageManager();
		String packageName = appcontext.getPackageName();

		String myVersionName = "not available"; 
		String androidVersion = android.os.Build.VERSION.RELEASE;

		try {
			myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		emailIntent.putExtra(Intent.EXTRA_TEXT, "Version: " + myVersionName + "\r\nAndroid version: " + androidVersion);

		try {
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			finish();
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(HelpActivity.this, 
					"There is no email client installed.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);

	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
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
			View rootView = inflater.inflate(R.layout.fragment_help, container,
					false);
			return rootView;
		}
	}
}
