package com.example.Weather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        int hasProfile = checkIfThereIsAProfile(dataBaseHelper);
        int hasDefault = checkIfThereIsADefaultProfile(dataBaseHelper);
        String titleThereIsNoProfileExists = "There is no weather profile exists!";
        String msgThereIsNoProfileExists = "Would you like to continue to add a new one?";
        String titleThereIsNoDefaultProfile = "There is no default weather profile set!";
        String msgThereIsNoDefaultProfile = "Would you like to continue to select one?";

        if (hasProfile == 0){
            showAlertDialogButtonClicked(msgThereIsNoProfileExists, titleThereIsNoProfileExists);
        } else if (hasDefault == 0){
            showAlertDialogButtonClicked(msgThereIsNoDefaultProfile, titleThereIsNoDefaultProfile);
        }else {
            Intent intent=new Intent(MainActivity.this,ShowCurrentProfileActivity.class);//CHANGE THIS TO CURRENT LAYOUT
            MainActivity.this.startActivity(intent);
            finish();
        }

    }

    private int checkIfThereIsAProfile(DataBaseHelper dataBaseHelper) {
        Cursor allProfiles = dataBaseHelper.getAllProfiles();
        return allProfiles.getCount();
    }

    private int checkIfThereIsADefaultProfile(DataBaseHelper dataBaseHelper) {
        Cursor allProfiles = dataBaseHelper.getDefaultProfile();
        return allProfiles.getCount();
    }


    private void showAlertDialogButtonClicked(String msg, String title) {
        final char whichState;
        if (msg.contains("add")){
            whichState='1';//Add state
        } else {
            whichState='2';//Default state
        }
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);

        // add the buttons
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               if(whichState=='1'){
                   Intent intent = new Intent(MainActivity.this,AddProfileActivity.class);
                   MainActivity.this.startActivity(intent);
                   finish();
               }else{
                   Intent intent = new Intent(MainActivity.this,SelectProfileActivity.class);
                   MainActivity.this.startActivity(intent);
                   finish();
               }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
