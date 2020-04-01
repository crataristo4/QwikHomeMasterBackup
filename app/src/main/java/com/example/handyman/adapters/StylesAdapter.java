package com.example.handyman.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handyman.R;
import com.example.handyman.databinding.LayoutStylesListItemBinding;
import com.example.handyman.models.ServicePerson;

import java.util.List;

public class StylesAdapter extends RecyclerView.Adapter<StylesAdapter.StylesViewHolder> {
    private Context context;
    private List<ServicePerson> servicePersonList;

    public StylesAdapter(Context context, List<ServicePerson> servicePersonList) {
        this.context = context;
        this.servicePersonList = servicePersonList;
    }

    @NonNull
    @Override
    public StylesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutStylesListItemBinding layoutStylesListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.layout_items_styles, viewGroup, false);

        return new StylesViewHolder(layoutStylesListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StylesViewHolder stylesViewHolder, int position) {

        ServicePerson servicePerson = servicePersonList.get(position);
        stylesViewHolder.layoutStylesListItemBinding.setItem(servicePerson);

    }

    @Override
    public int getItemCount() {
        return servicePersonList == null ? 0 : servicePersonList.size();
    }

    class StylesViewHolder extends RecyclerView.ViewHolder {

        LayoutStylesListItemBinding layoutStylesListItemBinding;

        StylesViewHolder(@NonNull LayoutStylesListItemBinding layoutStylesListItemBinding) {
            super(layoutStylesListItemBinding.getRoot());
            this.layoutStylesListItemBinding = layoutStylesListItemBinding;
        }
    }
}
