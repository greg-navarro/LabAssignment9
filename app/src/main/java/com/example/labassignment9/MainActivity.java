package com.example.labassignment9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private double lastTemp;
    private String postfix = "K";

    // views
    public TextView output;
    public EditText cityEditText;
    public EditText stateEditText;
    private Button getButton;
    private Button toggle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bind views
        output = (TextView) findViewById(R.id.WeatherDataTextView);
        cityEditText = (EditText) findViewById(R.id.editTextCity);
        cityEditText.setText("London");

        stateEditText = (EditText) findViewById(R.id.editTextState);
        stateEditText.setText("UK");
        getButton = (Button) findViewById(R.id.button);
        toggle = (Button) findViewById(R.id.button2);

        getButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String city = cityEditText.getText().toString();
                        String state = stateEditText.getText().toString();
                        String weatherQuery = city + "," + state;
                        getData(weatherQuery);
                        Log.v("MainActivity", "MainActivity - sent weather query!");
                    }
                });
        toggle.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        if (postfix.equals("C")) {
                            postfix = "F";
                        } else {
                            postfix = "C";
                        }

                        String out = String.valueOf(lastTemp)+ DEGREE+postfix;
                        output.setText("Current temp: " + out);
                    }
                });

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
                Log.d("TEST", json);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TemperatureParser parser = new TemperatureParser( json );
                        if (postfix.equals("C")) {
                            lastTemp = parser.getTemperatureC();
                        } else {
                            lastTemp = parser.getTemperatureF();
                        }
                        String out = String.valueOf(lastTemp)+ DEGREE+postfix;
                        output.setText("Current temp: " + out);
                        Log.d("MainActivity", out);
                    }
                });
            }
        });
    } // end oncreate

    public void getData(String newCity) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Do background here
                //String baseUrl = "", cityString = "", keyName = "", key = "";
                //Create an object RemoteDataReader
                RemoteDataReader rdr =
                        new RemoteDataReader( STARTING_URL, newCity, KEY_NAME, key );
                //Get the JSON string
                json= rdr.getData();
                Log.w("TEST", json);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TemperatureParser parser = new TemperatureParser( json );
                        if (postfix.equals("C")) {
                            lastTemp = parser.getTemperatureC();
                        } else {
                            lastTemp = parser.getTemperatureF();
                        }
                        String out = String.valueOf(lastTemp)+ DEGREE+postfix;
                        output.setText("Current temp: " + out);
                        Log.d("MainActivity", out);
                    }
                });
            }
        });
    }
}
