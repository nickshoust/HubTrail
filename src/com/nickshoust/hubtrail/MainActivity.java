package com.nickshoust.hubtrail;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger.LogLevel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends Activity implements
ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	public static final String PREFS_NAME = "MyPrefsFile";
	private GoogleMap mMap;
    long startTime;


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
		// May return null if a EasyTracker has not yet been initialized with a
		// property ID.
		EasyTracker easyTracker = EasyTracker.getInstance(this);

		switch (position) {
		case 0:
			easyTracker.send(MapBuilder
					.createEvent("ui_view",     // Event category (required)
							"spinner_selection",  // Event action (required)
							"city",   // Event label
							null)            // Event value
							.build()
					);
			break;
		case 1:  
			easyTracker.send(MapBuilder
					.createEvent("ui_view",     // Event category (required)
							"spinner_selection",  // Event action (required)
							"downtown",   // Event label
							null)            // Event value
							.build()
					);
			break;
		case 2:  
			easyTracker.send(MapBuilder
					.createEvent("ui_view",     // Event category (required)
							"spinner_selection",  // Event action (required)
							"east",   // Event label
							null)            // Event value
							.build()
					);
			break;
		case 3:  
			easyTracker.send(MapBuilder
					.createEvent("ui_view",     // Event category (required)
							"spinner_selection",  // Event action (required)
							"west",   // Event label
							null)            // Event value
							.build()
					);
			break;
		case 4:  
			easyTracker.send(MapBuilder
					.createEvent("ui_view",     // Event category (required)
							"spinner_selection",  // Event action (required)
							"finn",   // Event label
							null)            // Event value
							.build()
					);
			break;
		case 5:  
			easyTracker.send(MapBuilder
					.createEvent("ui_view",     // Event category (required)
							"spinner_selection",  // Event action (required)
							"fort",   // Event label
							null)            // Event value
							.build()
					);
			break;

		}


		setUpMapIfNeeded(position);
		return true;
	}

	private void setUpMapIfNeeded(int ID) {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {

			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

		}

		// Check if we were successful in obtaining the map.
		if (mMap != null) {
			// The Map is verified. It is now safe to manipulate the map.
			// Get a handle to the Map Fragment
			boolean earthmode = false;
			boolean mylocation = true;
			boolean altRoute = false;
			SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
			if (settings.contains("earthview")){
				earthmode = settings.getBoolean("earthview", false);
			}
			if (settings.contains("mylocation")){
				mylocation = settings.getBoolean("mylocation", true);
			} 
			if (settings.contains("altRoute")){
				altRoute = settings.getBoolean("altRoute", true);
			}


			if (earthmode){
				mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			} else {
				mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			}
			if (mylocation){
				mMap.setMyLocationEnabled(true);
			} else {
				mMap.setMyLocationEnabled(false);
			}

			PolylineOptions trailPath = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.504064,-84.29998398))
			.add(new LatLng(46.50441846,-84.30137873))
			.add(new LatLng(46.50543753,-84.31659222))
			.add(new LatLng(46.50543753,-84.31659222))
			.add(new LatLng(46.502535, -84.318329))
			.add(new LatLng(46.50283812,-84.32056189))
			.add(new LatLng(46.50311875,-84.32034731))
			.add(new LatLng(46.50323691,-84.32062626))
			.add(new LatLng(46.50288243,-84.32103395))
			.add(new LatLng(46.5030449,-84.32182789))
			.add(new LatLng(46.50347322,-84.32150602))
			.add(new LatLng(46.50360614,-84.32206392))
			.add(new LatLng(46.50373907,-84.32277203))
			.add(new LatLng(46.50397538,-84.3228364))
			.add(new LatLng(46.50404923,-84.32317972))
			.add(new LatLng(46.50401969,-84.3233943))
			.add(new LatLng(46.50459569,-84.32534695))
			.add(new LatLng(46.50511261,-84.32511091))
			.add(new LatLng(46.50548184,-84.32558298))
			.add(new LatLng(46.50548184,-84.32558298))
			.add(new LatLng(46.50622767,-84.32778239))
			.add(new LatLng(46.50632367,-84.32790041))
			.add(new LatLng(46.50667073,-84.32902694))
			.add(new LatLng(46.50658212,-84.32917714))
			.add(new LatLng(46.50689964,-84.32962775))
			.add(new LatLng(46.50640489,-84.3304646))
			.add(new LatLng(46.50648612,-84.33065772))
			.add(new LatLng(46.50613167,-84.33111906))
			.add(new LatLng(46.50594706,-84.33121562))
			.add(new LatLng(46.50588799,-84.33135509))
			.add(new LatLng(46.50585845,-84.33154821))
			.add(new LatLng(46.50565907,-84.33192372))
			.add(new LatLng(46.50567384,-84.3322134))
			.add(new LatLng(46.50616859,-84.33295369))
			.add(new LatLng(46.50634582,-84.33299661))
			.add(new LatLng(46.50653781,-84.33288932))
			.add(new LatLng(46.50667073,-84.33256745))
			.add(new LatLng(46.50697349,-84.33234215))
			.add(new LatLng(46.50727624,-84.33248162))
			.add(new LatLng(46.50745347,-84.33268547))
			.add(new LatLng(46.507579,-84.3326962))
			.add(new LatLng(46.5077193,-84.33390856))
			.add(new LatLng(46.50822142,-84.33456302))
			.add(new LatLng(46.50819188,-84.33470249))
			.add(new LatLng(46.50871615,-84.33537841))
			.add(new LatLng(46.50788175,-84.33671951))
			.add(new LatLng(46.50873831,-84.33764219))
			.add(new LatLng(46.5091592,-84.33696628))
			.add(new LatLng(46.50946194,-84.33734179))
			.add(new LatLng(46.50910751,-84.33796406))
			.add(new LatLng(46.50910751,-84.33796406))
			.add(new LatLng(46.50981637,-84.33895111))
			.add(new LatLng(46.51061382,-84.3395412))
			.add(new LatLng(46.51238591,-84.34062481))
			.add(new LatLng(46.51453448,-84.34319973))
			.add(new LatLng(46.51488149,-84.34330702))
			.add(new LatLng(46.51468953,-84.34387565))
			.add(new LatLng(46.51483719,-84.3449378))
			.add(new LatLng(46.51511037,-84.34559226))
			.add(new LatLng(46.51508822,-84.34591413))
			.add(new LatLng(46.51516944,-84.34634328))
			.add(new LatLng(46.5152728,-84.34690118))
			.add(new LatLng(46.51626214,-84.34702992))
			.add(new LatLng(46.51643933,-84.34832811))
			.add(new LatLng(46.51822599,-84.34805989))
			.add(new LatLng(46.51842532,-84.34840322))
			.add(new LatLng(46.51842532,-84.34840322))
			.add(new LatLng(46.51906024,-84.35383201))
			.add(new LatLng(46.51935554,-84.3536067))
			.add(new LatLng(46.51982064,-84.35349941))
			.add(new LatLng(46.52041125,-84.35293078))
			.add(new LatLng(46.52153337,-84.35142875))
			.add(new LatLng(46.52157766,-84.3510747))
			.add(new LatLng(46.52179913,-84.35080647))
			.add(new LatLng(46.52236757,-84.34985161))
			.add(new LatLng(46.5231796,-84.34908986))
			.add(new LatLng(46.52360038,-84.34888601))
			.add(new LatLng(46.52647192,-84.34826374))
			.add(new LatLng(46.52666384,-84.34792042))
			.add(new LatLng(46.52684838,-84.34800625))
			.add(new LatLng(46.5270403,-84.3479526))
			.add(new LatLng(46.52711412,-84.34782386))
			.add(new LatLng(46.53054643,-84.34712648))
			.add(new LatLng(46.53319618,-84.34631109))
			.add(new LatLng(46.53383092,-84.34592485))
			.add(new LatLng(46.53476825,-84.3443048))
			.add(new LatLng(46.53536607,-84.34385419))
			.add(new LatLng(46.53617053,-84.343822))
			.add(new LatLng(46.53659859,-84.34407949))
			.add(new LatLng(46.53659859,-84.34407949))
			.add(new LatLng(46.53797868,-84.34540987))
			.add(new LatLng(46.53832555,-84.3452704))
			.add(new LatLng(46.53844363,-84.34535623))
			.add(new LatLng(46.53883477,-84.34433699))
			.add(new LatLng(46.53934398,-84.34414387))
			.add(new LatLng(46.53977939,-84.34411168))
			.add(new LatLng(46.54005245,-84.34434772))
			.add(new LatLng(46.54009673,-84.34545279))
			.add(new LatLng(46.54015577,-84.34556007))
			.add(new LatLng(46.54030336,-84.34554935))
			.add(new LatLng(46.54053213,-84.34630036))
			.add(new LatLng(46.54065021,-84.34637547))
			.add(new LatLng(46.54082732,-84.34700847))
			.add(new LatLng(46.5412701,-84.34704065))
			.add(new LatLng(46.54234014,-84.34802771))
			.add(new LatLng(46.54269435,-84.34775949))
			.add(new LatLng(46.54293787,-84.34766293))
			.add(new LatLng(46.5433216,-84.34781313))
			.add(new LatLng(46.5435725,-84.34777021))
			.add(new LatLng(46.54374222,-84.34753418))
			.add(new LatLng(46.54381602,-84.34723377))
			.add(new LatLng(46.54383815,-84.34683681))
			.add(new LatLng(46.54383815,-84.34652567))
			.add(new LatLng(46.54417022,-84.34650421))
			.add(new LatLng(46.54429567,-84.34624672))
			.add(new LatLng(46.54438422,-84.34598923))
			.add(new LatLng(46.54454656,-84.34599996))
			.add(new LatLng(46.54481959,-84.34609652))
			.add(new LatLng(46.54515165,-84.34607506))
			.add(new LatLng(46.54568294,-84.3462038))
			.add(new LatLng(46.5461552,-84.34615016))
			.add(new LatLng(46.54653153,-84.34589267))
			.add(new LatLng(46.54675289,-84.3454957))
			.add(new LatLng(46.54681193,-84.34484124))
			.add(new LatLng(46.54666435,-84.34413314))
			.add(new LatLng(46.54646512,-84.34340358))
			.add(new LatLng(46.5464356,-84.3432641))
			.add(new LatLng(46.54695212,-84.34120417))
			.add(new LatLng(46.54728417,-84.34089303))
			.add(new LatLng(46.54759408,-84.34078574))
			.add(new LatLng(46.54777855,-84.3407321))
			.add(new LatLng(46.54790399,-84.34041023))
			.add(new LatLng(46.54803681,-84.33988452))
			.add(new LatLng(46.54845001,-84.3396163))
			.add(new LatLng(46.54861972,-84.33897257))
			.add(new LatLng(46.54875992,-84.33848977))
			.add(new LatLng(46.54892224,-84.33840394))
			.add(new LatLng(46.54935758,-84.33846831))
			.add(new LatLng(46.54984456,-84.33854342))
			.add(new LatLng(46.55042746,-84.33651567))
			.add(new LatLng(46.55113578,-84.33559299))
			.add(new LatLng(46.55207281,-84.33352232))
			.add(new LatLng(46.55239745,-84.33233142))
			.add(new LatLng(46.55246386,-84.33227777))
			.add(new LatLng(46.55270733,-84.3310225))
			.add(new LatLng(46.55285489,-84.32599068))
			.add(new LatLng(46.55280325,-84.32452083))
			.add(new LatLng(46.55280325,-84.32452083))
			.add(new LatLng(46.55292867,-84.31554079))
			.add(new LatLng(46.5529877,-84.31474686))
			.add(new LatLng(46.55306148,-84.31039095))
			.add(new LatLng(46.5531205,-84.30938244))
			.add(new LatLng(46.55285489,-84.3094039))
			.add(new LatLng(46.55239745,-84.30942535))
			.add(new LatLng(46.55186622,-84.30918932))
			.add(new LatLng(46.55145304,-84.30931807))
			.add(new LatLng(46.55089229,-84.30916786))
			.add(new LatLng(46.55028727,-84.30929661))
			.add(new LatLng(46.54990359,-84.30916786))
			.add(new LatLng(46.5460519,-84.308846))
			.add(new LatLng(46.5460519,-84.308846))
			.add(new LatLng(46.54591907,-84.30872798))
			.add(new LatLng(46.54555012,-84.30870652))
			.add(new LatLng(46.54535089,-84.30903912))
			.add(new LatLng(46.54489338,-84.30913568))
			.add(new LatLng(46.54429567,-84.30918932))
			.add(new LatLng(46.54386029,-84.30924296))
			.add(new LatLng(46.54342491,-84.30937171))
			.add(new LatLng(46.54318139,-84.30972576))
			.add(new LatLng(46.54250987,-84.30876017))
			.add(new LatLng(46.54250987,-84.30876017))
			.add(new LatLng(46.53656907,-84.31634545))
			.add(new LatLng(46.53642884,-84.31604505))
			.add(new LatLng(46.53627386,-84.31592703))
			.add(new LatLng(46.5358089,-84.31598067))
			.add(new LatLng(46.53559487,-84.31592703))
			.add(new LatLng(46.53484944,-84.3159163))
			.add(new LatLng(46.53450255,-84.31557298))
			.add(new LatLng(46.53416305,-84.31564808))
			.add(new LatLng(46.53415567,-84.30763364))
			.add(new LatLng(46.53424423,-84.30745125))
			.add(new LatLng(46.53423685,-84.30340648))
			.add(new LatLng(46.53419257,-84.30135727))
			.add(new LatLng(46.5338014,-84.30044532))
			.add(new LatLng(46.53350617,-84.30020928))
			.add(new LatLng(46.53332903,-84.2996943))
			.add(new LatLng(46.53348403,-84.29939389))
			.add(new LatLng(46.53394901,-84.29922223))
			.add(new LatLng(46.53417043,-84.29892182))
			.add(new LatLng(46.53421471,-84.2973876))
			.add(new LatLng(46.53348403,-84.29601431))
			.add(new LatLng(46.53245072,-84.29469466))
			.add(new LatLng(46.53183073,-84.29383636))
			.add(new LatLng(46.53107787,-84.29298878))
			.add(new LatLng(46.53107787,-84.29298878))
			.add(new LatLng(46.5300519,-84.29233432))
			.add(new LatLng(46.52892995,-84.29178715))
			.add(new LatLng(46.52830253,-84.29137945))
			.add(new LatLng(46.52585185,-84.28918004))
			.add(new LatLng(46.52565255,-84.28919077))
			.add(new LatLng(46.52515058,-84.28899765))
			.add(new LatLng(46.524656,-84.2888689))
			.add(new LatLng(46.52422047,-84.28847194))
			.add(new LatLng(46.52341583,-84.28836465))
			.add(new LatLng(46.52331248,-84.28788185))
			.add(new LatLng(46.52212395,-84.28783894))
			.add(new LatLng(46.52212395,-84.28783894))
			.add(new LatLng(46.52198369,-84.28778529))
			.add(new LatLng(46.52176222,-84.28775311))
			.add(new LatLng(46.52176222,-84.28307533))
			.add(new LatLng(46.52224945,-84.27957773))
			.add(new LatLng(46.52221992,-84.2790091))
			.add(new LatLng(46.5220206,-84.27821517))
			.add(new LatLng(46.52169578,-84.27767873))
			.add(new LatLng(46.52120855,-84.27722812))
			.add(new LatLng(46.52047031,-84.27665949))
			.add(new LatLng(46.52050722,-84.27652001))
			.add(new LatLng(46.51921527,-84.27527547))
			.add(new LatLng(46.5186911,-84.27503943))
			.add(new LatLng(46.51779779,-84.27498579))
			.add(new LatLng(46.51739911,-84.2748034))
			.add(new LatLng(46.51707427,-84.2753613))
			.add(new LatLng(46.51656484,-84.27665949))
			.add(new LatLng(46.51644671,-84.27697062))
			.add(new LatLng(46.51618831,-84.27714229))
			.add(new LatLng(46.51570841,-84.2771852))
			.add(new LatLng(46.51556813,-84.27738905))
			.add(new LatLng(46.51537617,-84.27746415))
			.add(new LatLng(46.51463046,-84.27758217))
			.add(new LatLng(46.5144385,-84.27804351))
			.add(new LatLng(46.51427607,-84.27819371))
			.add(new LatLng(46.51384783,-84.27795768))
			.add(new LatLng(46.51352296,-84.27770019))
			.add(new LatLng(46.51336053,-84.27757144))
			.add(new LatLng(46.51297659,-84.2775929))
			.add(new LatLng(46.51262218,-84.27776456))
			.add(new LatLng(46.51246713,-84.27811861))
			.add(new LatLng(46.51220132,-84.27819371))
			.add(new LatLng(46.51197981,-84.2780757))
			.add(new LatLng(46.51181737,-84.27764654))
			.add(new LatLng(46.51178784,-84.27724957))
			.add(new LatLng(46.51014126,-84.27727103))
			.add(new LatLng(46.51014126,-84.27727103))
			.add(new LatLng(46.51022986,-84.28337574))
			.add(new LatLng(46.50949147,-84.28337574))
			.add(new LatLng(46.50936595,-84.28337574))
			.add(new LatLng(46.50927734,-84.28333282))
			.add(new LatLng(46.50917396,-84.2833972))
			.add(new LatLng(46.50780791,-84.28336501))
			.add(new LatLng(46.50540061,-84.2833972))
			.add(new LatLng(46.50537107,-84.28684115))
			.add(new LatLng(46.50512,-84.28710938))
			.add(new LatLng(46.50515692,-84.28772092))
			.add(new LatLng(46.50525292,-84.28789258))
			.add(new LatLng(46.50525292,-84.29042459))
			.add(new LatLng(46.50525292,-84.29042459))
			.add(new LatLng(46.50524554,-84.29421186))
			.add(new LatLng(46.50532676,-84.29461956))
			.add(new LatLng(46.50533415,-84.29542422))
			.add(new LatLng(46.50532676,-84.29884672))
			.add(new LatLng(46.50530461,-84.29905057))
			.add(new LatLng(46.504064,-84.29998398));																
			mMap.addPolyline(trailPath);
			
			PolylineOptions altRoute0 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5023803,-84.2956066))
			.add(new LatLng(46.5021661,-84.2957783))
			.add(new LatLng(46.5020701,-84.2957246))
			.add(new LatLng(46.5017525,-84.2958963))
			.add(new LatLng(46.5015827,-84.2960143))
			.add(new LatLng(46.5013611,-84.2962182))
			.add(new LatLng(46.5012356,-84.296304))
			.add(new LatLng(46.50111,-84.2962074))
			.add(new LatLng(46.5006522,-84.295553))
			.add(new LatLng(46.50065955,-84.2953598));
			PolylineOptions altRoute1 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5007556,-84.2956924))
			.add(new LatLng(46.50070395,-84.2957461))
			.add(new LatLng(46.5008073,-84.2959714))
			.add(new LatLng(46.5007482,-84.2961538))
			.add(new LatLng(46.5006891,-84.2964113))
			.add(new LatLng(46.5007556,-84.2969906))
			.add(new LatLng(46.5007112,-84.297452))
			.add(new LatLng(46.5005783,-84.2975378))
			.add(new LatLng(46.5004675,-84.2974305))
			.add(new LatLng(46.5003789,-84.2972374))
			.add(new LatLng(46.5003937,-84.2969692))
			.add(new LatLng(46.4999949,-84.2962074))
			.add(new LatLng(46.4999284,-84.296304))
			.add(new LatLng(46.49996531,-84.2964864))
			.add(new LatLng(46.4998176,-84.296658))
			.add(new LatLng(46.4996182,-84.296701))
			.add(new LatLng(46.4995001,-84.2967975))
			.add(new LatLng(46.4993154,-84.2963254));
			PolylineOptions altRoute2 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5021661,-84.2957783))
			.add(new LatLng(46.5021809,-84.2961216))
			.add(new LatLng(46.5022695,-84.2964756))
			.add(new LatLng(46.5022104,-84.2967546))
			.add(new LatLng(46.5022326,-84.2969799))
			.add(new LatLng(46.5021292,-84.2973554))
			.add(new LatLng(46.5025132,-84.2976987))
			.add(new LatLng(46.502683,-84.2979991))
			.add(new LatLng(46.5026166,-84.2982674))
			.add(new LatLng(46.5026166,-84.2984605))
			.add(new LatLng(46.5025723,-84.2986536))
			.add(new LatLng(46.5026313,-84.2989862))
			.add(new LatLng(46.5026092,-84.2992651))
			.add(new LatLng(46.5026609,-84.2995012))
			.add(new LatLng(46.5027569,-84.299705));
			PolylineOptions altRoute3 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5026609,-84.2995012))
			.add(new LatLng(46.5020332,-84.2990935))
			.add(new LatLng(46.5019298,-84.2984712))
			.add(new LatLng(46.501819,-84.2983961))
			.add(new LatLng(46.5017821,-84.29822441))
			.add(new LatLng(46.5017082,-84.2981708))
			.add(new LatLng(46.5015679,-84.2981708))
			.add(new LatLng(46.5013685,-84.2981279))
			.add(new LatLng(46.5012431,-84.2977846))
			.add(new LatLng(46.5008589,-84.2976665))
			.add(new LatLng(46.5007112,-84.297452));
			PolylineOptions altRoute4 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5008589,-84.2976665))
			.add(new LatLng(46.50070395,-84.2977309))
			.add(new LatLng(46.5002534,-84.29814932));
			PolylineOptions altRoute5 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.50070395,-84.2977309))
			.add(new LatLng(46.50063,-84.2980099))
			.add(new LatLng(46.5004306,-84.2983317))
			.add(new LatLng(46.5001943,-84.298203));
			PolylineOptions altRoute6 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5004306,-84.2983317))
			.add(new LatLng(46.5001943,-84.2986321))
			.add(new LatLng(46.49996531,-84.2987287))
			.add(new LatLng(46.49987671,-84.2988575))
			.add(new LatLng(46.50000971,-84.2992866))
			.add(new LatLng(46.4998472,-84.2995012))
			.add(new LatLng(46.4995444,-84.2992759))
			.add(new LatLng(46.4992268,-84.2990184))
			.add(new LatLng(46.49902,-84.2986643))
			.add(new LatLng(46.4988058,-84.2984712))
			.add(new LatLng(46.4988945,-84.2965722))
			.add(new LatLng(46.4989831,-84.2966044));
			PolylineOptions altRoute7 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.4988058,-84.2984712))
			.add(new LatLng(46.4979491,-84.2986214))
			.add(new LatLng(46.49766851,-84.298718))
			.add(new LatLng(46.4974986,-84.2986858))
			.add(new LatLng(46.4972697,-84.2988038))
			.add(new LatLng(46.4963538,-84.2986321))
			.add(new LatLng(46.4960289,-84.2983854))
			.add(new LatLng(46.4956596,-84.2979562))
			.add(new LatLng(46.4952903,-84.2975378))
			.add(new LatLng(46.4953198,-84.2973876))
			.add(new LatLng(46.4955931,-84.2972267))
			.add(new LatLng(46.495726,-84.2969263))
			.add(new LatLng(46.4956891,-84.2966366))
			.add(new LatLng(46.4956596,-84.2963576))
			.add(new LatLng(46.4957704,-84.2962933))
			.add(new LatLng(46.496051,-84.296422))
			.add(new LatLng(46.4963243,-84.2964005))
			.add(new LatLng(46.496568,-84.2963147))
			.add(new LatLng(46.4970702,-84.2956924))
			.add(new LatLng(46.4973066,-84.2953169))
			.add(new LatLng(46.4974617,-84.2952526))
			.add(new LatLng(46.4975872,-84.2953384))
			.add(new LatLng(46.4976463,-84.2955637))
			.add(new LatLng(46.4975651,-84.2956817))
			.add(new LatLng(46.4973509,-84.2957783))
			.add(new LatLng(46.49701851,-84.2962933))
			.add(new LatLng(46.4967674,-84.2977309))
			.add(new LatLng(46.49678226,-84.298085))
			.add(new LatLng(46.4970038,-84.2983425))
			.add(new LatLng(46.4979565,-84.2986107));
			PolylineOptions altRoute8 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.50000971,-84.2992866))
			.add(new LatLng(46.5001426,-84.2996085))
			.add(new LatLng(46.5003124,-84.2995656))
			.add(new LatLng(46.50040115,-84.2996299))
			.add(new LatLng(46.5005414,-84.2997265))
			.add(new LatLng(46.5006374,-84.299705))
			.add(new LatLng(46.5008885,-84.2993188))
			.add(new LatLng(46.5010066,-84.2993295))
			.add(new LatLng(46.5011174,-84.2997479))
			.add(new LatLng(46.5012799,-84.30014492))
			.add(new LatLng(46.5014276,-84.3005311))
			.add(new LatLng(46.5017008,-84.3010676))
			.add(new LatLng(46.5019224,-84.3013465))
			.add(new LatLng(46.5019815,-84.3014967))
			.add(new LatLng(46.5017082,-84.3017435))
			.add(new LatLng(46.5016122,-84.3020654))
			.add(new LatLng(46.5016565,-84.3025696))
			.add(new LatLng(46.5017968,-84.3027842))
			.add(new LatLng(46.501915,-84.3029773))
			.add(new LatLng(46.5020849,-84.3035781))
			.add(new LatLng(46.50202586,-84.3041468));
			PolylineOptions altRoute9 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5019445,-84.2985892))
			.add(new LatLng(46.5018264,-84.2990291))
			.add(new LatLng(46.5013685,-84.2992115))
			.add(new LatLng(46.5010583,-84.299469));
			PolylineOptions altRoute10 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5020332,-84.2990935))
			.add(new LatLng(46.5018264,-84.2990291))
			.add(new LatLng(46.5021735,-84.2997265))
			.add(new LatLng(46.5021883,-84.3001127))
			.add(new LatLng(46.5023803,-84.3007028))
			.add(new LatLng(46.5022473,-84.3009818))
			.add(new LatLng(46.5020036,-84.3011642))
			.add(new LatLng(46.50193721,-84.3012607))
			.add(new LatLng(46.5019815,-84.3014967))
			.add(new LatLng(46.5022843,-84.3013787))
			.add(new LatLng(46.5025058,-84.3017328))
			.add(new LatLng(46.50272004,-84.3019581))
			.add(new LatLng(46.5029637,-84.3023765))
			.add(new LatLng(46.5030671,-84.3023765));
			PolylineOptions altRoute11= new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5043963,-84.2869914))
			.add(new LatLng(46.5044554,-84.2868197))
			.add(new LatLng(46.5053637,-84.2855108));
			PolylineOptions altRoute12 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5124524,-84.2766917))
			.add(new LatLng(46.5123785,-84.2767453))
			.add(new LatLng(46.51164021,-84.2768526))
			.add(new LatLng(46.5114999,-84.2766166));
			PolylineOptions altRoute13 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5115737,-84.2772818))
			.add(new LatLng(46.51164021,-84.2768526))
			.add(new LatLng(46.51181,-84.27724962));
			PolylineOptions altRoute14 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5119798,-84.278065))
			.add(new LatLng(46.5118026,-84.2783761))
			.add(new LatLng(46.5114999,-84.2784405));
			PolylineOptions altRoute15 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5140767,-84.27671311))
			.add(new LatLng(46.5132867,-84.2767882))
			.add(new LatLng(46.5131759,-84.2766702));
			PolylineOptions altRoute16 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5137814,-84.2767668))
			.add(new LatLng(46.5136706,-84.2769599))
			.add(new LatLng(46.5133384,-84.2767882));
			PolylineOptions altRoute17 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5136706,-84.2769599))
			.add(new LatLng(46.5135525,-84.277668));
			PolylineOptions altRoute18 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5130135,-84.2789984))
			.add(new LatLng(46.5130135,-84.2783761))
			.add(new LatLng(46.5133458,-84.2776036));
			PolylineOptions altRoute19 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5157232,-84.2770994))
			.add(new LatLng(46.515642,-84.2771959))
			.add(new LatLng(46.515295,-84.2772067))
			.add(new LatLng(46.5148593,-84.2767453));
			PolylineOptions altRoute20 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5246634,-84.2889118))
			.add(new LatLng(46.5236594,-84.2894697))
			.add(new LatLng(46.523497,-84.29021))
			.add(new LatLng(46.5233272,-84.2903602))
			.add(new LatLng(46.5232977,-84.2907465))
			.add(new LatLng(46.523342,-84.2912614))
			.add(new LatLng(46.5234306,-84.2915189))
			.add(new LatLng(46.5236742,-84.2918408))
			.add(new LatLng(46.5236594,-84.2927742))
			.add(new LatLng(46.5241614,-84.2928064))
			.add(new LatLng(46.5242057,-84.2925811))
			.add(new LatLng(46.5244493,-84.2925704))
			.add(new LatLng(46.5244862,-84.2927957))
			.add(new LatLng(46.52450841,-84.2929673))
			.add(new LatLng(46.5245231,-84.2932677))
			.add(new LatLng(46.5244493,-84.2935145));
			PolylineOptions altRoute21 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5342369,-84.3033957))
			.add(new LatLng(46.5323548,-84.3033528))
			.add(new LatLng(46.5323252,-84.3026447))
			.add(new LatLng(46.5322219,-84.3019795))
			.add(new LatLng(46.5319045,-84.301132))
			.add(new LatLng(46.531506,-84.3004024))
			.add(new LatLng(46.53090074,-84.2998016))
			.add(new LatLng(46.5312255,-84.2989433))
			.add(new LatLng(46.5310557,-84.2982459))
			.add(new LatLng(46.5306202,-84.2983425))
			.add(new LatLng(46.5301995,-84.2982674))
			.add(new LatLng(46.5284428,-84.2970657))
			.add(new LatLng(46.5280885,-84.2967439))
			.add(new LatLng(46.5276013,-84.2960036))
			.add(new LatLng(46.527092,-84.2954135))
			.add(new LatLng(46.5265384,-84.29499511))
			.add(new LatLng(46.5257191,-84.2949092))
			.add(new LatLng(46.5256083,-84.2948771))
			.add(new LatLng(46.5256083,-84.2939544))
			.add(new LatLng(46.5254975,-84.293772))
			.add(new LatLng(46.5253204,-84.2937613))
			.add(new LatLng(46.5249144,-84.29378271))
			.add(new LatLng(46.5246043,-84.2936862))
			.add(new LatLng(46.5244493,-84.2935145));
			PolylineOptions altRoute22 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5433954,-84.3497014))
			.add(new LatLng(46.5432035,-84.3478346));
			PolylineOptions altRoute23 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.548664,-84.3386507))
			.add(new LatLng(46.5481918,-84.3383932))
			.add(new LatLng(46.5479852,-84.337256));
			PolylineOptions altRoute24 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.515258,-84.3468904))
			.add(new LatLng(46.5140767,-84.3472552))
			.add(new LatLng(46.5137814,-84.3478346))
			.add(new LatLng(46.5137223,-84.3492723))
			.add(new LatLng(46.5137814,-84.3507957))
			.add(new LatLng(46.5134122,-84.3508816))
			.add(new LatLng(46.5131759,-84.3506455))
			.add(new LatLng(46.51283634,-84.35070991))
			.add(new LatLng(46.512792,-84.3509674))
			.add(new LatLng(46.512541,-84.3508601))
			.add(new LatLng(46.5122899,-84.3508601))
			.add(new LatLng(46.51200931,-84.3505812))
			.add(new LatLng(46.5120832,-84.3500018))
			.add(new LatLng(46.5116845,-84.3506241))
			.add(new LatLng(46.511522,-84.3510747))
			.add(new LatLng(46.5114334,-84.3515468))
			.add(new LatLng(46.511714,-84.3533921))
			.add(new LatLng(46.5115811,-84.3540573))
			.add(new LatLng(46.5113596,-84.3550229))
			.add(new LatLng(46.5116992,-84.3560958))
			.add(new LatLng(46.5114925,-84.3571043))
			.add(new LatLng(46.5112414,-84.3574691))
			.add(new LatLng(46.5110642,-84.358027))
			.add(new LatLng(46.5111233,-84.358263))
			.add(new LatLng(46.5106064,-84.3595505))
			.add(new LatLng(46.510695,-84.3600655))
			.add(new LatLng(46.5118469,-84.3596792))
			.add(new LatLng(46.5121718,-84.3589282))
			.add(new LatLng(46.5126739,-84.3587351))
			.add(new LatLng(46.5126148,-84.3565035))
			.add(new LatLng(46.5126886,-84.3541861))
			.add(new LatLng(46.5124671,-84.3530488))
			.add(new LatLng(46.5125853,-84.3516541))
			.add(new LatLng(46.5127772,-84.3509245));
			PolylineOptions altRoute25 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5121718,-84.3589282))
			.add(new LatLng(46.5122013,-84.3576407))
			.add(new LatLng(46.5126148,-84.3563318));
			PolylineOptions altRoute26 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5122013,-84.3576407))
			.add(new LatLng(46.5117878,-84.3573618))
			.add(new LatLng(46.5114925,-84.3571043));
			PolylineOptions altRoute27 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.51283634,-84.35070991))
			.add(new LatLng(46.5127625,-84.3496156))
			.add(new LatLng(46.5124671,-84.3495297))
			.add(new LatLng(46.5126,-84.3491864))
			.add(new LatLng(46.5124376,-84.3483496))
			.add(new LatLng(46.5121422,-84.348135))
			.add(new LatLng(46.5120684,-84.3473196))
			.add(new LatLng(46.5119798,-84.3470407))
			.add(new LatLng(46.5118321,-84.3468475))
			.add(new LatLng(46.5115368,-84.34626821));
			PolylineOptions altRoute28 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5124671,-84.3495297))
			.add(new LatLng(46.5120832,-84.3500018));
			PolylineOptions altRoute29 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.511522,-84.3510747))
			.add(new LatLng(46.5112562,-84.35081721))
			.add(new LatLng(46.511463,-84.3500662))
			.add(new LatLng(46.5108575,-84.3494439))
			.add(new LatLng(46.51042924,-84.349401))
			.add(new LatLng(46.5101634,-84.3492079))
			.add(new LatLng(46.5097942,-84.3493152))
			.add(new LatLng(46.5095875,-84.3497229))
			.add(new LatLng(46.5094398,-84.3500876))
			.add(new LatLng(46.509233,-84.3502378))
			.add(new LatLng(46.5088786,-84.3500662))
			.add(new LatLng(46.5085389,-84.3501949))
			.add(new LatLng(46.5086128,-84.3507314))
			.add(new LatLng(46.5085685,-84.3512678))
			.add(new LatLng(46.5087309,-84.3517828))
			.add(new LatLng(46.5088195,-84.3519759))
			.add(new LatLng(46.508982,-84.3522334))
			.add(new LatLng(46.5091444,-84.3526196))
			.add(new LatLng(46.5091297,-84.3529201))
			.add(new LatLng(46.5092626,-84.3532634))
			.add(new LatLng(46.5094398,-84.3534994))
			.add(new LatLng(46.5096761,-84.353478))
			.add(new LatLng(46.5099419,-84.3534994))
			.add(new LatLng(46.5101486,-84.3533707))
			.add(new LatLng(46.5105031,-84.3534565))
			.add(new LatLng(46.5107689,-84.3539071))
			.add(new LatLng(46.5106507,-84.3544436))
			.add(new LatLng(46.5105474,-84.3550444))
			.add(new LatLng(46.5105326,-84.3553019))
			.add(new LatLng(46.5102077,-84.3555593))
			.add(new LatLng(46.5101782,-84.3559456))
			.add(new LatLng(46.5099567,-84.35601));
			PolylineOptions altRoute30 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5105031,-84.3534565))
			.add(new LatLng(46.5103554,-84.352963))
			.add(new LatLng(46.510444,-84.3525124))
			.add(new LatLng(46.5107393,-84.3523836))
			.add(new LatLng(46.5109904,-84.3519974))
			.add(new LatLng(46.5112562,-84.35081721));
			PolylineOptions altRoute31 = new PolylineOptions()
			.width(3)
			.color(Color.RED)
			.add(new LatLng(46.5073575,-84.3357968))
			.add(new LatLng(46.5071507,-84.3354535))
			.add(new LatLng(46.5069883,-84.3350458))
			.add(new LatLng(46.50685535,-84.3346488))
			.add(new LatLng(46.506907,-84.3344879))
			.add(new LatLng(46.50641971,-84.3339193))
			.add(new LatLng(46.5070326,-84.3329751))
			.add(new LatLng(46.5072762,-84.332943))
			.add(new LatLng(46.5073649,-84.332782))
			.add(new LatLng(46.5073501,-84.3326426))
			.add(new LatLng(46.5070326,-84.3323958))
			.add(new LatLng(46.5068184,-84.3325675))
			.add(new LatLng(46.5066264,-84.3329108))
			.add(new LatLng(46.5062794,-84.3333936))
			.add(new LatLng(46.5058658,-84.3328464))
			.add(new LatLng(46.505408,-84.331913))
			.add(new LatLng(46.5057772,-84.3314624))
			.add(new LatLng(46.5057772,-84.3308079))
			.add(new LatLng(46.5062867,-84.3300354))
			.add(new LatLng(46.506464,-84.3302929));
			
			if (altRoute){
				
				mMap.addPolyline(altRoute0);
				mMap.addPolyline(altRoute1);
				mMap.addPolyline(altRoute2);
				mMap.addPolyline(altRoute3);
				mMap.addPolyline(altRoute4);
				mMap.addPolyline(altRoute5);
				mMap.addPolyline(altRoute6);
				mMap.addPolyline(altRoute7);
				mMap.addPolyline(altRoute8);
				mMap.addPolyline(altRoute9);
				mMap.addPolyline(altRoute10);
				mMap.addPolyline(altRoute11);
				mMap.addPolyline(altRoute12);
				mMap.addPolyline(altRoute13);
				mMap.addPolyline(altRoute14);
				mMap.addPolyline(altRoute15);
				mMap.addPolyline(altRoute16);
				mMap.addPolyline(altRoute17);
				mMap.addPolyline(altRoute18);
				mMap.addPolyline(altRoute19);
				mMap.addPolyline(altRoute20);
				mMap.addPolyline(altRoute21);
				mMap.addPolyline(altRoute22);
				mMap.addPolyline(altRoute23);
				mMap.addPolyline(altRoute24);
				mMap.addPolyline(altRoute25);
				mMap.addPolyline(altRoute26);
				mMap.addPolyline(altRoute27);
				mMap.addPolyline(altRoute28);
				mMap.addPolyline(altRoute29);
				mMap.addPolyline(altRoute30);
				mMap.addPolyline(altRoute31);
			} else {
				mMap.clear();
				mMap.addPolyline(trailPath);
			}
			

			if (ID == 0) {
				LatLngBounds CITY = new LatLngBounds(new LatLng(46.501515, -84.363128), new LatLng(46.556786, -84.270774));

				//LatLng city = new LatLng(46.523909, -84.315324);
				mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(CITY, 13));
			}
			if (ID == 1){
				LatLngBounds DOWNTOWN = new LatLngBounds(new LatLng(46.501899, -84.355188), new LatLng(46.515515, -84.295450));

				//LatLng downtown = new LatLng(46.509408, -84.325066);
				mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(DOWNTOWN, 0));

			}
			if (ID == 2){
				LatLngBounds EASTEND = new LatLngBounds(new LatLng(46.502460, -84.307467), new LatLng(46.523695, -84.271031));

				//LatLng eastend = new LatLng(46.512214, -84.278116);
				mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(EASTEND, 0));

			}
			if (ID == 3){
				LatLngBounds WESTEND = new LatLngBounds(new LatLng(46.518291, -84.356690), new LatLng(46.538369, -84.335319));

				//LatLng westend = new LatLng(46.527216, -84.347897);
				mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(WESTEND, 0));

			}
			if (ID == 4){
				LatLngBounds FINHILL = new LatLngBounds(new LatLng(46.521923, -84.319397), new LatLng(46.553805, -84.288326));

				//LatLng finhill = new LatLng(46.535297, -84.302882);
				mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(FINHILL, 0));

			}
			if (ID == 5){
				LatLngBounds FORTCREEK = new LatLngBounds(new LatLng(46.537778, -84.350425), new LatLng(46.552979, -84.319697));

				//LatLng fortcreek = new LatLng(46.545097, -84.332343);
				mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(FORTCREEK, 0));

			}


			mMap.getUiSettings().setCompassEnabled(false);
			mMap.getUiSettings().setRotateGesturesEnabled(false);
			mMap.getUiSettings().setTiltGesturesEnabled(false);
			mMap.getUiSettings().setZoomControlsEnabled(false);
			mMap.getUiSettings().setZoomGesturesEnabled(true);

		}
	}

}
