package com.misa.cukcuklite.screen.dialogicon;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.misa.cukcuklite.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mIcons;
    private OnItemClick mOnItemClick;
    private LayoutInflater mInflater;

    public IconAdapter(Context context, List<String> icons, OnItemClick onItemClick) {
        mContext = context;
        mIcons = icons;
        mOnItemClick = onItemClick;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_icon, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindView(mContext, mIcons.get(i), viewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return mIcons != null ? mIcons.size() : 0;
    }

    private Bitmap getBitmapFromAssets(String fileName) {
        AssetManager assetManager = mContext.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open("images/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(istr);
    }

    interface OnItemClick {
        void onClick(String icon);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcon;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.ivIcon);
        }

        void bindView(Context context, final String icon, View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClick.onClick(icon);
                }
            });
            imgIcon.setImageBitmap(getBitmapFromAssets(icon));
        }
    }
}
