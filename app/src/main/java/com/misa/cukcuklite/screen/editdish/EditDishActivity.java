package com.misa.cukcuklite.screen.editdish;

import android.os.Bundle;

import com.misa.cukcuklite.R;

import androidx.appcompat.app.AppCompatActivity;

public class EditDishActivity extends AppCompatActivity implements IEditDishContract.IView {
    private static final String TAG = EditDishActivity.class.getName();
    private IEditDishContract.IPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);
        mPresenter = new EditDishPresenter(this);
    }
}
