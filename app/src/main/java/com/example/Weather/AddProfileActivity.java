package com.example.Weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class AddProfileActivity extends AppCompatActivity {

    private EditText txtCityName;
    private EditText txtProfileName;
    private EditText txtAPIKey;
    private CheckBox boxDefault;
    private CheckBox boxCelsius;
    private Button   save;
    private Button   cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_add_profile);
        txtCityName= (EditText) findViewById(R.id.city_name_id);
        txtProfileName=(EditText) findViewById(R.id.prof_name_id);
        txtAPIKey=(EditText)findViewById(R.id.api_key_id);
        boxCelsius=(CheckBox) findViewById(R.id.unit_id);
        boxDefault=(CheckBox) findViewById(R.id.default_id);
        save=(Button) findViewById(R.id.save_id);
        cancel=(Button) findViewById(R.id.cancel_id);
    }

    @Override
    protected  void onResume() {
        super.onResume();
        save.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                DataBaseHelper dataBaseHelper =new DataBaseHelper(AddProfileActivity.this);
                if(txtAPIKey.getText().toString().isEmpty() || txtCityName.getText().toString().isEmpty() || txtProfileName.getText().toString().isEmpty()){
                    Toast.makeText(AddProfileActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                } else if(dataBaseHelper.checkIfThisProfileExists(txtProfileName.getText().toString()).getCount()==1){
                    Toast.makeText(AddProfileActivity.this, "This profile name already exists", Toast.LENGTH_LONG).show();
                }
                else {
                    if (boxDefault.isChecked()){
                        dataBaseHelper.resetDefaultProfile();
                    }
                    Weather weather=new Weather();
                    weather.setAPIKey(txtAPIKey.getText().toString());
                    weather.setCityName(txtCityName.getText().toString());
                    weather.setProfileName(txtProfileName.getText().toString());
                    weather.setDefaultOne(boxDefault.isChecked());
                    weather.setUnit(boxCelsius.isChecked() ? "metric" : "imperial");
                    dataBaseHelper.insertWeatherProfile(weather);
                    Intent intent=new Intent(AddProfileActivity.this,MainActivity.class);//CHANGE THIS TO CURRENT LAYOUT
                    AddProfileActivity.this.startActivity(intent);
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddProfileActivity.this,MainActivity.class);//CHANGE THIS TO CURRENT LAYOUT
                AddProfileActivity.this.startActivity(intent);
                finish();
            }
        });

    }
}