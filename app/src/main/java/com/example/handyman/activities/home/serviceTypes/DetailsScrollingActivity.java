package com.example.handyman.activities.home.serviceTypes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.handyman.R;
import com.example.handyman.adapters.StylesAdapter;
import com.example.handyman.databinding.ActivityDetailsScrollingBinding;
import com.example.handyman.databinding.LayoutStylesListItemBinding;
import com.example.handyman.models.StylesItemModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions;
import com.shreyaspatil.firebase.recyclerpagination.FirebaseRecyclerPagingAdapter;
import com.shreyaspatil.firebase.recyclerpagination.LoadingState;

import java.util.Objects;

public class DetailsScrollingActivity extends AppCompatActivity {

    private ActivityDetailsScrollingBinding activityDetailsScrollingBinding;
    private DatabaseReference databaseReference;
    // private StylesAdapter adapter;
    FirebaseRecyclerPagingAdapter<StylesItemModel, StylesAdapter.StylesViewHolder> adapter;
    private String name, about, image, userId;
    int numberOfItems = 0;
    private static final String TAG = "DetailsActivity";
    BottomSheetBehavior mBottomSheetBehavior;
    private long mLastClickTime = 0;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);


        activityDetailsScrollingBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_scrolling);


        setSupportActionBar(activityDetailsScrollingBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mBottomSheetBehavior = BottomSheetBehavior.from(activityDetailsScrollingBinding.nestedScroll);




        Intent intent = getIntent();
        if (intent != null) {
            String position = intent.getStringExtra("position");
            assert position != null;
            name = intent.getStringExtra("name");
            about = intent.getStringExtra("about");
            image = intent.getStringExtra("image");
            userId = intent.getStringExtra("userId");
        }

        activityDetailsScrollingBinding.fabCall.setOnClickListener(view -> Snackbar.make(view,
                "Call ".concat(name),
                Snackbar.LENGTH_LONG)
                .setAction("Ok", v -> {

                }).show());


        databaseReference = FirebaseDatabase.getInstance()
                .getReference()
                .child("Styles")
                .child(userId);
        databaseReference.keepSynced(true);
        //get number of items in database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    numberOfItems = (int) ds.getChildrenCount();

                }

                //check if the adapter is empty and notify users
                if (numberOfItems == 0) {

                    activityDetailsScrollingBinding.contentDetails.txtStyleLabel.setText(getResources().getString(R.string.noStyles));


                } else {

                    activityDetailsScrollingBinding.contentDetails.txtStyleLabel.setText(getString(R.string.styles_offered));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
        Query query = databaseReference.orderByChild("price").limitToFirst(3);


        // TODO: 09-Apr-20 load more items on refresh and on recycler view scrolled to bottom


        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(1)
                .setPageSize(3)
                .build();

        DatabasePagingOptions<StylesItemModel> databasePagingOptions = new DatabasePagingOptions.Builder<StylesItemModel>()
                .setLifecycleOwner(this)
                .setQuery(databaseReference, config, StylesItemModel.class)
                .build();



        FirebaseRecyclerOptions<StylesItemModel> options =
                new FirebaseRecyclerOptions.Builder<StylesItemModel>().setQuery(query,
                        StylesItemModel.class)
                        .build();
        adapter = new StylesAdapter(databasePagingOptions);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        }

        /*//on item click
        adapter.setOnItemClickListener((view, position) -> {
            String price = String.valueOf(adapter.getRef(position).child("price"));
            String itemStyleName = adapter.getRef(position).child("styleItem").toString();
            String imageItem = adapter.getRef(position).child("itemImage").toString();

            //scroll app bar to state collapsed when item is clicked
            activityDetailsScrollingBinding.appBar.setExpanded(false, true);

            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return;
            }

            mLastClickTime = SystemClock.elapsedRealtime();

            Bundle bundle = new Bundle();
            bundle.putString(MyConstants.PRICE, price);
            bundle.putString(MyConstants.STYLE, itemStyleName);
            bundle.putString(MyConstants.IMAGE_URL, imageItem);

            SendRequestBottomSheet sendRequestBottomSheet = new SendRequestBottomSheet();
            sendRequestBottomSheet.setArguments(bundle);
            sendRequestBottomSheet.show(getSupportFragmentManager(), "sendRequest");


            // mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            //display details into the bottom sheet
            // activityDetailsScrollingBinding.requestLayout.txtPrice.setText(String.format("GH %s", price));

            // activityDetailsScrollingBinding.requestLayout.txtStyleName.setText(itemStyleName);
*//*

            Glide.with(DetailsScrollingActivity.this)
                    .load(imageItem)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(activityDetailsScrollingBinding.requestLayout.imgItemPhoto);

*//*

        });
*/

        adapter = new FirebaseRecyclerPagingAdapter<StylesItemModel, StylesAdapter.StylesViewHolder>(databasePagingOptions) {
            @Override
            protected void onBindViewHolder(@NonNull StylesAdapter.StylesViewHolder stylesViewHolder, int i, @NonNull StylesItemModel itemModel) {

                stylesViewHolder.layoutStylesListItemBinding.setItem(itemModel);
            }

            @Override
            protected void onLoadingStateChanged(@NonNull LoadingState loadingState) {

                switch (loadingState) {
                    case LOADING_INITIAL:
                    case LOADING_MORE:
                        // Do your loading animation
                        activityDetailsScrollingBinding.contentDetails.swipeRefresh.setRefreshing(true);
                        break;

                    case LOADED:
                    case FINISHED:
                        //Reached end of Data set
                        // Stop Animation
                        activityDetailsScrollingBinding.contentDetails.swipeRefresh.setRefreshing(false);
                        break;

                    case ERROR:
                        retry();
                        break;
                }


            }


            @Override
            protected void onError(@NonNull DatabaseError databaseError) {
                super.onError(databaseError);
                activityDetailsScrollingBinding.contentDetails.swipeRefresh.setRefreshing(false);
                databaseError.toException().printStackTrace();
            }

            @NonNull
            @Override
            public StylesAdapter.StylesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutStylesListItemBinding layoutStylesListItemBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                                R.layout.layout_styles_list_item, viewGroup, false);

                return new StylesAdapter.StylesViewHolder(layoutStylesListItemBinding);
            }
        };


        recyclerView.setAdapter(adapter);

        activityDetailsScrollingBinding.contentDetails.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                adapter.refresh();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
