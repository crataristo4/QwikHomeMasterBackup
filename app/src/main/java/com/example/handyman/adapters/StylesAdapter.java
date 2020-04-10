package com.example.handyman.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

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
import com.example.handyman.models.StylesItemModel;
import com.example.handyman.utils.DisplayViewUI;
import com.shreyaspatil.firebase.recyclerpagination.DatabasePagingOptions;
import com.shreyaspatil.firebase.recyclerpagination.FirebaseRecyclerPagingAdapter;
import com.shreyaspatil.firebase.recyclerpagination.LoadingState;


public class StylesAdapter extends FirebaseRecyclerPagingAdapter<StylesItemModel, StylesAdapter.StylesViewHolder> {
    private static onItemClickListener onItemClickListener;

    public StylesAdapter(@NonNull DatabasePagingOptions<StylesItemModel> options) {
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
                                    int i, @NonNull StylesItemModel itemModel) {

        stylesViewHolder.layoutStylesListItemBinding.setItem(itemModel);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(DisplayViewUI.getRandomDrawableColor());
        requestOptions.error(DisplayViewUI.getRandomDrawableColor());
        requestOptions.centerCrop();

        Glide.with(stylesViewHolder.layoutStylesListItemBinding.getRoot().getContext())
                .load(itemModel.itemImage)
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

        stylesViewHolder.layoutStylesListItemBinding.mCardViewItem.startAnimation(AnimationUtils.loadAnimation(stylesViewHolder.layoutStylesListItemBinding.getRoot().getContext()
                , R.anim.fade_scale_animation));


    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState loadingState) {
        switch (loadingState) {
            case LOADING_INITIAL:
            case LOADING_MORE:

                break;
        }

    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        StylesAdapter.onItemClickListener = onItemClickListener;

    }

    public interface onItemClickListener {
        void onClick(View view, int position);
    }

    public static class StylesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LayoutStylesListItemBinding layoutStylesListItemBinding;

        public StylesViewHolder(@NonNull LayoutStylesListItemBinding layoutStylesListItemBinding) {
            super(layoutStylesListItemBinding.getRoot());
            this.layoutStylesListItemBinding = layoutStylesListItemBinding;
            layoutStylesListItemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onClick(layoutStylesListItemBinding.getRoot(), getAdapterPosition());

        }
    }
}
