package com.example.handyman.activities.home.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.handyman.R;
import com.example.handyman.activities.home.MainActivity;
import com.example.handyman.databinding.FragmentAccountBinding;
import com.example.handyman.utils.MyConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private static final String TAG = "AccountFragment";
    String uid, accountType, name, about, imageUrl;
    private FirebaseAuth mAuth;
    private DatabaseReference serviceAccountDbRef, serviceTypeDbRef;
    private FragmentAccountBinding accountBinding;
    private TextView txtName, txtServiceType, txtAbout;
    private CircleImageView mPhoto;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accountBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);

        if (savedInstanceState != null) {
            accountBinding.txtAboutPerson.setText(savedInstanceState.getString(MyConstants.ABOUT));
            accountBinding.txtName.setText(savedInstanceState.getString(MyConstants.NAME));
            accountBinding.txtAccountType.setText(savedInstanceState.getString(MyConstants.ACCOUNT_TYPE));

            Glide.with(getActivity())
                    .load(savedInstanceState.getString(MyConstants.IMAGE_URL))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(accountBinding.imgProfilePhoto);
        }
        return accountBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            return;
        }
        uid = mFirebaseUser.getUid();

        accountType = MainActivity.serviceType;


        serviceAccountDbRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Services").child(accountType).child(uid);

        txtAbout = accountBinding.txtAboutPerson;
        txtName = accountBinding.txtName;
        txtServiceType = accountBinding.txtAccountType;
        mPhoto = accountBinding.imgProfilePhoto;

        txtAbout.setText(MainActivity.about);
        txtName.setText(MainActivity.name);
        txtServiceType.setText(MainActivity.serviceType);

        Glide.with(getActivity())
                .load(savedInstanceState.getString(MainActivity.imageUrl))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(accountBinding.imgProfilePhoto);


/*
        serviceAccountDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                accountType = (String) dataSnapshot.child("accountType").getValue();
                name = (String) dataSnapshot.child("name").getValue();
                imageUrl = (String) dataSnapshot.child("image").getValue();
                about = (String) dataSnapshot.child("about").getValue();

                txtAbout.setText(about);
                txtName.setText(name);
                txtServiceType.setText(accountType);

                Glide.with(getActivity())
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mPhoto);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                DisplayViewUI.displayToast(getActivity(), databaseError.getMessage());

            }
        });
*/

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MyConstants.ACCOUNT_TYPE, MainActivity.serviceType);
        outState.putString(MyConstants.NAME, MainActivity.name);
        outState.putString(MyConstants.ABOUT, MainActivity.about);
        outState.putString(MyConstants.IMAGE_URL, MainActivity.imageUrl);
    }


}
