package com.example.Weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class WeatherConditionsFiveDays implements Serializable {
    private int minTemperature;
    private int maxTemperature;
    private int mainWeather;
    private int temperature;
    private String unit;


    public WeatherConditionsFiveDays(int minTemperature, int maxTemperature, int mainWeather, int temperature,String unit) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.mainWeather = mainWeather;
        this.temperature = temperature;
        this.unit = unit;


    }

    public WeatherConditionsFiveDays() {

    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    protected WeatherConditionsFiveDays(Parcel in) {
        minTemperature = in.readInt();
        maxTemperature = in.readInt();
        mainWeather = in.readInt();
        temperature = in.readInt();
    }


    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getMainWeather() {
        return mainWeather;
    }

    public void setMainWeather(int mainWeather) {
        this.mainWeather = mainWeather;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

}
