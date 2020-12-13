package com.example.Weather;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
public class DownloadWeatherConditions extends AsyncTask<String, String, String> {

    Activity activity;

    public DownloadWeatherConditions(Activity activity ) {
         this.activity = activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpManager.getData(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
            if(s==null){
                ((ShowCurrentProfileActivity) activity).errorInConnection();
            }else {
                super.onPostExecute(s);
                List<WeatherConditionsFiveDays> weathers = WeatherJasonParser.getDataFromJason(s);
                assert weathers != null;
                ((ShowCurrentProfileActivity) activity).fillCurrentWeather(weathers);
            }
        }


}