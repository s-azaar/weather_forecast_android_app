package com.example.Weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherJasonParser {

    public static List<WeatherConditionsFiveDays> getDataFromJason(String jason) {
        List<WeatherConditionsFiveDays> weathers= new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(jason);
            String listString= jsonObject.getString("list");
            JSONArray list = new JSONArray(listString);
            for (int i = 0; i <5; i++) {
                   JSONObject weatherObject;
                   JSONObject  listWeather,listMain;

                   weatherObject = (JSONObject) list.get(i);
                   String weatherObj = weatherObject.getString("weather");
                   JSONArray arrayWeather = new JSONArray(weatherObj);
                   listWeather=(JSONObject) arrayWeather.get(0);
                   listMain=weatherObject.getJSONObject("main");

                  WeatherConditionsFiveDays weather = new WeatherConditionsFiveDays();
                   weather.setMainWeather(listWeather.getInt("id"));
                 weather.setMaxTemperature((int) Math.round(listMain.getDouble("temp_max")));
                   weather.setMinTemperature((int) Math.round(listMain.getDouble("temp_min")));
                  weather.setTemperature((int) Math.round(listMain.getDouble("temp")));
                   weathers.add(weather);
           }
        }
        catch (JSONException e) {
            e.printStackTrace();
            return weathers;
        }
        return weathers;
    }
}
