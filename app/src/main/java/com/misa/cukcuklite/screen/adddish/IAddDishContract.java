package com.misa.cukcuklite.screen.adddish;

import android.content.Context;
import android.graphics.Bitmap;

interface IAddDishContract {
    interface IView {
    }

    interface IPresenter {
        Bitmap getBitmapFromAssets(Context context, String icon);
    }
}
