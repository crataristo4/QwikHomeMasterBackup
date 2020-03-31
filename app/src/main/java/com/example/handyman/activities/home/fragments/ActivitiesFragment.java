package com.example.handyman.activities.home.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handyman.R;
import com.example.handyman.adapters.AllServicesAdapter;
import com.example.handyman.databinding.FragmentActivitiesBinding;
import com.example.handyman.models.SinglePerson;
import com.example.handyman.utils.MyConstants;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivitiesFragment extends Fragment {

    private FragmentActivitiesBinding fragmentActivitiesBinding;
    private RecyclerView rvBarbers, rvHairStylist, rvInteriorDeco;
    private AllServicesAdapter allServicesAdapter, allServicesAdapter1, allServicesAdapter2;
    private DatabaseReference dbBarbersRef;
    private static final String TAG = "ActivityFragment";

    public ActivitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentActivitiesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_activities, container, false);
        return fragmentActivitiesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");

        rvBarbers = fragmentActivitiesBinding.rvBarbers;
        rvHairStylist = fragmentActivitiesBinding.rvHairStylist;
        rvInteriorDeco = fragmentActivitiesBinding.rvInteriorDeco;

        rvBarbers.setHasFixedSize(true);
        rvBarbers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvHairStylist.setHasFixedSize(true);
        rvHairStylist.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvInteriorDeco.setHasFixedSize(true);
        rvInteriorDeco.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
        loadData1();
        loadData2();

    }

    private void loadData1() {
        dbBarbersRef = FirebaseDatabase.getInstance().getReference()
                .child(MyConstants.SERVICES)
                .child(MyConstants.WOMEN_HAIR_STYLIST);
        dbBarbersRef.keepSynced(true);

        //querying the database base of the time posted
        Query query = dbBarbersRef.orderByChild("name");

        FirebaseRecyclerOptions<SinglePerson> options =
                new FirebaseRecyclerOptions.Builder<SinglePerson>().setQuery(query,
                        SinglePerson.class)
                        .build();

        allServicesAdapter1 = new AllServicesAdapter(options);
        rvHairStylist.setAdapter(allServicesAdapter1);
    }

    private void loadData2() {
        dbBarbersRef = FirebaseDatabase.getInstance().getReference()
                .child(MyConstants.SERVICES)
                .child(MyConstants.INTERIOR_DERCORATOR);
        dbBarbersRef.keepSynced(true);

        //querying the database base of the time posted
        Query query = dbBarbersRef.orderByChild("name");

        FirebaseRecyclerOptions<SinglePerson> options =
                new FirebaseRecyclerOptions.Builder<SinglePerson>().setQuery(query,
                        SinglePerson.class)
                        .build();

        allServicesAdapter2 = new AllServicesAdapter(options);
        rvInteriorDeco.setAdapter(allServicesAdapter2);
    }

    private void loadData() {

        dbBarbersRef = FirebaseDatabase.getInstance().getReference()
                .child(MyConstants.SERVICES)
                .child(MyConstants.BARBERS);
        dbBarbersRef.keepSynced(true);

        //querying the database base of the time posted
        Query query = dbBarbersRef.orderByChild("name");

        FirebaseRecyclerOptions<SinglePerson> options =
                new FirebaseRecyclerOptions.Builder<SinglePerson>().setQuery(query,
                        SinglePerson.class)
                        .build();

        allServicesAdapter = new AllServicesAdapter(options);
        rvBarbers.setAdapter(allServicesAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        allServicesAdapter.startListening();
        allServicesAdapter1.startListening();
        allServicesAdapter2.startListening();


        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        allServicesAdapter.stopListening();
        allServicesAdapter1.stopListening();
        allServicesAdapter2.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
