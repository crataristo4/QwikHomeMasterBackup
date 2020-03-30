package com.example.handyman.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.handyman.R;
import com.example.handyman.databinding.LayoutListItemsBinding;
import com.example.handyman.models.SinglePerson;
import com.example.handyman.utils.DisplayViewUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AllBarbersAdapter extends FirebaseRecyclerAdapter<SinglePerson,
        AllBarbersAdapter.AllBarbersViewHolder> {
    private Context mContext;

    public AllBarbersAdapter(@NonNull FirebaseRecyclerOptions<SinglePerson> options, Context context) {
        super(options);
        mContext = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull AllBarbersViewHolder allBarbersViewHolder,
                                    int i, @NonNull SinglePerson singlePerson) {

        allBarbersViewHolder.cardView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));
        allBarbersViewHolder.listItemsServicesBinding.setServiceType(singlePerson);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(DisplayViewUI.getRandomDrawableColor());
        requestOptions.error(DisplayViewUI.getRandomDrawableColor());
        requestOptions.centerCrop();

        Glide.with(allBarbersViewHolder.itemView.getContext())
                .load(singlePerson.image)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        allBarbersViewHolder.listItemsServicesBinding.pbLoading.setVisibility(View.VISIBLE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        allBarbersViewHolder.listItemsServicesBinding.pbLoading.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(allBarbersViewHolder.listItemsServicesBinding.imgUserPhoto);


    }

    @NonNull
    @Override
    public AllBarbersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutListItemsBinding listItemsServicesBinding = DataBindingUtil.inflate
                (LayoutInflater.from(viewGroup.getContext()),
                        R.layout.layout_list_items, viewGroup, false);

        return new AllBarbersViewHolder(listItemsServicesBinding);
    }

    static class AllBarbersViewHolder extends RecyclerView.ViewHolder {

        LayoutListItemsBinding listItemsServicesBinding;
        CardView cardView;

        AllBarbersViewHolder(@NonNull LayoutListItemsBinding listItemsServicesBinding) {
            super(listItemsServicesBinding.getRoot());

            this.listItemsServicesBinding = listItemsServicesBinding;
            cardView = listItemsServicesBinding.mMaterialCard;
        }
    }
}
