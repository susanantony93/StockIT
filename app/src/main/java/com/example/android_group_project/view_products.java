package com.example.android_group_project;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import android.widget.Toast;

public class view_products extends AppCompatActivity {

    private static final String TAG = "ktr";
    private EditText stock_edit;
    private TextView itemname,itemprice,itemvendor;
    private EditText itemstock;
    private ImageView itemimage;
    private Button stock_edit_button;
    ProductdbHelper db;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_products);
        db=StockIt.db;
        stock_edit_button = (Button)findViewById(R.id.editStock);
        itemname=findViewById(R.id.itemname);
        itemprice=findViewById(R.id.price);
        itemstock  = (EditText) findViewById(R.id.itemstock);
        itemvendor  = findViewById(R.id.description);
        itemimage = (ImageView) findViewById(R.id.itemImage);
        Intent data=getIntent();
        int id=data.getIntExtra("id",-1);
        Log.d(TAG, "onCreate: id"+id);
        if(id!=-1)
            cursor=db.stock_details(id);
            cursor.moveToFirst();
        ItemList item = new ItemList();


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
            case "Colgate":
                item.setItemImage(R.drawable.colgate);
                break;
            case "Coffee":
                item.setItemImage(R.drawable.coffee);
                break;
            default:
                item.setItemImage(R.drawable.ic_launcher_foreground);
        }
        itemprice.setText("Price: $"+Double.toString(item.getItemPrice()));
        itemname.setText(item.getItemName());
        itemstock.setText(Integer.toString(item.getItemStock()));
        itemvendor.setText("Vendor Name:"+ (item.getItemDesc()));
        itemimage.setImageResource(item.getItemImage());

        stock_edit_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                stock_edit.setEnabled(true);
            }
        });

    }

}
