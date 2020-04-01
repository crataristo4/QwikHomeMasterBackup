package com.example.handyman.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.handyman.databinding.LayoutStylesListItemBinding;
import com.example.handyman.models.ServicePerson;
import com.example.handyman.utils.DisplayViewUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class StylesAdapter extends FirebaseRecyclerAdapter<ServicePerson, StylesAdapter.StylesViewHolder> {

    public StylesAdapter(@NonNull FirebaseRecyclerOptions<ServicePerson> options) {
        super(options);
    }


    @NonNull
    @Override
    public StylesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutStylesListItemBinding layoutStylesListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.layout_styles_list_item, viewGroup, false);

        return new StylesViewHolder(layoutStylesListItemBinding);
    }


    @Override
    protected void onBindViewHolder(@NonNull StylesViewHolder stylesViewHolder,
                                    int i, @NonNull ServicePerson servicePerson) {

        stylesViewHolder.layoutStylesListItemBinding.setItem(servicePerson);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(DisplayViewUI.getRandomDrawableColor());
        requestOptions.error(DisplayViewUI.getRandomDrawableColor());
        requestOptions.centerCrop();

        Glide.with(stylesViewHolder.layoutStylesListItemBinding.getRoot().getContext())
                .load(servicePerson.image)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        stylesViewHolder.layoutStylesListItemBinding.progressBar.setVisibility(View.VISIBLE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        stylesViewHolder.layoutStylesListItemBinding.progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(stylesViewHolder.layoutStylesListItemBinding.imgStylePhoto);

    }


    class StylesViewHolder extends RecyclerView.ViewHolder {

        LayoutStylesListItemBinding layoutStylesListItemBinding;

        StylesViewHolder(@NonNull LayoutStylesListItemBinding layoutStylesListItemBinding) {
            super(layoutStylesListItemBinding.getRoot());
            this.layoutStylesListItemBinding = layoutStylesListItemBinding;
        }
    }
}
