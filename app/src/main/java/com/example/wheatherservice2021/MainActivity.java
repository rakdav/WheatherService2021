package com.example.wheatherservice2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean binded=false;
    private WheatherSevice wheatherSevice;
    private TextView textViewWeather;
    private EditText editTextLocation;
    private Button buttonWeather;

    ServiceConnection weatherServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            WheatherSevice.LocalWheatherBinder binder=(WheatherSevice.LocalWheatherBinder)service;
            wheatherSevice=binder.getService();
            binded=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binded=false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,WheatherSevice.class);
        bindService(intent,weatherServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(binded){
            unbindService(weatherServiceConnection);
            binded=false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewWeather=findViewById(R.id.textViewWeather);
        editTextLocation=findViewById(R.id.editText_Location);
        buttonWeather=findViewById(R.id.button_weather);
        buttonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location=editTextLocation.getText().toString();
                String weaher=wheatherSevice.getWeatherToday(location);
                textViewWeather.setText(weaher);
            }
        });
    }
}