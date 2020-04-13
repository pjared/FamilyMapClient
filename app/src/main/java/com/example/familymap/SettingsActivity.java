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
            dCache.setSettingsChange(true);
            if(isChecked) {
                dCache.setLifeStorySwitch(true);
            } else {
                dCache.setLifeStorySwitch(false);
            }
        });

        familyTreeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dCache.setSettingsChange(true);
            if(isChecked) {
                dCache.setFamilyTreeSwitch(true);
            } else {
                dCache.setFamilyTreeSwitch(false);
            }
        });

        spouseSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dCache.setSettingsChange(true);
            if(isChecked) {
                dCache.setSpouseSwitch(true);
            } else {
                dCache.setSpouseSwitch(false);
            }
        });

        fatherSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dCache.setSettingsChange(true);
            if(isChecked) {
                dCache.setFatherSwitch(true);
            } else {
                dCache.setFatherSwitch(false);
            }
        });

        motherSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dCache.setSettingsChange(true);
            if(isChecked) {
                dCache.setMotherSwitch(true);
            } else {
                dCache.setMotherSwitch(false);
            }
        });

        maleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dCache.setSettingsChange(true);
            if(isChecked) {
                dCache.setMaleSwitch(true);
            } else {
                dCache.setMaleSwitch(false);
            }
        });

        femaleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dCache.setSettingsChange(true);
            if(isChecked) {
                dCache.setFemaleSwitch(true);
            } else {
                dCache.setFemaleSwitch(false);
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
