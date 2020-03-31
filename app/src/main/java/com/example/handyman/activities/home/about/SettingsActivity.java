package com.example.handyman.activities.home.about;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.example.handyman.R;
import com.example.handyman.activities.home.fragments.ProfileFragment;
import com.example.handyman.activities.home.fragments.ProfilePhotoEditFragment;
import com.example.handyman.databinding.SettingsActivityBinding;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements

        ProfileFragment.OnFragmentInteractionListener,
        ProfilePhotoEditFragment.OnFragmentInteractionListener {

    SettingsActivityBinding settingsActivityBinding;
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsActivityBinding = DataBindingUtil.setContentView(this, R.layout.settings_activity);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right,
                        R.anim.exit_to_right,
                        R.anim.enter_from_right,
                        R.anim.exit_to_right)
                .add(R.id.containerSettings, profileFragment)
                .commit();
        setTitle("Settings");


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setTitle("Settings");

    }
}
