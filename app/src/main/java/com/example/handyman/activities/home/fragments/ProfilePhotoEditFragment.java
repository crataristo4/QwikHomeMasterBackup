package com.example.handyman.activities.home.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.handyman.R;
import com.example.handyman.activities.home.MainActivity;
import com.example.handyman.databinding.FragmentProfilePhotoEditBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilePhotoEditFragment extends Fragment {

    FragmentProfilePhotoEditBinding fragmentProfilePhotoEditBinding;
    private OnFragmentInteractionListener mListener;


    public ProfilePhotoEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle("Profile photo");
        // Inflate the layout for this fragment
        fragmentProfilePhotoEditBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_photo_edit, container, false);
        return fragmentProfilePhotoEditBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentProfilePhotoEditBinding.imgEditPhoto.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.scale_in));

        MainActivity.retrieveSingleUserDetails(fragmentProfilePhotoEditBinding.imgEditPhoto);


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
