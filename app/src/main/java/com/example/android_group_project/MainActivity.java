package com.example.android_group_project;

import android.database.Cursor;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    List<ItemList> itemList;

    //the recyclerview
    RecyclerView itemRecyclerView;
    public ProductdbHelper db;
    Button view_product;


    String[] PERMISSIONS = {Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.LOCATION_HARDWARE,
                            Manifest.permission.RECEIVE_SMS, Manifest.permission.CAMERA, Manifest.permission.INTERNET, Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewforItems);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new ProductdbHelper(this);
        FloatingActionButton fab = findViewById(R.id.fab);

        //http://mobiledevhub.com/2017/11/15/android-fundamentals-requesting-multiple-runtime-permissions/
        requestMultiplePermissions();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainActivity.this, Barcode_search.class);
                startActivity(myintent);
            }


        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        view_product = findViewById(R.id.viewItem);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //getting the recyclerview from xml


        //initializing the productlist


        db = StockIt.db;

        itemList = new ArrayList<>();
        Cursor product_data = db.stock_details();

        while (product_data.moveToNext()) {
            ItemList item = new ItemList();


            item.setId( product_data.getInt(0));
            item.setItemName(product_data.getString(1));
            item.setItemDesc( product_data.getString(6));
            item.setItemPrice(product_data.getInt(3));
            item.setItemStock(product_data.getInt(2));
            switch (item.getItemName()){
                case "Salt":
                    item.setItemImage(R.drawable.salt);
                    break;
                case "Sugar":
                    item.setItemImage(R.drawable.sugar);
                    break;
                case "Milk":
                    item.setItemImage(R.drawable.milk);
                    break;
                case "Pepper":
                    item.setItemImage(R.drawable.pepper);
                    break;
                case "Coffee":
                    item.setItemImage(R.drawable.coffee);
                    break;
                default:
                    item.setItemImage(R.drawable.ic_launcher_foreground);
            }
            itemList.add(item);
        }
        //adding some items to our list


        //creating recyclerview adapter
        ItemListAdapter adapter = new ItemListAdapter(this, itemList);

        //setting adapter to recyclerview
        itemRecyclerView.setAdapter(adapter);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void requestMultiplePermissions(){
        List<String> remainingPermissions = new ArrayList<>();
        for (String permission : PERMISSIONS) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                remainingPermissions.add(permission);
            }
        }
        requestPermissions(remainingPermissions.toArray(new String[remainingPermissions.size()]), 101);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 101){
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    if(shouldShowRequestPermissionRationale(permissions[i])){
                        displayAlertMessage("you need to provide accses for both permission for both",
                                new DialogInterface.OnClickListener(){

                                    public void onClick(DialogInterface dialogInterface, int i){
//                                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                                        dialogInterface.dismiss();
                                        requestMultiplePermissions();
                                        //requestPermissions(new String[]{CAMERA} , REQUEST_CAMERA);
//                                                }

                                    }
                                });
                    }
                    return;
                }
            }
            //all is good, continue flow
        }
    }
    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancle", null)
                .create()
                .show();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_alerts) {
            Intent myintent1 = new Intent(MainActivity.this, alerts_list.class);
            startActivity(myintent1);
        } else if (id == R.id.nav_settings) {
            Intent myintent2 = new Intent(MainActivity.this, Settings.class);
            startActivity(myintent2);
        } else if (id == R.id.nav_account) {
            Intent myintent3 = new Intent(MainActivity.this, Account_managment.class);
            startActivity(myintent3);
        } else if (id == R.id.home) {
            Intent myintent4 = new Intent(MainActivity.this, MainActivity.class);
            startActivity(myintent4);
        }  else if (id == R.id.nav_help) {
            Intent myintent5 = new Intent(MainActivity.this, Help_Page.class);
            startActivity(myintent5);
        } else if (id == R.id.nav_About_us) {
            Intent myintent6 = new Intent(MainActivity.this, About_us.class);
            startActivity(myintent6);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
