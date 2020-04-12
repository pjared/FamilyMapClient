package com.example.familymap;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Proxy.DataCache;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        View view = getLayoutInflater().inflate(R.layout.activity_settings, null);

        //need to set the settings every time it opens based on datacache

        DataCache dCache = DataCache.getInstance();
        Switch lifeStorySwitch = (Switch) findViewById(R.id.lifeStorySwitch);
        Switch familyTreeSwitch = (Switch) findViewById(R.id.familyTreeSwitch);
        Switch spouseSwitch = (Switch) findViewById(R.id.spouseSwitch);
        Switch fatherSwitch = (Switch) findViewById(R.id.fatherSwitch);
        Switch motherSwitch = (Switch) findViewById(R.id.motherSwitch);
        Switch maleSwitch = (Switch) findViewById(R.id.maleSwitch);
        Switch femaleSwitch = (Switch) findViewById(R.id.femaleSwitch);

        lifeStorySwitch.setChecked(dCache.isLifeStorySwitch());
        familyTreeSwitch.setChecked(dCache.isFamilyTreeSwitch());
        spouseSwitch.setChecked(dCache.isSpouseSwitch());
        fatherSwitch.setChecked(dCache.isFatherSwitch());
        motherSwitch.setChecked(dCache.isMotherSwitch());
        maleSwitch.setChecked(dCache.isMaleSwitch());
        femaleSwitch.setChecked(dCache.isFemaleSwitch());

        lifeStorySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache dCache1 = DataCache.getInstance();
            if(isChecked) {
                dCache1.setLifeStorySwitch(true);
            } else {
                dCache1.setLifeStorySwitch(false);
            }
        });

        familyTreeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache dCache12 = DataCache.getInstance();
            if(isChecked) {
                dCache12.setFamilyTreeSwitch(true);
            } else {
                dCache12.setFamilyTreeSwitch(false);
            }
        });

        spouseSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache dCache13 = DataCache.getInstance();
            if(isChecked) {
                dCache13.setSpouseSwitch(true);
            } else {
                dCache13.setSpouseSwitch(false);
            }
        });

        fatherSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache dCache14 = DataCache.getInstance();
            if(isChecked) {
                dCache14.setFatherSwitch(true);
            } else {
                dCache14.setFatherSwitch(false);
            }
        });

        motherSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache dCache15 = DataCache.getInstance();
            if(isChecked) {
                dCache15.setMotherSwitch(true);
            } else {
                dCache15.setMotherSwitch(false);
            }
        });

        maleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache dCache16 = DataCache.getInstance();
            if(isChecked) {
                dCache16.setMaleSwitch(true);
            } else {
                dCache16.setMaleSwitch(false);
            }
        });

        femaleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DataCache dCache17 = DataCache.getInstance();
            if(isChecked) {
                dCache17.setFemaleSwitch(true);
            } else {
                dCache17.setFemaleSwitch(false);
            }
        });

        TableRow logout = (TableRow) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    public void logout() {
        Toast.makeText(this, "Logged Out",Toast.LENGTH_SHORT).show();
        //clear the data then go back
        Intent intent = new Intent(this, LoginFragment.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
}
