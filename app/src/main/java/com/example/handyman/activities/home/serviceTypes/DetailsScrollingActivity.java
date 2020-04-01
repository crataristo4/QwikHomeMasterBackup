package com.example.handyman.activities.home.serviceTypes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.handyman.R;
import com.example.handyman.adapters.StylesAdapter;
import com.example.handyman.databinding.ActivityDetailsScrollingBinding;
import com.example.handyman.models.ServicePerson;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailsScrollingActivity extends AppCompatActivity {

    private ActivityDetailsScrollingBinding activityDetailsScrollingBinding;
    private List<ServicePerson> servicePersonList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsScrollingBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_scrolling);

        setSupportActionBar(activityDetailsScrollingBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        activityDetailsScrollingBinding.fabCall.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        Intent intent = getIntent();

        String position = intent.getStringExtra("position");
        String name = intent.getStringExtra("name");
        String about = intent.getStringExtra("about");
        String image = intent.getStringExtra("image");

        assert position != null;
        databaseReference = FirebaseDatabase.getInstance().getReference("Services").child(position);

        activityDetailsScrollingBinding.collapsingToolBar.setTitle(name);
        activityDetailsScrollingBinding.contentDetails.txtAbout.setText(about);
        activityDetailsScrollingBinding.userImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_in));

        Glide.with(this)
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(activityDetailsScrollingBinding.userImage);


        loadStyleItems();
    }

    private void loadStyleItems() {

        servicePersonList = new ArrayList<>();
        RecyclerView recyclerView = activityDetailsScrollingBinding.contentDetails.rvStylesItem;
        recyclerView.setHasFixedSize(true);
        StylesAdapter adapter = new StylesAdapter(this, servicePersonList);

        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        } else if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    ServicePerson person = ds.getValue(ServicePerson.class);
                    servicePersonList.add(person);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);



    }
}
