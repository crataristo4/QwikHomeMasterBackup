package com.example.handyman.activities.home;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.handyman.R;
import com.example.handyman.activities.home.about.AboutActivity;
import com.example.handyman.activities.home.about.JobTypesActivity;
import com.example.handyman.activities.home.about.SettingsActivity;
import com.example.handyman.activities.welcome.SplashScreenActivity;
import com.example.handyman.databinding.ActivityMainBinding;
import com.example.handyman.utils.DisplayViewUI;
import com.example.handyman.utils.MyConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    public static final String IS_DIALOG_SHOWN = "dialogShown";
    public static final String PREFS = "PREFS";
    private static final String TAG = "MainActivity";
    public static String serviceType, name, imageUrl, about, uid;
    FirebaseUser firebaseUser;
    private ActivityMainBinding activityMainBinding;
    private static DatabaseReference serviceTypeDbRef, serviceAccountDbRef;
    private FirebaseAuth mAuth;
    private static Object mContext;

    public static Context getAppContext() {
        return (Context) mContext;
    }

    public static void retrieveServiceType() {

        serviceTypeDbRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Services")
                .child("ServiceType")
                .child(uid);

        serviceTypeDbRef.keepSynced(true);

        serviceTypeDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceType = (String) dataSnapshot.child("accountType").getValue();
                Log.i(TAG, "onDataChange: " + serviceType);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DisplayViewUI.displayToast(MainActivity.this, databaseError.getMessage());
            }
        });


    }

    private void setUpAppBarConfig() {
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_activities, R.id.navigation_home, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(activityMainBinding.bottomNavigationView, navController);

    }

    private void checkDisplayAlertDialog() {
        SharedPreferences pref = getSharedPreferences(PREFS, 0);
        boolean alertShown = pref.getBoolean(IS_DIALOG_SHOWN, false);

        if (!alertShown) {

            DisplayViewUI.displayAlertDialogMsg(this,
                    "Want to be seen by more users?\nPlease edit profile and add more skills",
                    "OK", (dialog, which) -> {
                        if (which == -1) {

                            dialog.dismiss();
                            Intent gotoAbout = new Intent(MainActivity.this, AboutActivity.class);
                            gotoAbout.putExtra(MyConstants.ACCOUNT_TYPE, serviceType);
                            startActivity(gotoAbout);
                        }
                    });

            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean(IS_DIALOG_SHOWN, true);
            edit.apply();
        }
    }

    private void SendUserToLoginActivity() {
        Intent Login = new Intent(MainActivity.this, SplashScreenActivity.class);
        startActivity(Login);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main_settings,menu);
        return true;
    }

    public static void retrieveUserDetails(TextView txtName, TextView txtAbout, CircleImageView photo) {
        serviceAccountDbRef = FirebaseDatabase.getInstance()
                .getReference().child("Services")
                .child(serviceType)
                .child(uid);
        serviceAccountDbRef.keepSynced(true);

        serviceAccountDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = (String) dataSnapshot.child("name").getValue();
                about = (String) dataSnapshot.child("about").getValue();
                imageUrl = (String) dataSnapshot.child("image").getValue();

                txtName.setText(name);
                txtAbout.setText(about);
                Glide.with(getAppContext())
                        .load(MainActivity.imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(photo);

                Log.i(TAG, "onReceive: " + name + about + imageUrl);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        uid = firebaseUser.getUid();

        setUpAppBarConfig();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent gotoSettingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
               /* Bundle bundle = new Bundle();
                bundle.putString(MyConstants.NAME,name);
                bundle.putString(MyConstants.ABOUT,about);
                bundle.putString(MyConstants.IMAGE_URL,imageUrl);

                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(bundle);
*/

                startActivity(gotoSettingsIntent);

                return true;

            case R.id.action_logout:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this,SplashScreenActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

                break;

            case R.id.action_addStyles:
                startActivity(new Intent(MainActivity.this, JobTypesActivity.class));
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        retrieveServiceType();

        try {
            assert firebaseUser != null;

            if (mAuth.getCurrentUser() == null || !firebaseUser.isEmailVerified()) {
                SendUserToLoginActivity();
            } else {
                checkDisplayAlertDialog();
                // retrieveUserDetails();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
