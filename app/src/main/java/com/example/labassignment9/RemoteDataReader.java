package com.example.labassignment9;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RemoteDataReader {
    private String urlString;

    public RemoteDataReader( String baseUrl, String cityString,
                             String keyName, String key ) {
        urlString = baseUrl+cityString+keyName+key;
    }

    public String getData( ) {
        Log.d("TEST", "Remote Data Reader - entering get data");
        try {
            // Establish the connection

            URL url = new URL( urlString );
            HttpURLConnection con = ( HttpURLConnection ) url.openConnection();
            con.connect( );
            int code = con.getResponseCode();
            if (code !=  200) {
                Log.d("TEST", String.valueOf(code));
                throw new IOException("Invalid response from server: " + code);
            }


            // Get the input stream and prepare to read
            InputStream is = con.getInputStream();
            BufferedReader br =
                    new BufferedReader( new InputStreamReader(is));

            // Read the data
            String dataRead = "";
            String line = br.readLine( );
            while ( line != null ) {
                dataRead += line;
                line = br.readLine( );
            }

            is.close( );
            con.disconnect( );
            return dataRead;
        } catch( Exception e ) {
            return "Error";
        }
    }
}
