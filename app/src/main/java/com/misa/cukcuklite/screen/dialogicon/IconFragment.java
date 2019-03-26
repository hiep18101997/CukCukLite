package com.misa.cukcuklite.screen.dialogicon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.adddish.AddDishActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IconFragment extends DialogFragment implements IconAdapter.OnItemClick, View.OnClickListener {
    private RecyclerView rvIcon;
    private IconAdapter adapter;
    private TextView tvCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_icon_fragment, container);
        getDialog().setTitle("Chọn icon");
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        tvCancel = rootView.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        try {
            String[] images = getContext().getAssets().list("images");
            List<String> listImages = new ArrayList<String>(Arrays.asList(images));
            rvIcon = rootView.findViewById(R.id.rvIcon);
            adapter = new IconAdapter(getContext(), listImages, this);
            rvIcon.setAdapter(adapter);
            rvIcon.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onClick(String icon) {
        ((AddDishActivity) getActivity()).setIcon(icon);
        dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                dismiss();
                break;
        }
    }
}
