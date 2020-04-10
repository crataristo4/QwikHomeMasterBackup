package com.example.handyman.activities.home.bottomsheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.handyman.R;
import com.example.handyman.databinding.LayoutSendRequestBinding;
import com.example.handyman.utils.MyConstants;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class SendRequestBottomSheet extends BottomSheetDialogFragment {

    private LayoutSendRequestBinding layoutSendRequestBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutSendRequestBinding = DataBindingUtil.inflate(inflater, R.layout.layout_send_request, container, false);

        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return layoutSendRequestBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get data from bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            //pass value to items
            layoutSendRequestBinding.txtStyleName.setText(bundle.getString(MyConstants.STYLE));
            layoutSendRequestBinding.txtPrice.setText(bundle.getString(MyConstants.PRICE));


            String imageItem = bundle.getString(MyConstants.IMAGE_URL);
            Glide.with(Objects.requireNonNull(getActivity()))
                    .load(imageItem)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(layoutSendRequestBinding.imgItemPhoto);


        }

        sendItemRequest();
    }

    private void sendItemRequest() {
    }


}
