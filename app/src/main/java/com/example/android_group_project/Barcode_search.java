package com.example.android_group_project;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission_group.CAMERA;

public class Barcode_search extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    //https://www.youtube.com/watch?v=otkz5Cwdw38
    private static final int REQUEST_CAMERA = 1;

    // variable to store barcode result
    private ZXingScannerView scannerView;

    TextView textView;

    // variable to scan barcode
    BarcodeDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_search);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermission()){
                ///  Toast.makeText(Barcode_search.this, "Permission is granted" , Toast.LENGTH_LONG).show();
            }
            else{
                requestPermission();
            }

        }

        // creating new barcode detector object
        detector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE).build();
        if(!detector.isOperational()){
            textView.setText("could not set up detector");
        }


    }


    private boolean checkPermission(){
        return  (ContextCompat.checkSelfPermission(Barcode_search.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    public void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }


    public void onResume(){
        super.onResume();

        // when ever activity is created again or resumed it will show ask for scan again
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkPermission()){
                if(scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else{
                requestPermission();
            }
        }
    }

    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();

        // dialog box to ask user preference if they want to scan again or it is ok
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("scan result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                // if user clicks ok user will be redirected to view product page
                // that shows product details stored by user for that barcode.
                Intent intent = new Intent(Barcode_search.this, view_product.class);

                intent.putExtra("barcodeResult", scanResult);
                startActivity(intent);
            }
        });
        builder.setNeutralButton("Scan Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                scannerView.resumeCameraPreview(Barcode_search.this);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
    }


}
