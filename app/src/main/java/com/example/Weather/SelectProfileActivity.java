package com.example.Weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Objects;

public class SelectProfileActivity extends AppCompatActivity {

    private ArrayList<ProfileItemData> listdata;
    private RecyclerView recyclerView;
    private ProfileListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_select_profile);

        listdata= new ArrayList<ProfileItemData>();
        recyclerView = (RecyclerView) findViewById(R.id.select_recycler_view);

    }

    @Override
    protected void onResume() {
        super.onResume();
        DataBaseHelper dataBaseHelper =new DataBaseHelper(SelectProfileActivity.this);
        Cursor allProfiles=dataBaseHelper.getAllProfilesExpectDefault();
        while (allProfiles.moveToNext()){
            String profileName = allProfiles.getString(2);
            ProfileItemData profile=new ProfileItemData();
            profile.setProfileName(profileName);
            listdata.add(profile);
        }
        adapter = new ProfileListAdapter(listdata);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }


}


