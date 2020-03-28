package com.example.handyman.activities.home.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.handyman.R;
import com.example.handyman.databinding.ActivityJobTypesBinding;
import com.example.handyman.utils.DisplayViewUI;
import com.example.handyman.utils.MyConstants;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class JobTypesActivity extends AppCompatActivity {

    private ActivityJobTypesBinding activityJobTypesBinding;
    private TextInputLayout txtStyleName,txtPrice;
    private CircleImageView styleItemPhoto;
    private DatabaseReference serviceAccountDbRef, serviceTypeDbRef;
    private StorageReference mStorageReference;
    private Uri uri;
    private String uid,style;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState != null){
            Objects.requireNonNull(txtStyleName.getEditText()).setText(savedInstanceState.getString(MyConstants.STYLE));
            Objects.requireNonNull(txtPrice.getEditText()).setText(savedInstanceState.getString(MyConstants.PRICE));
            Glide.with(JobTypesActivity.this)
                    .load((Uri) savedInstanceState.getParcelable(MyConstants.IMAGE_URL))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(styleItemPhoto);

        }

        super.onCreate(savedInstanceState);

        activityJobTypesBinding = DataBindingUtil.setContentView(this,R.layout.activity_job_types);
        intViews();

    }

    private void intViews() {
        activityJobTypesBinding.txtDes.startAnimation(AnimationUtils.loadAnimation(this,R.anim.blinking_text));

        txtPrice = activityJobTypesBinding.textInputLayoutPrice;
        txtStyleName = activityJobTypesBinding.txtInputLayoutStyle;
        styleItemPhoto = activityJobTypesBinding.imgStylePhoto;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            return;
        }
        uid = mFirebaseUser.getUid();

        activityJobTypesBinding.fabAddIcon.setOnClickListener(v -> openGallery());

        activityJobTypesBinding.imgStylePhoto.setOnClickListener(v -> openGallery());

        activityJobTypesBinding.btnAdd.setOnClickListener(this::validateInputs);

    }

    private void openGallery() {
        CropImage.activity()
                .setAspectRatio(16, 16)
                .start(JobTypesActivity.this);
    }

    private void validateInputs(View v) {
        style = Objects.requireNonNull(txtStyleName.getEditText()).getText().toString();
        price = Objects.requireNonNull(Double.parseDouble(txtPrice.getEditText().getText().toString()));

        if (style.trim().isEmpty()) {
            txtStyleName.setErrorEnabled(true);
            txtStyleName.setError("must include a style");
        } else {
            txtStyleName.setErrorEnabled(false);
        }

        if (txtPrice.getEditText().getText().toString().trim().isEmpty()) {
            txtPrice.setErrorEnabled(true);
            txtPrice.setError("must include a price");
        } else {
            txtPrice.setErrorEnabled(false);
        }
        if (price < 0){
            txtPrice.setErrorEnabled(true);
            txtPrice.setError("invalid price");
        } else {
            txtPrice.setErrorEnabled(false);
        }

        if (uri == null) {
            DisplayViewUI.displayToast(this, "Please select a photo to upload");

        }

        if (!style.trim().isEmpty() && uri != null && price > 0) {

            uploadFile();
        }
    }

    private void uploadFile() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                assert result != null;
                uri = result.getUri();

                Glide.with(JobTypesActivity.this)
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(styleItemPhoto);

                // uploadFile();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                // progressDialog.dismiss();
                assert result != null;
                String error = result.getError().getMessage();
                DisplayViewUI.displayToast(this, error);
            }
        }

    }

    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(MyConstants.STYLE,style);
        outState.putString(MyConstants.PRICE,String.valueOf(price));
       outState.putParcelable(MyConstants.IMAGE_URL,uri);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Objects.requireNonNull(txtStyleName.getEditText()).setText(savedInstanceState.getString(MyConstants.STYLE));
        Objects.requireNonNull(txtPrice.getEditText()).setText(savedInstanceState.getString(MyConstants.PRICE));
        Glide.with(JobTypesActivity.this)
                .load((Uri) savedInstanceState.getParcelable(MyConstants.IMAGE_URL))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(styleItemPhoto);

    }
}
