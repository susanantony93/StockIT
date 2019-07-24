package com.example.android_group_project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class alerts_list extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ktr";
    Button send_sms;
    Button open_dialer;
    // variable to get the device current location
    private GPSTracker GPSTracker;
    double longitude;
    double latitude;

    TextView textdemo;
    ProductdbHelper db;
    // variable to get database values
    Cursor cursor;
    ItemList item = new ItemList();
    ListView lvproducts;
    ArrayList<String> products = new ArrayList();
    ArrayAdapter<String> adapter;

    //https://github.com/Oclemy/pwizards/blob/master/DBListView/src/com/tutorials/dbgridview/MainActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        toolbar.setTitle("Alert List of Products");
        send_sms = findViewById(R.id.send_sms);
        open_dialer = findViewById(R.id.open_dialer);
        textdemo = findViewById(R.id.demoText);
        lvproducts = findViewById(R.id.lvproducts);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // adapter to show the values in list view fetched from database
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,products);
        db=StockIt.db;

        try {
            cursor = db.stock_details_alert();

            // this loop will get all the products name from cursor
            while(cursor.moveToNext())
            {
                String name=cursor.getString(1);
                products.add(name);
            }

            // setting adapter value into list view
            lvproducts.setAdapter(adapter);

            if(products.size() <= 0){
                textdemo.setText("Stock looks good");
            }



            Log.d(TAG, "onCreate: id"+ item.getItemName());

        }catch (Exception e){

        }

        // shared preference object to get the phone number value from account details.
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Contact", Context.MODE_PRIVATE);
        final String phonenumber = sharedPref.getString("contact",null);



        send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if phone number is not provided by the user
                // it will redirect to the account details page to get number \.
                if(phonenumber == null) {
                    Intent myintent = new Intent(alerts_list.this , Account_managment.class);
                    startActivity(myintent);
                    Toast.makeText(getApplicationContext(),"Please provide valid phone number here to send message." ,Toast.LENGTH_LONG).show();

                }else{

                    // GPS tracker object to track user's live location
                GPSTracker = new GPSTracker(alerts_list.this);

                if (GPSTracker.canGetLocation()) {

                    longitude = GPSTracker.getLongitude();
                    latitude = GPSTracker.getLatitude();
                    Geocoder geocoder;
                    List<Address> addresses = null;

                    geocoder = new Geocoder(alerts_list.this, Locale.getDefault());

                    // this will convert latitude and longitude values into the address with pin code.
                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // if address is null it will show toast message/
                    // address can be null when user has not turn on their device location and internet or wifi service
                    String add = " ";
                    if(addresses != null){
                        add = addresses.get(0).getAddressLine(0);
                    }else{
                        Toast.makeText(getApplicationContext(),"Please turn on your device location and Internet/Wifi connection " ,Toast.LENGTH_LONG).show();
                    }


                    String product = " ";

                    // taking all the items name from list that need to be reorder.
                    if(products.size() > 0) {
                        for (int i = 0; i < products.size(); i++) {
                            product = product + " , "+ products.get(i)  ;
                        }
                        product = product + " are items to be order.";
                    }
                    else
                        product = "No items to be order, stock is good. ";

                    String msg = "This is the branch/shop location " + add + " Also " + product;

                    Log.d(TAG, phonenumber);

                    // this will send a text SMS to the number stored in account details.
                    SmsManager.getDefault().sendTextMessage("+1"+phonenumber,
                            null, msg,
                            null, null);
                    Toast.makeText(getApplicationContext(),"Message has been sent " ,Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onCreate: id ,,,,,,,,,  "+ msg);
                }

            }}
        });


        final String diler = "+1" + phonenumber;
        open_dialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phonenumber == null) {
                    Intent myintent = new Intent(alerts_list.this , Account_managment.class);
                    startActivity(myintent);
                    Toast.makeText(getApplicationContext(),"Please provide valid phone number here." ,Toast.LENGTH_LONG).show();
                }else {

                    // this intent will redirect user to dialer with showing in dialer pad phone number stored in account details.
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel: " + diler));
                    startActivity(callIntent);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_alerts) {
            Intent myintent1 = new Intent(alerts_list.this, alerts_list.class);
            startActivity(myintent1);
        } else if (id == R.id.nav_account) {
            Intent myintent3 = new Intent(alerts_list.this, Account_managment.class);
            startActivity(myintent3);
        } else if (id == R.id.home) {
            Intent myintent4 = new Intent(alerts_list.this, MainActivity.class);
            startActivity(myintent4);
        }  else if (id == R.id.nav_help) {
            Intent myintent5 = new Intent(alerts_list.this, Help_Page.class);
            startActivity(myintent5);
        } else if (id == R.id.nav_About_us) {
            Intent myintent6 = new Intent(alerts_list.this, About_us.class);
            startActivity(myintent6);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
