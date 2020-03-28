package com.example.handyman.activities.welcome;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.handyman.R;
import com.example.handyman.activities.ItemViewClickEvents;
import com.example.handyman.adapters.SlidePagerAdapter;
import com.example.handyman.databinding.ActivityWelcomeBinding;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding activityWelcomeBinding;
    ItemViewClickEvents itemViewClickEvents;
   private Runnable runnable;
    private Handler handler = new Handler(Looper.getMainLooper());
    Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityWelcomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        itemViewClickEvents = new ItemViewClickEvents(this);
        activityWelcomeBinding.setOnItemClick(itemViewClickEvents);

        initViews();

    }


    private void initViews() {


        SlidePagerAdapter slidePagerAdapter = new SlidePagerAdapter(this);

        activityWelcomeBinding.Viewpager.setAdapter(slidePagerAdapter);
        activityWelcomeBinding.slideDots.setViewPager(activityWelcomeBinding.Viewpager);
        activityWelcomeBinding.slideDots.setBackgroundColor(Color.BLACK);


            runnable = () -> {

                int count = activityWelcomeBinding.Viewpager.getCurrentItem();
                if (count == slidePagerAdapter.slideDescriptions.length - 1) {
                    count = 0;
                    activityWelcomeBinding.Viewpager.setCurrentItem(count, true);
                } else {
                    count++;
                    activityWelcomeBinding.Viewpager.setCurrentItem(count, true);

                }

            };


            timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(runnable);
                    }
                }, 2000, 2000);


    }


}
