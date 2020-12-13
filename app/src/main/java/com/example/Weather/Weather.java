package com.example.Weather;


public class Weather {
    private  String  profileName;
    private  String cityName;
    private  String unit;
    private  String APIKey;
    private  boolean defaultOne;

    public Weather() {

    }

    public boolean isDefaultOne() {
        return defaultOne;
    }

    public void setDefaultOne(boolean defaultOne) {
        this.defaultOne = defaultOne;
    }

    public String getProfileName() {
        return profileName;
    }


    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Weather(String cityName, String unit, String APIKey) {
        this.cityName = cityName;
        this.unit = unit;
        this.APIKey = APIKey;
    }



    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

}
