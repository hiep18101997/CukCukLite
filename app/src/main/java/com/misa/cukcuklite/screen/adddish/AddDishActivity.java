package com.misa.cukcuklite.screen.adddish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maltaisn.calcdialog.CalcDialog;
import com.maltaisn.calcdialog.CalcNumpadLayout;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.chooseunit.ChooseUnitActivity;
import com.misa.cukcuklite.screen.dialogicon.IconFragment;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.misa.cukcuklite.AppConstant.ACTION_PICK_UNIT_DONE;
import static com.misa.cukcuklite.AppConstant.EXTRA_PICK_UNIT_DONE;

public class AddDishActivity extends AppCompatActivity implements IAddDishContract.IView, View.OnClickListener, CalcDialog.CalcDialogCallback {
    private static final String TAG = AddDishActivity.class.getName();
    private static final int COLOR_DEF = -14235942;
    private RelativeLayout rlLayoutColor, rlLayoutIcon;
    private ImageView ivProductIcon;
    private TextView tvCost, tvUnit, tvDone;
    private IAddDishContract.IPresenter mPresenter;
    private int selectedColor;
    private BroadcastReceiver mReceiver;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AddDishActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        mPresenter = new AddDishPresenter(this, this);
        initComps();
        initListener();
        initBroadcastReceiver();
    }

    private void initBroadcastReceiver() {
        IntentFilter filter = new IntentFilter(ACTION_PICK_UNIT_DONE);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String unit = intent.getStringExtra(EXTRA_PICK_UNIT_DONE);
                tvUnit.setText(unit);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter);
    }

    private void initListener() {
        rlLayoutColor.setOnClickListener(this);
        rlLayoutIcon.setOnClickListener(this);
        tvCost.setOnClickListener(this);
        tvUnit.setOnClickListener(this);
        findViewById(R.id.ivBack).setOnClickListener(this);
        findViewById(R.id.tvDone).setOnClickListener(this);

    }

    private void initComps() {
        tvCost = findViewById(R.id.tvCost);
        tvUnit = findViewById(R.id.tvUnit);
        ivProductIcon = findViewById(R.id.ivProductIcon2);
        rlLayoutColor = findViewById(R.id.rlIconContainer1);
        rlLayoutIcon = findViewById(R.id.rlIconContainer2);
    }

    void showDialogPickColor() {
        SpectrumDialog dialog = new SpectrumDialog.Builder(this)
                .setTitle("Chọn màu")
                .setSelectedColor(selectedColor != 0 ? selectedColor : COLOR_DEF)
                .setPositiveButtonText(getString(R.string.ok))
                .setNegativeButtonText(getString(R.string.cancel))
                .setColors(R.array.arr_colors)
                .setDismissOnColorSelected(false)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        if (positiveResult) {
                            Drawable drawableBg = getResources().getDrawable(R.drawable.bg_circle);
                            drawableBg.setColorFilter(color, PorterDuff.Mode.SRC);
                            rlLayoutColor.setBackground(drawableBg);
                            rlLayoutIcon.setBackground(drawableBg);
                            selectedColor = color;
                        } else {

                        }
                    }
                }).build();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "fragment_picker");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlIconContainer1:
                showDialogPickColor();
                break;
            case R.id.rlIconContainer2:
                showDialogPickIcon();
                break;
            case R.id.tvCost:
                showDialogCalculator();
                break;
            case R.id.tvUnit:
                startActivity(ChooseUnitActivity.getIntent(this, tvUnit.getText().toString()));
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.tvDone:

                break;
        }
    }

    private void showDialogCalculator() {
        CalcDialog calcDialog = new CalcDialog();
        calcDialog.getSettings()
                .setRequestCode(0)
                .setInitialValue(null)
                .setNumberFormat(NumberFormat.getInstance())
                .setNumpadLayout(CalcNumpadLayout.CALCULATOR)
                .setExpressionShown(false)
                .setExpressionEditable(false)
                .setZeroShownWhenNoValue(true)
                .setAnswerBtnShown(false)
                .setSignBtnShown(true)
                .setShouldEvaluateOnOperation(true)
                .setOrderOfOperationsApplied(true);
        FragmentManager fm = getSupportFragmentManager();
        calcDialog.setCancelable(false);
        calcDialog.show(fm, "fragment_cal");
    }

    private void showDialogPickIcon() {
        FragmentManager fm = getSupportFragmentManager();
        IconFragment tv = new IconFragment();
        tv.show(fm, "icon");
    }

    public void setIcon(String icon) {
        Glide.with(this).load(mPresenter.getBitmapFromAssets(this, icon)).into(ivProductIcon);
    }


    @Override
    public void onValueEntered(int requestCode, @Nullable BigDecimal value) {
        tvCost.setText(NumberFormat.getNumberInstance(Locale.US).format(value));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }
}
