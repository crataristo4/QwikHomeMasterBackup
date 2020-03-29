package com.example.handyman.activities.home.about;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.handyman.R;
import com.example.handyman.databinding.SettingsActivityBinding;

public class SettingsActivity extends AppCompatActivity {
    SettingsActivityBinding settingsActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsActivityBinding = DataBindingUtil.setContentView(this, R.layout.settings_activity);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

}
