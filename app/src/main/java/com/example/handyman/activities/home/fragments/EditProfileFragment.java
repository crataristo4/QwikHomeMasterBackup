package com.example.handyman.activities.home.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.handyman.R;
import com.example.handyman.activities.home.MainActivity;
import com.example.handyman.activities.home.bottomsheets.EditItemBottomSheet;
import com.example.handyman.activities.home.bottomsheets.VerifyPhoneBottomSheet;
import com.example.handyman.databinding.FragmentEditProfileBinding;
import com.example.handyman.utils.DisplayViewUI;
import com.example.handyman.utils.MyConstants;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding fragmentEditProfileBinding;
    private ProfilePhotoEditFragment profilePhotoEditFragment = new ProfilePhotoEditFragment();
    private Uri uri;
    private long mLastClickTime = 0;
    private OnFragmentInteractionListener mListener;


    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle("Profile");
        // Inflate the layout for this fragment
        fragmentEditProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false);
        return fragmentEditProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentEditProfileBinding
                .fabUploadPhoto.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fadein));
        fragmentEditProfileBinding
                .imgUploadPhoto.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_transition_animation));

        fragmentEditProfileBinding.imgUploadPhoto.setOnClickListener(v -> {
            FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.scale_out)
                    .replace(R.id.containerSettings, profilePhotoEditFragment)
                    .addToBackStack(null)
                    .commit();

            // getActivity().setTitle("Profile photo");



        });

        fragmentEditProfileBinding.fabUploadPhoto.setOnClickListener(v -> openGallery());

        MainActivity.retrieveSingleUserDetails(fragmentEditProfileBinding.txtUserName,
                fragmentEditProfileBinding.txtAboutUser, fragmentEditProfileBinding.imgUploadPhoto);

        //  fragmentEditProfileBinding.nameLayout.setEnabled(true);
        fragmentEditProfileBinding.nameLayout.setOnClickListener(//open bottom sheet to edit name
                this::onClick);

        fragmentEditProfileBinding.aboutLayout.setOnClickListener(
                //open bottom sheet to edit about
                this::onClick);


        fragmentEditProfileBinding.editPhoneLayout.setOnClickListener(this::onClick);

    }

    private void openGallery() {
        CropImage.activity()
                .setAspectRatio(16, 16)
                .start(Objects.requireNonNull(getActivity()));
    }

    public void onClick(View v) {
        Bundle bundle = new Bundle();
        EditItemBottomSheet editItemBottomSheet = new EditItemBottomSheet();
        VerifyPhoneBottomSheet verifyPhoneBottomSheet = new VerifyPhoneBottomSheet();

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();


        if (v.getId() == R.id.nameLayout) {
            if (fragmentEditProfileBinding.nameLayout.isEnabled()) {
                // fragmentEditProfileBinding.nameLayout.setEnabled(false);
                String getName = String.valueOf(fragmentEditProfileBinding.txtUserName.getText());
                bundle.putString(MyConstants.NAME, getName);
                editItemBottomSheet.setArguments(bundle);
                editItemBottomSheet.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), MyConstants.NAME);

            }


        } else if (v.getId() == R.id.aboutLayout) {

            String getAbout = String.valueOf(fragmentEditProfileBinding.txtAboutUser.getText());
            bundle.putString(MyConstants.ABOUT, getAbout);
            editItemBottomSheet.setArguments(bundle);
            editItemBottomSheet.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), MyConstants.ABOUT);
        } else if (v.getId() == R.id.editPhoneLayout) {
            //open phone number verification
            //Objects.requireNonNull(getActivity()).startActivity(new Intent(getContext(), AddPhoneNumberActivity.class));

            verifyPhoneBottomSheet.setCancelable(false);
            verifyPhoneBottomSheet.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), MyConstants.PHONE_NUMBER);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            getActivity();
            if (resultCode == Activity.RESULT_OK) {
                assert result != null;
                uri = result.getUri();

                /*Glide.with(getActivity())
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(profileImage);*/

                // uploadFile();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                // progressDialog.dismiss();
                assert result != null;
                String error = result.getError().getMessage();
                DisplayViewUI.displayToast(getActivity(), error);
            }
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
