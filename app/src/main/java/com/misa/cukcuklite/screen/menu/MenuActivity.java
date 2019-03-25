package com.misa.cukcuklite.screen.menu;

import android.os.Bundle;

import com.misa.cukcuklite.R;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuActivity extends AppCompatActivity implements IMenuContract.IView {
    private static final String TAG = MenuActivity.class.getName();
    private IMenuContract.IPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mPresenter = new MenuPresenter(this);

        try {
            String[] images =getAssets().list("images");
            ArrayList<String> listImages = new ArrayList<String>(Arrays.asList(images));
//            InputStream inputstream=mContext.getAssets().open("images/"
//                    +listImages.get(position));
//            Drawable drawable = Drawable.createFromStream(inputstream, null);
//            imageView.setImageDrawable(drawable);

            for (String image: listImages){
                Log.d(TAG, "onCreate: "+image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
