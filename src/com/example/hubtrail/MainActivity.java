package com.example.hubtrail;

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

        }
	    
	     // Check if we were successful in obtaining the map.
        if (mMap != null) {
            // The Map is verified. It is now safe to manipulate the map.
        	// Get a handle to the Map Fragment
        	boolean earthmode = false;
        	boolean mylocation = true;
        	SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    	    if (settings.contains("earthview")){
    	    	earthmode = settings.getBoolean("earthview", false);
    	    }
    	    if (settings.contains("mylocation")){
    	    	mylocation = settings.getBoolean("mylocation", true);
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
