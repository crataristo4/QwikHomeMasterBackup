package com.example.handyman.activities.home.serviceTypes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handyman.R;
import com.example.handyman.adapters.AllBarbersAdapter;
import com.example.handyman.databinding.ActivityAllBarbersBinding;
import com.example.handyman.models.ServicePerson;
import com.example.handyman.utils.MyConstants;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

//TODO change class name
public class AllBarbersActivity extends AppCompatActivity {
    private ActivityAllBarbersBinding activityAllBarbersBinding;
    private AllBarbersAdapter adapter;
    private RecyclerView recyclerView;
    private String serviceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAllBarbersBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_barbers);

        initRecyclerView();

    }

    private void initRecyclerView() {

        Intent getIntent = getIntent();

        //88getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent != null) {

            switch (getIntent.getStringExtra(MyConstants.ACCOUNT_TYPE)) {
                case MyConstants.BARBERS:
                    serviceType = MyConstants.BARBERS;
                    setTitle(serviceType);

                    break;
                case MyConstants.WOMEN_HAIR_STYLIST:
                    serviceType = MyConstants.WOMEN_HAIR_STYLIST;
                    setTitle(serviceType);

                    break;
                case MyConstants.INTERIOR_DERCORATOR:
                    serviceType = MyConstants.INTERIOR_DERCORATOR;
                    setTitle(serviceType);

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + getIntent.getStringExtra(MyConstants.ACCOUNT_TYPE));
            }

        }


        DatabaseReference allBarbersDbRef = FirebaseDatabase.getInstance()
                .getReference()
                .child(MyConstants.SERVICES)
                .child(serviceType);
        allBarbersDbRef.keepSynced(true);

        recyclerView = activityAllBarbersBinding.rvAllBarbers;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //querying the database BY NAME
        Query query = allBarbersDbRef.orderByChild("name");

        FirebaseRecyclerOptions<ServicePerson> options =
                new FirebaseRecyclerOptions.Builder<ServicePerson>().setQuery(query,
                        ServicePerson.class)
                        .build();

        //DISPLAY different layout for screen orientation
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        } else {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        }

        adapter = new AllBarbersAdapter(options, AllBarbersActivity.this);

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
