package com.example.android_group_project;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.telephony.SmsManager;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class alerts_list extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button send_sms;
    Button open_dialer;
    private GPSTracker GPSTracker;
    double longitude;
    double latitude;
    String[] PERMISSIONS = {Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_SMS, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        send_sms = findViewById(R.id.send_sms);
        open_dialer = findViewById(R.id.open_dialer);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GPSTracker = new GPSTracker(alerts_list.this);

                if (GPSTracker.canGetLocation()) {

                    longitude = GPSTracker.getLongitude();
                    latitude = GPSTracker.getLatitude();
                    Geocoder geocoder;
                    List<Address> addresses = null;

                    geocoder = new Geocoder(alerts_list.this, Locale.getDefault());


                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    String address = addresses.get(0).getAddressLine(0);
//                    String city = addresses.get(0).getLocality();
//                    String state = addresses.get(0).getAdminArea();
//                    String country = addresses.get(0).getCountryName();
//                    String postalCode = addresses.get(0).getPostalCode();
//                    String knownName = addresses.get(0).getFeatureName();


                    String add = "My current location is - " + addresses.get(0).getAddressLine(0);

                    String msg = "This is my current location " + add;



                    SmsManager.getDefault().sendTextMessage("9028170568",
                            null, msg,
                            null, null);
                }

            }
        });

        open_dialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: +19028170568"));
                startActivity(callIntent);

            }
        });
    }
    public String GetAddress(String lat, String lon)
    {
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        String ret = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lon), 1);
            if(addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                ret = strReturnedAddress.toString();
            }
            else{
                ret = "No Address returned!";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ret = "Can't get Address!";
        }
        return ret;
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
        } else if (id == R.id.nav_settings) {
            Intent myintent2 = new Intent(alerts_list.this, Settings.class);
            startActivity(myintent2);
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
