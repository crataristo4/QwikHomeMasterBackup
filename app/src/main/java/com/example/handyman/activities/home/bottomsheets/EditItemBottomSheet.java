package com.example.handyman.activities.home.bottomsheets;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.handyman.R;
import com.example.handyman.activities.home.MainActivity;
import com.example.handyman.databinding.LayoutEditItemBottomSheetBinding;
import com.example.handyman.utils.MyConstants;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditItemBottomSheet extends BottomSheetDialogFragment {
    private LayoutEditItemBottomSheetBinding layoutEditItemBottomSheetBinding;
    private TextInputLayout txtInputItem;
    private Bundle bundle;
    private Map<String, Object> updateItem = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layoutEditItemBottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.layout_edit_item_bottom_sheet, container, false);

        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return layoutEditItemBottomSheetBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtInputItem = layoutEditItemBottomSheetBinding.textInputLayout;

        bundle = getArguments();
        if (bundle != null) {

            if (Objects.equals(bundle.getString(MyConstants.NAME), MainActivity.name)) {

                layoutEditItemBottomSheetBinding.textInputLayout.setHint("Edit name");
                Objects.requireNonNull(layoutEditItemBottomSheetBinding.textInputLayout.getEditText()).setSingleLine(true);
                Objects.requireNonNull(layoutEditItemBottomSheetBinding.textInputLayout
                        .getEditText()).setText(bundle.getString(MyConstants.NAME));

            } else if (Objects.equals(bundle.getString(MyConstants.ABOUT), MainActivity.about)) {
                Objects.requireNonNull(layoutEditItemBottomSheetBinding.textInputLayout.getEditText()).setImeOptions(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                layoutEditItemBottomSheetBinding.textInputLayout.setHint("Edit about");
                Objects.requireNonNull(layoutEditItemBottomSheetBinding.textInputLayout
                        .getEditText()).setText(bundle.getString(MyConstants.ABOUT));
            }


        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            layoutEditItemBottomSheetBinding.txtCancel.setOnClickListener(v -> {

                dismiss();
            });

        layoutEditItemBottomSheetBinding.btnOk.setOnClickListener(this::onOkClicked);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onOkClicked(View view) {

        if (Objects.equals(bundle.getString(MyConstants.NAME), MainActivity.name)) {

            updateUserName();

        } else if (Objects.equals(bundle.getString(MyConstants.ABOUT), MainActivity.about)) {

            updateUserAbout();
        }


    }

    private void updateUserAbout() {

        String about = Objects.requireNonNull(layoutEditItemBottomSheetBinding.textInputLayout.getEditText()).getText().toString();

        if (about.trim().isEmpty()) {
            txtInputItem.setErrorEnabled(true);
            txtInputItem.setError("about field required");
        } else {
            txtInputItem.setErrorEnabled(false);
        }

        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
            if (!about.trim().isEmpty()) {

                updateItem.put("about", about);
                MainActivity.serviceAccountDbRef.updateChildren(updateItem);
                dismiss();
            }

        });
    }

    private void updateUserName() {
        String name = Objects.requireNonNull(layoutEditItemBottomSheetBinding.textInputLayout.getEditText()).getText().toString();

        if (name.trim().isEmpty()) {
            txtInputItem.setErrorEnabled(true);
            txtInputItem.setError("name field required");
        } else {
            txtInputItem.setErrorEnabled(false);
        }

        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
            if (!name.trim().isEmpty()) {


                updateItem.put("name", name);
                MainActivity.serviceAccountDbRef.updateChildren(updateItem);
                dismiss();
            }

        });


    }
}
