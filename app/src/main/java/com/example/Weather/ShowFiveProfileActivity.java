package com.example.Weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ShowFiveProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private Cursor currentProfile;
     private RecyclerView recyclerView;
    private ArrayList<WeatherConditionsFiveDays> weathers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_show_five_profile);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_5);
        Intent i = getIntent();
        weathers = (ArrayList<WeatherConditionsFiveDays>) i.getSerializableExtra("list");


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(weathers == null){
            errorInConnection();
        }else {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(ShowFiveProfileActivity.this);
            currentProfile = dataBaseHelper.getDefaultProfile();
            currentProfile.moveToNext();
            FiveProfileListAdapter adapter;
            adapter = new FiveProfileListAdapter(weathers, LocalDate.now().getDayOfWeek().name());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    public void errorInConnection() {
        Toast.makeText(this, "Your connection is bad, make sure you have entered correct information ", Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_new_profile_five:
                Intent intentAdd = new Intent(ShowFiveProfileActivity.this,AddProfileActivity.class);
                ShowFiveProfileActivity.this.startActivity(intentAdd);
                finish();
                return true;
            case R.id.switch_to_profile_five:
                Intent intentSwitch = new Intent(ShowFiveProfileActivity.this,SelectProfileActivity.class);
                ShowFiveProfileActivity.this.startActivity(intentSwitch);
                return true;
            case R.id.edit_current_profile_five:
                Intent intentEdit = new Intent(ShowFiveProfileActivity.this,EditCurrentProfile.class);
                ShowFiveProfileActivity.this.startActivity(intentEdit);
                finish();
                return true;
            case R.id.show_current_profile_five:
                Intent intentCurrent = new Intent(ShowFiveProfileActivity.this,MainActivity.class);
                ShowFiveProfileActivity.this.startActivity(intentCurrent);
                finish();
                return true;
            default:
                return false;
        }

    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu5);
        popup.show();
    }
}