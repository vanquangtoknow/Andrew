/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import Adapter.MyListAdapter;
import DTO.DetailInfo;
import DTO.HeaderInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.microsoft.windowsazure.messaging.NotificationHub;
@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	
	private String SENDER_ID = "1050984167132";
	private GoogleCloudMessaging gcm;
	private NotificationHub hub;
	
	public TableRow loginRow;
	public TextView loginId;
	private boolean isLogin = false;
	private String code= "0";
	private String id = "0";
	public static  boolean IsNavigation = false;
	public void setLogin( boolean b)
	{
		isLogin = b;
	}
	public void setId( String i)
	{
		id = i;
	}
	public void setCode( String c)
	{
		code = c;
	}
	public boolean getLogin( )
	{
		return isLogin;
	}
	public String getId( )
	{
		return id;
	}
	public String getCode( )
	{
		return code;
	}
	
	// la gi day???
	//myDepartments chua headerInfo dung de tim nhanh ra mot deptsList
	//deptList chua headerInfo
		/*headerInfo
			- DetailInfo
			- DetailInfo
		headerInfo
			- DetailInfo
			- DetailInfo*/
	private LinkedHashMap<String, HeaderInfo> myDepartments = new LinkedHashMap<String, HeaderInfo>();
	private ArrayList<HeaderInfo> deptList = new ArrayList<HeaderInfo>();
	 
	private MyListAdapter listAdapter;
    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    //ten cua ung dung
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    
    public void changeFragment (Fragment fg)
    {
    	 	FragmentManager fragmentManager = getFragmentManager();
     		fragmentManager.beginTransaction().replace(R.id.content_frame, fg).commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new loginFragment();
        Log.e("trans", "login");
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    	
		
		
		
    	// gan control
    	loginRow = (TableRow) findViewById(R.id.LoginRow);
    	loginId = (TextView) findViewById(R.id.LoginID);
        mTitle = mDrawerTitle = getTitle();
        
        mPlanetTitles = getResources().getStringArray(R.array.function_array);
        //thut ra 
        // la activity_main trong layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
        loadData();
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        
        listAdapter = new MyListAdapter(MainActivity.this, deptList);
        mDrawerList.setAdapter(listAdapter);
        //listener for child row click
        mDrawerList.setOnChildClickListener(myListItemClicked);
		// listener for group heading click
        mDrawerList.setOnGroupClickListener(myListGroupClicked);
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setHomeButtonEnabled(false);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
        }
    }
    @SuppressWarnings("unchecked")
	private void registerWithNotificationHubs() {
		new AsyncTask() {
			@Override
			protected Object doInBackground(Object... params) {
				try {
					String regid = gcm.register(SENDER_ID);
					hub.register(regid);
				} catch (Exception e) {
					return e;
				}
				return null;
			}
		}.execute(null, null, null);
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        
        gcm = GoogleCloudMessaging.getInstance(this);

		String connectionString = "Endpoint=sb://helloworld2013.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=ddXw3TuEgMkdMdJN1CSjy1lHLjOFSFgf1RAjoZ4zFHI=";
		hub = new NotificationHub("demohelloworld", connectionString, this);

		registerWithNotificationHubs();
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }


    private class DrawerItemClickListener implements ExpandableListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			switch (position) {
			case 1: {
				
				break;
			}

			}
		}
    }

	// child listener
	private OnChildClickListener myListItemClicked = new OnChildClickListener() {

		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			HeaderInfo headerInfo = deptList.get(groupPosition);
			DetailInfo detailInfo = headerInfo.getProductList().get(
					childPosition);
			
			Toast.makeText(
					getBaseContext(),
					"Clicked on Detail " + headerInfo.getName() + "/"
							+ detailInfo.getName(), Toast.LENGTH_LONG).show();
			selectItem(groupPosition,childPosition);
			return false;
		}

	};

	// group listener
	private OnGroupClickListener myListGroupClicked = new OnGroupClickListener() {

		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {

			HeaderInfo headerInfo = deptList.get(groupPosition);
			Toast.makeText(getBaseContext(),
					"Child on Header " + headerInfo.getName(),
					Toast.LENGTH_LONG).show();

			return false;
		}

	};

	// expand all groups
	private void expandAll() {
		int count = listAdapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			mDrawerList.expandGroup(i);
		}
	}

	// collapse all groups
	private void collapseAll() {
		int count = listAdapter.getGroupCount();
		for (int i = 0; i < count; i++) {
			mDrawerList.collapseGroup(i);
		}
	}

	// load data 
	private void loadData() {

		addProduct("Report", "Full");
		addProduct("Report", "Employee");
		addProduct("Report", "Expense");
		/*addProduct("Ticket","Ticket");
		addProduct("Check", "Checkin - Checkout");*/
		addProduct("Manager", "Ticket");
		addProduct("Manager", "Checkin-checkout");
		addProduct("Manager", "Tracking");
		addProduct("Manager", "Calendar");
		addProduct("Option", "Login");
		addProduct("Option", "Logout");
		addProduct("Option", "Demo");

	}
	private int addProduct(String department, String product){
		 
		  int groupPosition = 0;
		  //kiem tra hashmap xem da chua item nay chua
		  HeaderInfo headerInfo = myDepartments.get(department); 
		  //them groupitem nay vao neu chua ton tai
		  if(headerInfo == null)
		  {
			   headerInfo = new HeaderInfo();
			   headerInfo.setName(department);
			   myDepartments.put(department, headerInfo);
			   deptList.add(headerInfo);
		  }
		  //myDepartments chua headerInfo dung de tim nhanh ra mot deptsList
		  //deptList chua headerInfo
			/*headerInfo
		  		- DetailInfo
		  		- DetailInfo
		  	headerInfo
		  		- DetailInfo
		  		- DetailInfo*/
		 
		  //lay danh sach child trong group
		  ArrayList<DetailInfo> productList = headerInfo.getProductList();
		  //lay size
		  int listSize = productList.size();
		  listSize++;
		 
		  //tao child va add vao group o vi tri cuoi cung
		  DetailInfo detailInfo = new DetailInfo();
		  detailInfo.setSequence(String.valueOf(listSize));
		  detailInfo.setName(product);
		  productList.add(detailInfo);
		  headerInfo.setProductList(productList);
		 
		  //tim  group position trong list
		  groupPosition = deptList.indexOf(headerInfo);
		  return groupPosition;
		 }
    private void selectItem(int groupPosition,int childPosition) {
    	IsNavigation = true;
    	FragmentManager fragmentManager = getFragmentManager();
    	switch (groupPosition) {
		case 0:
			if (childPosition == 0) 
			{ // FULL REPORT
				Fragment fragment = new ReportFragment();
				Log.e("trans", "full");
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			else 
				if (childPosition == 1) 
				{ //EMPLOYEE REPORT
				Fragment fragment = new ReportEmployeeFragment();

				fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
			} else { //EXPENSE
				Fragment fragment = new ReportExpenseFragment();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			break;
/*		case 1:
			if(childPosition==0)
			{
				Fragment fragment = new fgm_ticket();
				Log.e("trans", "ticket");
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			break;
		case 2:
			if(childPosition==0)
			{
				Fragment fragment = new fgm_manager_checkincheckout();
				Log.e("trans", "checkin - checkout");
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			break;*/
		case 1:
			if(childPosition==0)
			{
				Fragment fragment = new fgm_ticket();
				Log.e("trans", "ticket");
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			if(childPosition==1)
			{
				Fragment fragment = new fgm_manager_checkincheckout();
				Log.e("trans", "ticket");
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			if(childPosition==2)
			{
				Fragment fragment = new fgm_tracking();
				Log.e("trans", "ticket");
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			if(childPosition==3)
			{
				Fragment fragment = new fgm_calendar();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			break;
		case 2:
			if( childPosition == 0) // LOGIN
			{
				Fragment fragment = new loginFragment();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			else if( childPosition == 1) // LOGOUT
			{
				isLogin = false;
				loginRow.setBackgroundColor(Color.parseColor("#D42B24"));
				loginId.setText("NOT LOGIN !!!");
				Fragment fragment = new loginFragment();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			else if( childPosition == 2) 
			{
				isLogin = false;
				Fragment fragment = new testFragment();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
		default:
			break;
		}
    	
    	
		/*if (groupPosition == 0) { // REPORT
			if (childPosition == 0) 
			{ // FULL REPORT
				Fragment fragment = new ReportFragment();
				Log.e("trans", "full");
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
			else 
				if (childPosition == 1) 
				{ //EMPLOYEE REPORT
				Fragment fragment = new ReportEmployeeFragment();

				fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
			} else { //EXPENSE
				Fragment fragment = new ReportExpenseFragment();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
			}
		}
		else
			if(groupPosition==1)
			{
				
			}else
			{
				if ( groupPosition == 1 ) //OPTIONS
				{
					if( childPosition == 0) // LOGIN
					{
						Fragment fragment = new loginFragment();
						fragmentManager.beginTransaction()
								.replace(R.id.content_frame, fragment).commit();
					}
					else if( childPosition == 1) // LOGOUT
					{
						isLogin = false;
						loginRow.setBackgroundColor(Color.parseColor("#D42B24"));
						loginId.setText("NOT LOGIN !!!");
						Fragment fragment = new loginFragment();
						fragmentManager.beginTransaction()
								.replace(R.id.content_frame, fragment).commit();
					}
					else if( childPosition == 2) 
					{
						isLogin = false;
						Fragment fragment = new testFragment();
						fragmentManager.beginTransaction()
								.replace(R.id.content_frame, fragment).commit();
					}
				}
			}
				*/
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(childPosition, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    
}