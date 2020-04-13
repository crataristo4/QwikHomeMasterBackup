package com.example.handyman.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handyman.R;
import com.example.handyman.databinding.LayoutUserRequestSentBinding;
import com.example.handyman.models.RequestModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RequestAdapter extends FirebaseRecyclerAdapter<RequestModel, RequestAdapter.RequestViewHolder> {


    public RequestAdapter(@NonNull FirebaseRecyclerOptions<RequestModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RequestViewHolder requestViewHolder, int i, @NonNull RequestModel requestModel) {

        requestViewHolder.layoutUserRequestSentBinding.setRequestItems(requestModel);
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutUserRequestSentBinding layoutUserRequestSentBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.layout_user_request_sent, viewGroup, false);

        return new RequestViewHolder(layoutUserRequestSentBinding);
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        LayoutUserRequestSentBinding layoutUserRequestSentBinding;

        public RequestViewHolder(@NonNull LayoutUserRequestSentBinding layoutUserRequestSentBinding) {
            super(layoutUserRequestSentBinding.getRoot());
            this.layoutUserRequestSentBinding = layoutUserRequestSentBinding;
        }
    }
}
