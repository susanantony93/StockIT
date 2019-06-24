package com.example.android_group_project;

import android.content.Context;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private EditText stock_edit;
    private Button stock_edit_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stock_edit_button = (Button)findViewById(R.id.editStock);


        setContentView(R.layout.view_products);
        stock_edit_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                stock_edit.setEnabled(true);
            }
        });

    }

}
