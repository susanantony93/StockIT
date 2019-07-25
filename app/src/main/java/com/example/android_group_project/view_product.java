package com.example.android_group_project;

//https://codinginflow.com/tutorials/android/sqlite-recyclerview/part-1-layouts-contract-sqliteopenhelper
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



public class view_product extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "ktr";
    private EditText stock_edit;
    private TextView itemname,itemprice,itemvendor,itembarcode;
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
        toolbar.setTitle("Product details");


        db=StockIt.db;
        stock_save_button = findViewById(R.id.save_btn);
        itemname =findViewById(R.id.itemname);
        itemprice =findViewById(R.id.price);
        itemstock  = findViewById(R.id.itemstock);
        itemvendor  = findViewById(R.id.description);
        itemimage = findViewById(R.id.itemImage);
        itembarcode = findViewById(R.id.barcode_num);

        Intent data=getIntent();


        int id=data.getIntExtra("id",-1);
        Log.d(TAG, "onCreate: id"+id);

        String scan_result = data.getStringExtra("barcodeResult");

        // this will check id of product that is passed to these page and will fill the details into
        // form according to it
        // data of product are fetched from database adapter using cursor.
        if (id != ' ') {
            if (id != -1)
                cursor = db.stock_details(id);
            // checking barcode result
            else if(scan_result != null) {
                Log.i("Code", scan_result);
                cursor = db.stock_details_barcode(scan_result);
            }

            // filling all the details of product from cursor
            cursor.moveToFirst();
            item.setId( cursor.getInt(0));
            id_update =id;
            item.setItemName(cursor.getString(1));
            item.setItemDesc( cursor.getString(6));
            item.setItemPrice(cursor.getInt(3));
            item.setItemStock(cursor.getInt(2));
            item.setItemBarcode(cursor.getString(4));
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
            itemprice.setText("$ " + Double.toString(item.getItemPrice()));
            itemname.setText(item.getItemName());
            itemstock.setText(Integer.toString(item.getItemStock()));
            itemvendor.setText((item.getItemDesc()));
            itemimage.setImageResource(item.getItemImage());
            itembarcode.setText(item.getItemBarcode());
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


        // this is pop window that ask user's permission before updating stock details.
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        // if user click yes that it will redirect to home page and details of stock update will be stored in database.
                        Toast.makeText(getApplicationContext()," Inventory is updated" ,Toast.LENGTH_LONG).show();

                        if(!itemstock.getText().toString().equals("")){
                            db.update_stock( id_update , Integer.parseInt(itemstock.getText().toString()));
                        }else{
                            Toast.makeText(getApplicationContext()," Please enter stock value ",Toast.LENGTH_LONG).show();

                        }

                        // if stock is less then reorder level it will show a toast.
                        if(!itemstock.getText().toString().equals("")) {
                            if (Integer.parseInt(itemstock.getText().toString()) <= 10) {
                                Toast.makeText(getApplicationContext(), " Stock for " + item.getItemName() + " is less then reorder level", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext()," Please enter stock value ",Toast.LENGTH_LONG).show();
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

        // dialog box
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        stock_save_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // stock_edit.setEnabled(true);
                builder.setMessage("Do you want to save stock?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

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
            Intent myintent1 = new Intent(view_product.this, alerts_list.class);
            startActivity(myintent1);
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
