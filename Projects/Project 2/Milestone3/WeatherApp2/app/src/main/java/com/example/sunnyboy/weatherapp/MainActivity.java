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
import android.widget.ToggleButton;
import android.widget.CompoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import model.Weather;

public class MainActivity extends AppCompatActivity {

    public EditText city, zipcode;
    public TextView temp, humidity, wind, description, location;
    String cityValue, zipcodeValue;
    ToggleButton toggleTemp;
    Weather weather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = (EditText)findViewById(R.id.cityEditText);
        zipcode = (EditText)findViewById(R.id.zipcodeEditText);

        temp = (TextView)findViewById(R.id.tempValueTextView);
        humidity = (TextView)findViewById(R.id.humidityValuetextView);
        wind = (TextView)findViewById(R.id.windValueTextView);
        description = (TextView)findViewById(R.id.descriptionValueTextView);
        location = (TextView)findViewById(R.id.locationValueTextView);
        toggleTemp = (ToggleButton) findViewById(R.id.toggleTemp);

        temp.setVisibility(View.INVISIBLE);
        humidity.setVisibility(View.INVISIBLE);
        wind.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        location.setVisibility(View.INVISIBLE);
        toggleTemp.setVisibility(View.INVISIBLE);

        // toggle temperature between celcius and fehrenheit
        toggleTemp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show temperature in Fehrenheit
                    temp.setText("Temperature:  "+ String.valueOf(weather.getTempFeh()) + " F");

                } else {
                    temp.setText("Temperature:  "+ String.valueOf(weather.getTemperature()) + " C");
                }
            }
        });
    }


    public void getWeather(View view) {

        cityValue = city.getText().toString();
        zipcodeValue = zipcode.getText().toString();

        if(!cityValue.isEmpty() || !zipcodeValue.isEmpty()) {
            String apiURL = callweatherAPI(cityValue, zipcodeValue);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            WeatherTask wt = new WeatherTask();
            wt.execute(new String[]{apiURL});

        }
    }

    private String callweatherAPI(String cityValue, String zipcodeValue) {
        String APIKEY = "97dec25fb7cca21e8c614057e8900423";
        String apiCallBase = "http://api.openweathermap.org/data/2.5/weather";

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

    private class WeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {

            String data = getWeatherCreateConnection(params[0]);

            if(data != null){
                weather = getWeatherData(data);
                Log.v("Data: ", String.valueOf(weather.getTemperature()));
            }

            return weather;

        }

        @Override
        protected void onPostExecute(Weather weather){

            super.onPostExecute(weather);

            if(weather.getDescription().isEmpty()){
                description.setVisibility(View.VISIBLE);
                description.setText("Connection not established. Please try again!");
            }
            else{
                temp.setVisibility(View.VISIBLE);
                humidity.setVisibility(View.VISIBLE);
                wind.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                location.setVisibility(View.VISIBLE);
                toggleTemp.setVisibility(View.VISIBLE);

                temp.setText("Temperature:  "+ String.valueOf(weather.getTemperature()) + " C");
                humidity.setText("Humidity:  "+ String.valueOf(weather.getHumidity()) + " %");
                wind.setText("Wind Speed:  "+ String.valueOf(weather.getWindSpeed()) + " mps");
                description.setText("Condition: "+ weather.getDescription());
                location.setText("Location: "+ weather.getLocation());
            }
        }
    }

    public String getWeatherCreateConnection(String query) {
        HttpURLConnection connection = null;
        InputStream input = null;

        try {
            URL url = new URL(query);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //Read response
            try {
                StringBuilder sb = new StringBuilder();
                input = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(input));
                String line = null;

                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\r\n");
                }
                br.close();
                return sb.toString();
            } finally {
                connection.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Weather getWeatherData(String data){

        Weather weather = new Weather();

        //create JSONobject
        try {
            JSONObject json = new JSONObject(data);

            Double temp2 = json.getJSONObject("main").getDouble("temp");
            int humid =  json.getJSONObject("main").getInt("humidity");
            Double windSpeed = json.getJSONObject("wind").getDouble("speed");

            String desc = "";
            try {
                desc = json.getJSONArray("weather").getJSONObject(0).getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            weather.setTemperature(temp2.intValue());
            weather.setHumidity(humid);
            weather.setWindSpeed(windSpeed.intValue());
            weather.setDescription(desc);
            weather.setLocation(json.getString("name"));

            return weather;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}
