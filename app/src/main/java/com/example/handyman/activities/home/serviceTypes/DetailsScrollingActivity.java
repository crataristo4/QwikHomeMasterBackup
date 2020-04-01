package com.example.handyman.activities.home.serviceTypes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;

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
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class DetailsScrollingActivity extends AppCompatActivity {

    private ActivityDetailsScrollingBinding activityDetailsScrollingBinding;
    private DatabaseReference databaseReference;
    StylesAdapter adapter;

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
        Log.i("onCreate: ", position);
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

        RecyclerView recyclerView = activityDetailsScrollingBinding.contentDetails.rvStylesItem;
        recyclerView.setHasFixedSize(true);

        //querying the database BY NAME
        Query query = databaseReference.orderByChild("price");
        FirebaseRecyclerOptions<ServicePerson> options =
                new FirebaseRecyclerOptions.Builder<ServicePerson>().setQuery(query,
                        ServicePerson.class)
                        .build();

        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        } else if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        }

        adapter = new StylesAdapter(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
