package com.example.labassignment9;

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}

//package com.example.webservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final char DEGREE = '\u00B0';
    public static final String STARTING_URL
            = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String KEY_NAME = "&appid=";
    //private String city = "Long Beach,CA";
    private String city = "London,UK";
    private String key = "772253c413f7ed5ca045586d1b2b53bf";
    String json;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","Main Activity - Entering onCreate");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Do background here
                //String baseUrl = "", cityString = "", keyName = "", key = "";
                //Create an object RemoteDataReader
                RemoteDataReader rdr =
                        new RemoteDataReader( STARTING_URL, city, KEY_NAME, key );
                //Get the JSON string
                json= rdr.getData();
                Log.w("TEST", json);

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        TemperatureParser parser = new TemperatureParser( json );
                        Log.w("MainActivity", String.valueOf(parser.getTemperatureK( ))+ DEGREE+"K");
                    }
                });
            }
        });
    }
}
