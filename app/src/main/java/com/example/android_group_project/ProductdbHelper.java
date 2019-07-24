package com.example.android_group_project;
//https://codinginflow.com/tutorials/android/sqlite-recyclerview/part-1-layouts-contract-sqliteopenhelper
//https://medium.com/@ssaurel/learn-to-save-data-with-sqlite-on-android-b11a8f7718d3
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class ProductdbHelper extends SQLiteOpenHelper {
    private static final String TAG = "ktr";
    private ProductdbHelper dbHelper;
    private SQLiteDatabase Sqldb;

    public static final String DATABASE_NAME = "grocerylist.db";
    public static final int DATABASE_VERSION = 1;
    public static final String table = "products";
    public static final String Product_ID = "Product_ID";
    public static final String Product_name = "Product_name";
    public static final String Qty_in_Stock = "Qty_in_Stock";
    //public static final String Product_image = "Product_image";
    public static final String Price = "Price";
    public static final String Barcode = "Barcode";
    public static final String Reorder_level = "Reorder_level";
    public static final String Vendor_name = "Vendor_name";
    final String Sql_create_product_table =  "CREATE TABLE " +
            table + " (" +
            Product_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Product_name + " TEXT NOT NULL, " +
            Qty_in_Stock + " INTEGER NOT NULL," +
            //Product_image + " BLOB NOT NULL, " +
            Price + " INTEGER NOT NULL, " +
            Barcode + " TEXT NOT NULL, " +
            Reorder_level + " INTEGER NOT NULL," +
            Vendor_name + " INTEGER NOT NULL " +
            ");";

    public ProductdbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "onCreate: query"+Sql_create_product_table);
        db.execSQL(Sql_create_product_table);
        Log.d(TAG, "onCreate: database created");
        Log.d(TAG, "onCreate: now adding data into database");
        addData(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table);
        onCreate(db);
    }


    public void close()
    {
        dbHelper.close();
    }

    public void addData(SQLiteDatabase db)
    {
        long id;


// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ProductdbHelper.Product_name, "Salt");
        values.put(ProductdbHelper.Qty_in_Stock, 13);
        values.put(ProductdbHelper.Price, 12);
        values.put(ProductdbHelper.Barcode, "9781234567897");
        values.put(ProductdbHelper.Reorder_level, 10);
        values.put(ProductdbHelper.Vendor_name, "Windsor");
        id=db.insert(table,null,values);
        Log.d(TAG, "addData: "+id);
        values.put(ProductdbHelper.Product_name, "Sugar");
        values.put(ProductdbHelper.Qty_in_Stock, 15);
        values.put(ProductdbHelper.Price, 25);
        values.put(ProductdbHelper.Barcode, "725272730706");
        values.put(ProductdbHelper.Reorder_level, 10);
        values.put(ProductdbHelper.Vendor_name, "LorMangal");
        id=db.insert(table,null,values);
        Log.d(TAG, "addData: "+id);
        values.put(ProductdbHelper.Product_name, "Pepper");
        values.put(ProductdbHelper.Qty_in_Stock, 14);
        values.put(ProductdbHelper.Price, 14);
        values.put(ProductdbHelper.Barcode, "639382000393");
        values.put(ProductdbHelper.Reorder_level, 10);
        values.put(ProductdbHelper.Vendor_name, "Costco");
        id=db.insert(table,null,values);
        Log.d(TAG, "addData: "+id);
        values.put(ProductdbHelper.Product_name, "Coffee");
        values.put(ProductdbHelper.Qty_in_Stock, 32);
        values.put(ProductdbHelper.Price, 35);
        values.put(ProductdbHelper.Barcode, "671860013624");
        values.put(ProductdbHelper.Reorder_level, 10);
        values.put(ProductdbHelper.Vendor_name, "GoodFood");
        id=db.insert(table,null,values);
        Log.d(TAG, "addData: "+id);
        values.put(ProductdbHelper.Product_name, "Milk");
        values.put(ProductdbHelper.Qty_in_Stock, 12);
        values.put(ProductdbHelper.Price, 18);
        values.put(ProductdbHelper.Barcode, "705632085943");
        values.put(ProductdbHelper.Reorder_level, 10);
        values.put(ProductdbHelper.Vendor_name, "DearyDen");
        id=db.insert(table,null,values);
        Log.d(TAG, "addData: "+id);

    }

    // method to fetch all stock details
    public Cursor stock_details() {

        String query = "SELECT  * FROM " + table;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data_products = db.rawQuery(query, null);
        return data_products;
    }

    // method to find product details of given product ID
    public Cursor stock_details(int id) {
        String query = "SELECT  * FROM " + table +" WHERE "+Product_ID+"="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data_products = db.rawQuery(query, null);
        return data_products;
    }

    // method to find product list with stock less then reorder level
    public Cursor stock_details_alert() {
        String query = "SELECT  * FROM " + table +" WHERE "+Qty_in_Stock+"< '10'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data_products = db.rawQuery(query, null);
        return data_products;
    }

    // method to find product details with barcode id

    public Cursor stock_details_barcode(String barcode) {
        int id_new = 4;
        if(barcode.equals("9781234567897"))
            id_new = 1;
        else if (barcode.equals("725272730706"))
            id_new = 2;
        else if (barcode.equals("639382000393"))
            id_new = 3;
        else if (barcode.equals("671860013624"))
            id_new = 4;
        else if (barcode.equals("705632085943"))
            id_new = 5;

        String query = "SELECT  * FROM " + table +"  WHERE "+Product_ID+"="+ id_new;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data_products = db.rawQuery(query, null);
        return data_products;
    }

    // method to update stock details
    public boolean update_stock(int id_stock, int stock){

        //String query = "UPDATE TABLE " + table +" SET "+ Qty_in_Stock +" = "+ stock +"  WHERE "+Product_ID+" = "+ id_stock;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+ table +" SET "+ Qty_in_Stock +" = "+ stock +"  WHERE "+Product_ID+" = "+ id_stock);
        return true;
    }

}