package com.example.Weather;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FiveProfileListAdapter extends  RecyclerView.Adapter<FiveProfileListAdapter.ViewHolder> {

    private ArrayList<WeatherConditionsFiveDays> listdata = new ArrayList<WeatherConditionsFiveDays>();
    private Context context;
    private Activity activity;
    private ArrayList<String> days= new ArrayList<String>();
    private String currentDay;
    private String currentUnit;

    public FiveProfileListAdapter(ArrayList<WeatherConditionsFiveDays> listdata,String currentDay) {
        this.listdata = listdata;
        this.currentDay=currentDay;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        activity = (Activity) context;
        days.add("SATURDAY");
        days.add("SUNDAY");
        days.add("MONDAY");
        days.add("TUESDAY");
        days.add("WEDNESDAY");
        days.add("THURSDAY");
        days.add("FRIDAY");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.five_profile_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final WeatherConditionsFiveDays myListData = listdata.get(position);
        final int tempVal,tempMin,tempMax;
        tempMax=myListData.getMaxTemperature();
        tempMin=myListData.getMinTemperature();
        tempVal=myListData.getTemperature();
        final String unit = myListData.getUnit();
        holder.changeUnit.setVisibility(View.VISIBLE);

        if (myListData.getUnit().equals("metric")){
            currentUnit="metric";
            holder.tempValue.setText(String.valueOf(myListData.getTemperature())+ "°C");
            holder.tempMin.setText(String.valueOf(myListData.getMinTemperature())+ "°C");
            holder.tempMax.setText(String.valueOf(myListData.getMaxTemperature())+ "°C");
        }else{
            currentUnit="imperial";
            holder.tempValue.setText(String.valueOf(myListData.getTemperature())+ "°F");
            holder.tempMin.setText(String.valueOf(myListData.getMinTemperature())+ "°F");
            holder.tempMax.setText(String.valueOf(myListData.getMaxTemperature())+ "°F");
        }
        holder.changeUnit.setVisibility(View.VISIBLE);
        holder.dayName.setText(String.valueOf(days.get(days.indexOf(currentDay)+position)));
        if (listdata.get(position).getMainWeather()>=200 &&listdata.get(position).getMainWeather()<=232)
            holder.imageWeather.setImageResource(R.drawable.thunderstorm);
        else if (listdata.get(position).getMainWeather()>=300 && listdata.get(position).getMainWeather()<=321)
            holder.imageWeather.setImageResource(R.drawable.rain);
        else if (listdata.get(position).getMainWeather()>=500 && listdata.get(position).getMainWeather()<=531)
            holder.imageWeather.setImageResource(R.drawable.rain);
        else if (listdata.get(position).getMainWeather()>=600 && listdata.get(position).getMainWeather()<=622)
            holder.imageWeather.setImageResource(R.drawable.snow);
        else if (listdata.get(position).getMainWeather()==800)
            holder.imageWeather.setImageResource(R.drawable.sunny);
        else if (listdata.get(position).getMainWeather()>=801 && listdata.get(position).getMainWeather()<=804)
            holder.imageWeather.setImageResource(R.drawable.partly_cloudy);
        else
            holder.imageWeather.setImageResource(R.drawable.partly_cloudy);

        holder.changeUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUnit.equals("metric")){
                    if(unit.equals("metric")){
                        holder.tempValue.setText(String.valueOf(convertToImperial(tempVal))+ "°F");
                        holder.tempMin.setText(String.valueOf(convertToImperial(tempMin))+ "°F");
                        holder.tempMax.setText(String.valueOf(convertToImperial(tempMax))+ "°F");
                    }else{
                        holder.tempValue.setText(String.valueOf(tempVal)+ "°F");
                        holder.tempMin.setText(String.valueOf(tempMin)+ "°F");
                        holder.tempMax.setText(String.valueOf(tempMax)+ "°F");
                    }

                    currentUnit="imperial";
                }else{
                    if(unit.equals("metric")){
                        holder.tempValue.setText(String.valueOf(tempVal)+ "°C");
                        holder.tempMin.setText(String.valueOf(tempMin)+ "°C");
                        holder.tempMax.setText(String.valueOf(tempMax)+ "°C");
                    }else{
                        holder.tempValue.setText(String.valueOf(convertToMetric(tempVal))+ "°C");
                        holder.tempMin.setText(String.valueOf(convertToMetric(tempMin))+ "°C");
                        holder.tempMax.setText(String.valueOf(convertToMetric(tempMax))+ "°C");
                    }
                    currentUnit="metric";

                }
            }


        });

    }

    public int convertToImperial(int temp){
        return ((temp*9)/5)+32;
    }

    public int convertToMetric(int temp){
        return ((temp-32)*5)/9;
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayName;
        public TextView tempValue;
        public TextView tempMax;
        public TextView tempMin;
        public ImageView imageWeather;
        public Button changeUnit;
        public ConstraintLayout constraintLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.dayName = (TextView) itemView.findViewById(R.id.five_day_name);
            this.constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.select_layout_linear_five);
            this.changeUnit= (Button) itemView.findViewById(R.id.five_change);
            this.imageWeather = (ImageView) itemView.findViewById(R.id.five_weather_image);
            this.tempMax = (TextView) itemView.findViewById(R.id.five_max);
            this.tempMin = (TextView) itemView.findViewById(R.id.five_min);
            this.tempValue = (TextView) itemView.findViewById(R.id.five_temp_value);
        }
    }
}
