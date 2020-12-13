package com.example.Weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class EditCurrentProfile extends AppCompatActivity {

    private EditText txtCityName;
    private EditText txtProfileName;
    private EditText txtAPIKey;
    private CheckBox boxDefault;
    private CheckBox boxCelsius;
    private Button   update;
    private Button   cancel;
    private DataBaseHelper dataBaseHelper;
    private Cursor currentProfile;
    private String realProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_edit_current_profile);
        txtCityName= (EditText) findViewById(R.id.city_name_edit_id);
        txtProfileName=(EditText) findViewById(R.id.prof_edit_name_id);
        txtAPIKey=(EditText)findViewById(R.id.api_key_edit_id);
        boxCelsius=(CheckBox) findViewById(R.id.unit_edit_id);
        boxDefault=(CheckBox) findViewById(R.id.default_edit_id);
        update=(Button) findViewById(R.id.update_edit_id);
        cancel=(Button) findViewById(R.id.cancel_edit_id);
        dataBaseHelper=new DataBaseHelper(EditCurrentProfile.this);
        currentProfile= dataBaseHelper.getDefaultProfile();
        currentProfile.moveToNext();
        realProfile=currentProfile.getString(2);
        txtCityName.setText(currentProfile.getString(0));
        txtProfileName.setText(currentProfile.getString(2));
        txtAPIKey.setText(currentProfile.getString(1));
        String unit=currentProfile.getString(3);
        if(unit.contains("imperial"))
            boxCelsius.setChecked(false);
        else
            boxCelsius.setChecked(true);
        boxDefault.setChecked(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        update.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                DataBaseHelper dataBaseHelper =new DataBaseHelper(EditCurrentProfile.this);
                if(txtAPIKey.getText().toString().isEmpty() || txtCityName.getText().toString().isEmpty() || txtProfileName.getText().toString().isEmpty()){
                    Toast.makeText(EditCurrentProfile.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                } else if(dataBaseHelper.checkIfThisProfileExists(txtProfileName.getText().toString()).getCount()==1 && (!txtProfileName.getText().toString().equals(currentProfile.getString(2)))){
                    Toast.makeText(EditCurrentProfile.this, "This profile name already exists", Toast.LENGTH_LONG).show();
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
                    dataBaseHelper.updateProfile(weather,realProfile);
                    Intent intent=new Intent(EditCurrentProfile.this,MainActivity.class);//CHANGE THIS TO CURRENT LAYOUT
                    EditCurrentProfile.this.startActivity(intent);
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditCurrentProfile.this,MainActivity.class);//CHANGE THIS TO CURRENT LAYOUT
                EditCurrentProfile.this.startActivity(intent);
                finish();
            }
        });

    }
}