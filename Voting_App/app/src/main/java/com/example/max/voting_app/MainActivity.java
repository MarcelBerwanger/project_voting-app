package com.example.max.voting_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button addButton;
    ListView listview;
    String[] restaurants;
    private static final String DEBUG_TAG = "HttpExample";
    private String stringUrl = "https://query.yahooapis.com/v1/public/yql?q=select%20title%2C%20item.title%2C%20item.yweather%3Acondition.temp%20from" +
            "%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Innsbruck%2C%20" +
            "at%22)&format=xml&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.add_button);
        listview = (ListView) findViewById(R.id.listView);

        listview.setClickable(false);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(getBaseContext(), "Netzwerkverbindung erfolgreich", Toast.LENGTH_SHORT).show();
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            Toast.makeText(getBaseContext(), "Netzwerkverbindung fehlgeschlagen", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClick(View v){

        Intent intent = new Intent(MainActivity.this, AddRestaurantActivity.class);
        startActivity(intent);

    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {
                String[] text = result.split(">");

                restaurants = text[1].split(" ");

                listview.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_checked, restaurants));
/*
                String[] text = result.split(">");
                String[] subtextTitle;
                String[] subtextTemperature;
                String[] subtextCondition;
                int temperatureCelsius = 0;

                subtextCondition = text[9].split("<");
                subtextTitle = text[5].split("<");
                subtextTemperature = text[7].split("\"");

                temperatureCelsius = Integer.parseInt(subtextTemperature[3]);
                temperatureCelsius = (int) ((temperatureCelsius - 32) / 1.8);

                conditionFor.setText(subtextCondition[0]);
                title.setText(subtextTitle[0]);
                temperature.setText("Temperatur: " + temperatureCelsius + "°C");
*/
            }catch (Exception e){

                Toast.makeText(getBaseContext(), "!!!Something went wrong!!!", Toast.LENGTH_LONG).show();

            }

        }


        // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }


        // Reads an InputStream and converts it to a String.
        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);

        }

    }

}