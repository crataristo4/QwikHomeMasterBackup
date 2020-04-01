package com.example.handyman.activities.home.serviceTypes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.handyman.R;
import com.example.handyman.adapters.StylesAdapter;
import com.example.handyman.databinding.ActivityDetailsScrollingBinding;
import com.example.handyman.models.StylesItemModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class DetailsScrollingActivity extends AppCompatActivity {

    private ActivityDetailsScrollingBinding activityDetailsScrollingBinding;
    private DatabaseReference databaseReference;
    private StylesAdapter adapter;
    private String name, about, image, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsScrollingBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_scrolling);

        setSupportActionBar(activityDetailsScrollingBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        activityDetailsScrollingBinding.fabCall.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        Intent intent = getIntent();
        if (intent != null) {
            String position = intent.getStringExtra("position");
            assert position != null;
            name = intent.getStringExtra("name");
            about = intent.getStringExtra("about");
            image = intent.getStringExtra("image");
            userId = intent.getStringExtra("userId");
        }


        Log.i("onCreate: ", userId);

        databaseReference = FirebaseDatabase.getInstance()
                .getReference()
                .child("Styles")
                .child(userId);
        databaseReference.keepSynced(true);

        activityDetailsScrollingBinding.collapsingToolBar.setTitle(name);
        activityDetailsScrollingBinding.contentDetails.txtAbout.setText(about);
        //activityDetailsScrollingBinding.userImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_in));

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
        FirebaseRecyclerOptions<StylesItemModel> options =
                new FirebaseRecyclerOptions.Builder<StylesItemModel>().setQuery(query,
                        StylesItemModel.class)
                        .build();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
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
