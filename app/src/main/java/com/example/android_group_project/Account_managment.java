package com.example.android_group_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Account_managment extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //https://stackoverflow.com/questions/22505336/email-and-phone-number-validation-in-android
    //https://github.com/Oclemy/pwizards/blob/master/DBListView/src/com/tutorials/dbgridview/MainActivity.java
    Button save;
    EditText phonenumber,fullname,lastname, email;
    String full_name , last_name, email_text, phonenumber_text ;
    private static final String TAG = "ktr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_managment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" Account details");

        save = findViewById(R.id.save_accbtn);
        phonenumber = findViewById(R.id.editText_phn_number);
        fullname = findViewById(R.id.editText_FirstName);
        lastname = findViewById(R.id.editText_lastName);
        email = findViewById(R.id.editText_email);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Contact",Context.MODE_PRIVATE);

        // here we have set the account details from the shared preference
        // values stored when user enter or changes any details in it.
        full_name = sharedPref.getString("FullName", null);
        last_name = sharedPref.getString("LastName", null);
        email_text = sharedPref.getString("email", null);
        phonenumber_text = sharedPref.getString("contact", null);

        // checking if values are not null
        if (full_name != null && last_name != null && email_text != null && phonenumber_text != null) {
            phonenumber.setText(phonenumber_text);
            fullname.setText(full_name);
            lastname.setText(last_name);
            email.setText(email_text);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // we have checked validation for email and phone number here.
                final boolean check_email = isValidMail(email.getText().toString());
                final boolean check_phoneNumber = isValidMobile(phonenumber.getText().toString());
                // if email and phone number are valid this logic will save account details values in
                // shared preference storage. and will redirect to the home page.
                if (check_phoneNumber && check_email) {
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Contact", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    String phn_number = phonenumber.getText().toString();
                    editor.putString("contact", phn_number);
                    editor.putString("FullName", fullname.getText().toString());
                    editor.putString("LastName", lastname.getText().toString());
                    editor.putString("email", email.getText().toString());
                    editor.commit();

                    Intent myintent = new Intent(Account_managment.this, MainActivity.class);
                    startActivity(myintent);
                }else{
                    email.setTextColor(Color.RED);
                    phonenumber.setTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(),"Please enter valid email and mobile number" ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // method to check valid email address
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // method to check valid phone number
    private boolean isValidMobile(String phone) {
        if (phone.length() == 10) {
            String length = String.valueOf(phone.length());
            Log.d(TAG , length);
            return true;
        }
        return false;
    }
//        return android.util.Patterns.PHONE.matcher(phone).matches();


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
            Intent myintent1 = new Intent(Account_managment.this, alerts_list.class);
            startActivity(myintent1);
        } else if (id == R.id.nav_account) {
            Intent myintent3 = new Intent(Account_managment.this, Account_managment.class);
            startActivity(myintent3);
        } else if (id == R.id.home) {
            Intent myintent4 = new Intent(Account_managment.this, MainActivity.class);
            startActivity(myintent4);
        }  else if (id == R.id.nav_help) {
            Intent myintent5 = new Intent(Account_managment.this, Help_Page.class);
            startActivity(myintent5);
        } else if (id == R.id.nav_About_us) {
            Intent myintent6 = new Intent(Account_managment.this, About_us.class);
            startActivity(myintent6);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
