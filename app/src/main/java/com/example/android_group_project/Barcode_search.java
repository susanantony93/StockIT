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

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;

    TextView textView;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("scan result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
//                startActivity(intent);
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
