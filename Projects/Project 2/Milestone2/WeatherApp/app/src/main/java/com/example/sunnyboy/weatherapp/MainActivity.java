package com.example.sunnyboy.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public EditText city, zipcode;
    public TextView temp, humidity, wind, description;
    String cityValue, zipcodeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = (EditText)findViewById(R.id.cityEditText);
        //cityValue = city.getText().toString();

        zipcode = (EditText)findViewById(R.id.zipcodeEditText);
        //zipcodeValue = zipcode.getText().toString();

        //country = (EditText)findViewById(R.id.countryEditText);
        //countryValue = country.getText().toString();

        temp = (TextView)findViewById(R.id.tempValueTextView);
        //humidity = (TextView)findViewById(R.id.humidityValuetextView);
        //wind = (TextView)findViewById(R.id.windValueTextView);
        //description = (TextView)findViewById(R.id.descriptionValueTextView);

    }


    public void getWeather(View view) {

        cityValue = city.getText().toString();
        zipcodeValue = zipcode.getText().toString();
        //countryValue = country.getText().toString();

        String apiURL = callweatherAPI(cityValue,zipcodeValue);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        //String dataInString = getWeatherCreateConnection(apiURL);

        //WeatherTask2 wt2 = new WeatherTask2();
        //wt2.execute(new String[]{apiURL});
        //wt2.execute();

        WeatherTask wt = new WeatherTask();
        wt.execute(new String[]{apiURL});

        //Double getTemp = getWeatherData(dataInString);


        //temp.setText(getTemp.toString());
    }

    private String callweatherAPI(String cityValue, String zipcodeValue) {
        String APIKEY = "97dec25fb7cca21e8c614057e8900423";

        String apiCallBase = "http://api.openweathermap.org/data/2.5/weather";
        String iconBase = "http://openweathermap.org/img/w/";

        String query = "";

        if(!zipcodeValue.equals("")){
            query = "zip="+ zipcodeValue;
        }
        else if(!cityValue.equals("")){
            query = "q="+ cityValue;
        }

        String apiCall = apiCallBase + "?" + query + "&appid=" + APIKEY;

        return apiCall;

    }

    class WeatherTask2 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
            temp.setText("working on it!");
//            humidity.setText("working on it!");
//            wind.setText("working on it!");
        }

        @Override
        protected String doInBackground(String... urls){
            try{
                URL url = new URL(urls[0]);
                //URL url = new URL("http://api.openweathermap.org/data/2.5/weather?zip=110075&appid=97dec25fb7cca21e8c614057e8900423");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = br.readLine()) != null){
                        sb.append(line).append("\n");
                    }
                    br.close();
                    return sb.toString();
                }
                finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response){
            if(response == null){
                response = "There was an error!";
            }

            Log.i("INFO", response);
            temp.setText(response);
        }
    }


    private class WeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
            temp.setText("working on it!");
//            humidity.setText("working on it!");
//            wind.setText("working on it!");
        }

        @Override
        protected String doInBackground(String... params) {

            String data = getWeatherCreateConnection(params[0]);

            String temp = String.valueOf(getWeatherData(data));

            Log.v("Data: ", temp);

            return temp;

            //return null;
        }

        @Override
        protected void onPostExecute(String response){
            if(response == null){
                response = "There was an error!";
            }

            Log.i("INFO", response);
            temp.setText("Temperature:  "+ response + " C");
        }
    }

    //private JSONObject getJsonData(String )

    public String getWeatherCreateConnection(String query) {
        HttpURLConnection connection = null;
        InputStream input = null;

        try {
            URL url = new URL(query);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //connection.setDoInput(true);
            //connection.setDoOutput(true);
            //connection.connect();

            //Read response
            try {
                StringBuilder sb = new StringBuilder();
                //input = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;

                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\r\n");
                }
                br.close();
                return sb.toString();
            } finally {
                connection.disconnect();
            }

            //input.close();
            //connection.disconnect();

            //return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //return null;
    }

    public Integer getWeatherData(String data){

        //create JSONobject
        try {
            JSONObject json = new JSONObject(data);

            JSONObject tempJson = json.getJSONObject("main");
            Double temp = tempJson.getDouble("temp");

            return temp.intValue() - 273;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;


    }
}
