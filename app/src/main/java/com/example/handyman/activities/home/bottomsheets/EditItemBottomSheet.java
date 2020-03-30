package com.example.handyman.activities.home.bottomsheets;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.handyman.R;
import com.example.handyman.databinding.LayoutEditItemBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EditItemBottomSheet extends BottomSheetDialogFragment {
    private LayoutEditItemBottomSheetBinding layoutEditItemBottomSheetBinding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layoutEditItemBottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.layout_edit_item_bottom_sheet, container, false);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return layoutEditItemBottomSheetBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layoutEditItemBottomSheetBinding.getRoot().setBackgroundColor(Color.TRANSPARENT);
    }
}
