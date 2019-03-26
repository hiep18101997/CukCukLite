package com.misa.cukcuklite.screen.adddish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.db.model.Unit;

import java.io.IOException;
import java.io.InputStream;

public class AddDishPresenter implements IAddDishContract.IPresenter {
    private static final String TAG = AddDishPresenter.class.getName();
    private Context mContext;
    private IAddDishContract.IView mView;

    public AddDishPresenter(Context context, IAddDishContract.IView view) {
        mView = view;
        mContext = context;
    }

    @Override
    public Bitmap getBitmapFromAssets(Context context, String icon) {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open("images/" + icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(istr);
    }


}
