package com.example.android_group_project;

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
    final String Sql_create_product_table = "CREATE TABLE " +
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
        values.put(ProductdbHelper.Barcode, 12);
        values.put(ProductdbHelper.Reorder_level, 10);
        values.put(ProductdbHelper.Vendor_name, "Windsor");
        id=db.insert(table,null,values);
        Log.d(TAG, "addData: "+id);
        values.put(ProductdbHelper.Product_name, "Sugar");
        values.put(ProductdbHelper.Qty_in_Stock, 15);
        values.put(ProductdbHelper.Price, 25);
        values.put(ProductdbHelper.Barcode, 25);
        values.put(ProductdbHelper.Reorder_level, 10);
        values.put(ProductdbHelper.Vendor_name, "Windsor");
        id=db.insert(table,null,values);
        Log.d(TAG, "addData: "+id);
        values.put(ProductdbHelper.Product_name, "Colgate");
        values.put(ProductdbHelper.Qty_in_Stock, 14);
        values.put(ProductdbHelper.Price, 14);
        values.put(ProductdbHelper.Barcode, 14);
        values.put(ProductdbHelper.Reorder_level, 10);
        values.put(ProductdbHelper.Vendor_name, "Windsor");
        id=db.insert(table,null,values);
        Log.d(TAG, "addData: "+id);
        values.put(ProductdbHelper.Product_name, "Coffee");
        values.put(ProductdbHelper.Qty_in_Stock, 32);
        values.put(ProductdbHelper.Price, 35);
        values.put(ProductdbHelper.Barcode, 35);
        values.put(ProductdbHelper.Reorder_level, 10);
        values.put(ProductdbHelper.Vendor_name, "Windsor");
        id=db.insert(table,null,values);
        Log.d(TAG, "addData: "+id);
        values.put(ProductdbHelper.Product_name, "Milk");
        values.put(ProductdbHelper.Qty_in_Stock, 12);
        values.put(ProductdbHelper.Price, 18);
        values.put(ProductdbHelper.Barcode, 18);
        values.put(ProductdbHelper.Reorder_level, 10);
        values.put(ProductdbHelper.Vendor_name, "Windsor");
        id=db.insert(table,null,values);
        Log.d(TAG, "addData: "+id);





// Insert the new row, returning the primary key value of the new row

    }

    public Cursor stock_details() {


        String query = "SELECT  * FROM " + table;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data_products = db.rawQuery(query, null);
        return data_products;
    }

    public Cursor stock_details(int id) {


        String query = "SELECT  * FROM " + table +" WHERE "+Product_ID+"="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data_products = db.rawQuery(query, null);
        return data_products;
    }
}