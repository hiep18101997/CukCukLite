package com.misa.cukcuklite.screen.chooseunit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Unit;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseUnitAdapter extends RecyclerView.Adapter<ChooseUnitAdapter.ViewHolder> {
    private List<Unit> mUnits;
    private Context mContext;
    private LayoutInflater mInflater;
    private int indexSelect;
    private OnClickUnit mOnClick;

    public ChooseUnitAdapter(List<Unit> units, Context context, OnClickUnit onClick, String unit) {
        mUnits = units;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mOnClick = onClick;
        setIndexUnit(unit);

    }

    private void setIndexUnit(String unit) {
        if (unit == null) {
            indexSelect = 0;
            return;
        } else {
            for (int i = 0; i < mUnits.size(); i++) {
                if (mUnits.get(i).getName().equals(unit)) {
                    indexSelect = i;
                    return;
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_unit, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bindView(mUnits.get(position), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indexSelect = position;
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showPopUp(view);
                return false;
            }
        });
    }

    private void showPopUp(View view) {
        MenuBuilder menuBuilder = new MenuBuilder(mContext);
        MenuInflater inflater = new MenuInflater(mContext);
        inflater.inflate(R.menu.menu_popup_unit, menuBuilder);
        MenuPopupHelper optionsMenu = new MenuPopupHelper(mContext, menuBuilder, view);
        optionsMenu.setForceShowIcon(true);
        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {

                return true;
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {

            }
        });
        optionsMenu.show();

    }

    @Override
    public int getItemCount() {
        return mUnits != null ? mUnits.size() : 0;
    }

    public String getUnit() {
        return mUnits.get(indexSelect).getName();
    }

    public void addUnit(List<Unit> units) {
        if (units == null) {
            return;
        }
        mUnits.clear();
        mUnits.addAll(units);
        notifyDataSetChanged();
    }
    interface OnClickUnit {
        void onClickEdit(Unit unit);
        void onClickRemove(Unit unit);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivCheck, ivEdit;
        public TextView tvUnit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCheck = itemView.findViewById(R.id.ivCheck);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }

        public void bindView(final Unit unit, int position) {
            tvUnit.setText(unit.getName());
            if (position == indexSelect) {
                ivCheck.setVisibility(View.VISIBLE);
            } else {
                ivCheck.setVisibility(View.INVISIBLE);
            }
            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnClick.onClickEdit(unit);
                }
            });
        }
    }
}
