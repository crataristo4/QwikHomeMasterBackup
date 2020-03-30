package com.example.handyman.activities.home.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.handyman.R;
import com.example.handyman.activities.home.MainActivity;
import com.example.handyman.activities.home.bottomsheets.EditItemBottomSheet;
import com.example.handyman.databinding.FragmentEditProfileBinding;
import com.example.handyman.utils.MyConstants;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    FragmentEditProfileBinding fragmentEditProfileBinding;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentEditProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false);
        return fragmentEditProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentEditProfileBinding
                .fabUploadPhoto.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fadein));

        MainActivity.retrieveUserDetails(fragmentEditProfileBinding.txtUserName,
                fragmentEditProfileBinding.txtAboutUser, fragmentEditProfileBinding.imgUploadPhoto);

        fragmentEditProfileBinding.nameLayout.setOnClickListener(//open bottom sheet to edit name
                this::onClick);

        fragmentEditProfileBinding.aboutLayout.setOnClickListener(
                //open bottom sheet to edit about
                this::onClick);

    }

    public void onClick(View v) {
        Bundle bundle = new Bundle();
        EditItemBottomSheet editItemBottomSheet = new EditItemBottomSheet();
       /* bundle.putString(MyConstants.NAME, String.valueOf(fragmentEditProfileBinding.txtUserName));

        EditItemBottomSheet editItemBottomSheet = new EditItemBottomSheet();
        editItemBottomSheet.setArguments(bundle);
        editItemBottomSheet.show(getFragmentManager(),MyConstants.NAME);
*/
        if (v.getId() == R.id.nameLayout) {
            bundle.putString(MyConstants.NAME, String.valueOf(fragmentEditProfileBinding.txtUserName));
            editItemBottomSheet.setArguments(bundle);
            editItemBottomSheet.show(getFragmentManager(), MyConstants.NAME);

        } else if (v.getId() == R.id.aboutLayout) {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
            Objects.requireNonNull(bottomSheetDialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            bottomSheetDialog.setContentView(R.layout.layout_edit_item_bottom_sheet);
            bottomSheetDialog.getBehavior().setHideable(true);
            bottomSheetDialog.show();

            /*bundle.putString(MyConstants.ABOUT, String.valueOf(fragmentEditProfileBinding.txtAboutUser));
            editItemBottomSheet.setArguments(bundle);
            editItemBottomSheet.show(getFragmentManager(),MyConstants.ABOUT);*/
        }
    }
}
