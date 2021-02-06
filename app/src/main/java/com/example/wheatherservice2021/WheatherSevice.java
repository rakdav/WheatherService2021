package com.example.wheatherservice2021;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.DataFormatException;

public class WheatherSevice extends Service {
    private static String LOG_TAG="WheatherService";
    private static final Map<String,String> wheatherData=new HashMap<String, String>();
    private final IBinder binder=new LocalWheatherBinder();

    public class LocalWheatherBinder extends Binder{
        public WheatherSevice getService()
        {
            return WheatherSevice.this;
        }
    }
    public WheatherSevice() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG,"OnBind");
        return this.binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(LOG_TAG,"OnRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG,"onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG,"onDestroy");
    }

    public String getWeatherToday(String location)
    {
        Date now=new Date();
        DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
        String dayString=df.format(now);
        String keyLocalandDay=location+"$"+dayString;
        String weather=wheatherData.get(keyLocalandDay);
        if(weather!=null) return weather;
        String[] weathers=new String[]{"Rainy","Hot","Cool","Warm","Snowy"};
        int i=new Random().nextInt(5);
        weather=weathers[i];
        wheatherData.put(keyLocalandDay,weather);
        return weather;
    }
}