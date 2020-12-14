package com.example.Weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

//https://api.openweathermap.org/data/2.5/onecall?lat=33.441792&lon=-94.037689&exclude=hourly,daily&appid={API key}
public class ShowCurrentProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private TextView temperatureValue;
    private TextView minTemperatureValue;
    private TextView maxTemperatureValue;
    private TextView cityName;
    private TextView titleMax;
    private TextView titleMin;
    private ImageView imageCondition;
    private String urlAPI;
    private  Cursor currentProfile;
    private Button changeUnits;
    private String currentUnit;
    private ArrayList<WeatherConditionsFiveDays> weat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_show_current_profile);
        temperatureValue= (TextView) findViewById(R.id.temp_value_id);
        minTemperatureValue= (TextView) findViewById(R.id.min_value_id);
        titleMin= (TextView) findViewById(R.id.min_text_id);
        titleMax= (TextView) findViewById(R.id.max_text_id);
        maxTemperatureValue= (TextView) findViewById(R.id.max_value_id);
        cityName= (TextView) findViewById(R.id.city_name_curlay_id);
        imageCondition=(ImageView) findViewById(R.id.image_condition);
        changeUnits=(Button) findViewById(R.id.change_units);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!isNetworkAvailable()){
            errorInConnection();
        }
        DataBaseHelper dataBaseHelper = new DataBaseHelper(ShowCurrentProfileActivity.this);
            currentProfile = dataBaseHelper.getDefaultProfile();
            currentProfile.moveToNext();
            currentUnit = currentProfile.getString(4);
            DownloadWeatherConditions connectionAsyncTask = new DownloadWeatherConditions(this);
            urlAPI = "https://api.openweathermap.org/data/2.5/forecast?q=" + currentProfile.getString(0) + "&units=" + currentProfile.getString(3) + "&appid="+currentProfile.getString(1);
            connectionAsyncTask.execute(urlAPI);
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_profile:
                Intent intentAdd = new Intent(ShowCurrentProfileActivity.this,AddProfileActivity.class);
                ShowCurrentProfileActivity.this.startActivity(intentAdd);
                finish();
                return true;
            case R.id.switch_to_profile:
                Intent intentSwitch = new Intent(ShowCurrentProfileActivity.this,SelectProfileActivity.class);
                ShowCurrentProfileActivity.this.startActivity(intentSwitch);
                return true;
            case R.id.edit_current_profile:
                Intent intentEdit = new Intent(ShowCurrentProfileActivity.this,EditCurrentProfile.class);
                ShowCurrentProfileActivity.this.startActivity(intentEdit);
                finish();
                return true;
            case R.id.show_5_days:
                Intent intentFive = new Intent(ShowCurrentProfileActivity.this,ShowFiveProfileActivity.class);
                intentFive.putExtra("list",weat);
                startActivity(intentFive);
                ShowCurrentProfileActivity.this.startActivity(intentFive);
                finish();
                return true;
            default:
                return false;
        }
    }

    public void fillCurrentWeather(List<WeatherConditionsFiveDays> weathers) {
        final int tempVal,tempMin,tempMax;
        final int buffTempVal,buffTempMin,buffTempMax;
        final String unit;
        for (int i =0 ; i<weathers.size(); ++i){
            weathers.get(i).setUnit(currentProfile.getString(3));
        }
        weat= (ArrayList<WeatherConditionsFiveDays>) weathers;
        tempMax=weathers.get(0).getMaxTemperature();
        tempMin=weathers.get(0).getMinTemperature();
        tempVal=weathers.get(0).getTemperature();
        unit=weathers.get(0).getUnit();
        Log.e("here",unit);
        changeUnits.setVisibility(View.VISIBLE);
        titleMax.setVisibility(View.VISIBLE);
        titleMin.setVisibility(View.VISIBLE);
        if (weathers.get(0).getUnit().equals("metric")){
            currentUnit="metric";
            temperatureValue.setText(String.valueOf(weathers.get(0).getTemperature())+ "°C");
            minTemperatureValue.setText(String.valueOf(weathers.get(0).getMinTemperature())+ "°C");
            maxTemperatureValue.setText(String.valueOf(weathers.get(0).getMaxTemperature())+ "°C");
        }else{
            currentUnit="imperial";
            temperatureValue.setText(String.valueOf(weathers.get(0).getTemperature())+ "°F");
            minTemperatureValue.setText(String.valueOf(weathers.get(0).getMinTemperature())+ "°F");
            maxTemperatureValue.setText(String.valueOf(weathers.get(0).getMaxTemperature())+ "°F");
        }

        cityName.setText(currentProfile.getString(0));
        Log.e("ww",String.valueOf( weathers.get(0).getMainWeather()));
        if (weathers.get(0).getMainWeather()>=200 && weathers.get(0).getMainWeather()<=232)
            imageCondition.setImageResource(R.drawable.thunderstorm);
        else if (weathers.get(0).getMainWeather()>=300 && weathers.get(0).getMainWeather()<=321)
            imageCondition.setImageResource(R.drawable.rain);
        else if (weathers.get(0).getMainWeather()>=500 && weathers.get(0).getMainWeather()<=531)
            imageCondition.setImageResource(R.drawable.rain);
        else if (weathers.get(0).getMainWeather()>=600 && weathers.get(0).getMainWeather()<=622)
            imageCondition.setImageResource(R.drawable.snow);
        else if (weathers.get(0).getMainWeather()==800)
            imageCondition.setImageResource(R.drawable.sunny);
        else if (weathers.get(0).getMainWeather()>=801 && weathers.get(0).getMainWeather()<=804)
            imageCondition.setImageResource(R.drawable.partly_cloudy);
        else
            imageCondition.setImageResource(R.drawable.partly_cloudy);

        changeUnits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUnit.equals("metric")){
                    if(unit.equals("metric")){
                        temperatureValue.setText(String.valueOf(convertToImperial(tempVal))+ "°F");
                        minTemperatureValue.setText(String.valueOf(convertToImperial(tempMin))+ "°F");
                        maxTemperatureValue.setText(String.valueOf(convertToImperial(tempMax))+ "°F");
                    }else{
                        temperatureValue.setText(String.valueOf(tempVal)+ "°F");
                        minTemperatureValue.setText(String.valueOf(tempMin)+ "°F");
                        maxTemperatureValue.setText(String.valueOf(tempMax)+ "°F");
                    }

                    currentUnit="imperial";
                }else{
                    if(unit.equals("metric")){
                        temperatureValue.setText(String.valueOf(tempVal)+ "°C");
                        minTemperatureValue.setText(String.valueOf(tempMin)+ "°C");
                        maxTemperatureValue.setText(String.valueOf(tempMax)+ "°C");
                    }else{
                        temperatureValue.setText(String.valueOf(convertToMetric(tempVal))+ "°C");
                        minTemperatureValue.setText(String.valueOf(convertToMetric(tempMin))+ "°C");
                        maxTemperatureValue.setText(String.valueOf(convertToMetric(tempMax))+ "°C");
                    }
                    currentUnit="metric";

                }
            }
        });



    }
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    public void errorInConnection() {
        Toast.makeText(this, "Your connection is bad, make sure you have entered correct information ", Toast.LENGTH_LONG).show();

    }

    public int convertToImperial(int temp){
        return ((temp*9)/5)+32;
    }

    public int convertToMetric(int temp){
        return ((temp-32)*5)/9;
    }
}