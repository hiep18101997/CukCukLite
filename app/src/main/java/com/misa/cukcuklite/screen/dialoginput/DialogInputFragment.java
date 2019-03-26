package com.misa.cukcuklite.screen.dialoginput;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.chooseunit.ChooseUnitActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogInputFragment extends DialogFragment implements View.OnClickListener {
    private TextView tvSave, tvCancel;
    private EditText edtIput;
    private String input;

    public DialogInputFragment() {
    }

    @SuppressLint("ValidFragment")
    public DialogInputFragment(String string) {
        input = string;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_input_unit, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        initComponent(rootView);
        initListener();
        return rootView;
    }

    private void initListener() {
        tvSave.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void initComponent(View rootView) {
        tvSave = rootView.findViewById(R.id.tvSave);
        tvCancel = rootView.findViewById(R.id.tvCancel);
        edtIput = rootView.findViewById(R.id.edtInput);
        if (!TextUtils.isEmpty(input)) {
            edtIput.setText(input);
            edtIput.setSelection(edtIput.getText().length());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSave:
                if (!validateInput()) {
                    ((ChooseUnitActivity) getActivity()).onEmptyInput();
                    return;
                }
                ((ChooseUnitActivity) getActivity()).saveUnit(edtIput.getText().toString());
                dismiss();
                break;
            case R.id.tvCancel:
                dismiss();
                break;
        }
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(edtIput.getText())) {
            return false;
        }
        return true;
    }
}
