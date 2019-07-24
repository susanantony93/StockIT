package com.example.android_group_project;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Manifest;

public class view_product extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "ktr";
    private EditText stock_edit;
    private TextView itemname,itemprice,itemvendor;
    private EditText itemstock;
    private ImageView itemimage;
    private Button stock_save_button;
    int id_update = 0;
    ProductdbHelper db;
    Cursor cursor;
    ItemList item = new ItemList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        db=StockIt.db;
        stock_save_button = findViewById(R.id.save_btn);
        itemname =findViewById(R.id.itemname);
        itemprice =findViewById(R.id.price);
        itemstock  = findViewById(R.id.itemstock);
        itemvendor  = findViewById(R.id.description);
        itemimage = findViewById(R.id.itemImage);
        Intent data=getIntent();


        int id=data.getIntExtra("id",-1);
        Log.d(TAG, "onCreate: id"+id);

        String scan_result = data.getStringExtra("barcodeResult");
//        Toast.makeText(getApplicationContext()," scan_result " + scan_result ,Toast.LENGTH_LONG).show();
        if (id != ' ') {
            if (id != -1)
                cursor = db.stock_details(id);
            else if(scan_result != null) {
                Log.i("Code", scan_result);
                cursor = db.stock_details_barcode(scan_result);
            }

            cursor.moveToFirst();
            item.setId( cursor.getInt(0));
            id_update =id;
            item.setItemName(cursor.getString(1));
            item.setItemDesc( cursor.getString(6));
            item.setItemPrice(cursor.getInt(3));
            item.setItemStock(cursor.getInt(2));
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
            itemprice.setText(Double.toString(item.getItemPrice()));
            itemname.setText(item.getItemName());
            itemstock.setText(Integer.toString(item.getItemStock()));
            itemvendor.setText((item.getItemDesc()));
            itemimage.setImageResource(item.getItemImage());

        }


        else{
            Log.i("Code",scan_result);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Toast.makeText(getApplicationContext(),"yes clicked " ,Toast.LENGTH_LONG).show();

                        db.update_stock( id_update , Integer.parseInt(itemstock.getText().toString()));

                        if(Integer.parseInt(itemstock.getText().toString()) <= 10){
                            Toast.makeText(getApplicationContext()," Stock for " + item.getItemName() + " is less then reorder level",Toast.LENGTH_LONG).show();
                        }
                        Intent home = new Intent(view_product.this , MainActivity.class);
                        startActivity(home);

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        Toast.makeText(getApplicationContext(),"edit has canceled" ,Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        item.setId( cursor.getInt(0));
        item.setItemName(cursor.getString(1));
        item.setItemDesc( cursor.getString(6));
        item.setItemPrice(cursor.getInt(2));
        item.setItemStock(cursor.getInt(3));
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
        itemprice.setText(Double.toString(item.getItemPrice()));
        itemname.setText(item.getItemName());
        itemstock.setText(Integer.toString(item.getItemStock()));
        itemvendor.setText(item.getItemDesc());
        itemimage.setImageResource(item.getItemImage());

//        stock_edit_button.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v){
//               // stock_edit.setEnabled(true);
//
//
//            }
//        });
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
            Intent myintent1 = new Intent(view_product.this, alerts_list.class);
            startActivity(myintent1);
        } else if (id == R.id.nav_settings) {
            Intent myintent2 = new Intent(view_product.this, Settings.class);
            startActivity(myintent2);
        } else if (id == R.id.nav_account) {
            Intent myintent3 = new Intent(view_product.this, Account_managment.class);
            startActivity(myintent3);
        } else if (id == R.id.home) {
            Intent myintent4 = new Intent(view_product.this, MainActivity.class);
            startActivity(myintent4);
        }  else if (id == R.id.nav_help) {
            Intent myintent5 = new Intent(view_product.this, Help_Page.class);
            startActivity(myintent5);
        } else if (id == R.id.nav_About_us) {
            Intent myintent6 = new Intent(view_product.this, About_us.class);
            startActivity(myintent6);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
