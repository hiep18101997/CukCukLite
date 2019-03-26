package com.misa.cukcuklite.screen.chooseunit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Unit;
import com.misa.cukcuklite.screen.dialoginput.DialogInputFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.AppConstant.ACTION_PICK_UNIT_DONE;
import static com.misa.cukcuklite.AppConstant.EXTRA_PICK_UNIT_DONE;

public class ChooseUnitActivity extends AppCompatActivity implements IChooseUnitContract.IView, ChooseUnitAdapter.OnClickUnit, View.OnClickListener {
    public static final String EXTRA_UNIT = "com.misa.cukcuklite.extra.unit";
    private static final String TAG = ChooseUnitActivity.class.getName();
    private IChooseUnitContract.IPresenter mPresenter;
    private ChooseUnitAdapter mAdapter;
    private String mStringUnit;
    private ArrayList<Unit> mUnits;

    public static Intent getIntent(Context context, String unit) {
        Intent intent = new Intent(context, ChooseUnitActivity.class);
        intent.putExtra(EXTRA_UNIT, unit);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_unit);
        mPresenter = new ChooseUnitPresenter(this, this);
        initComponent();
        initListener();
    }

    private void initListener() {
        findViewById(R.id.ivBack).setOnClickListener(this);
        findViewById(R.id.ivAdd).setOnClickListener(this);
        findViewById(R.id.tvDone).setOnClickListener(this);
    }

    private void initComponent() {
        mUnits = new ArrayList<>();
        mPresenter.getListUnit();
        mStringUnit = getIntent().getStringExtra(EXTRA_UNIT);
        mAdapter = new ChooseUnitAdapter(mUnits, this, this, mStringUnit);
        RecyclerView recyclerView = findViewById(R.id.rvUnit);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onClickEdit(Unit unit) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogInputFragment inputDialog = new DialogInputFragment(unit.getName());
        inputDialog.show(fragmentManager, "input_dialog");
    }

    @Override
    public void onClickRemove(Unit unit) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivAdd:
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogInputFragment inputDialog = new DialogInputFragment();
                inputDialog.show(fragmentManager, "input_dialog");
                break;
            case R.id.tvDone:
                String s = mAdapter.getUnit();
                Intent intent = new Intent(ACTION_PICK_UNIT_DONE);
                intent.putExtra(EXTRA_PICK_UNIT_DONE, s);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                finish();
                break;
        }
    }

    @Override
    public void onGetUnitSuccess(List<Unit> units) {
        mAdapter.addUnit(units);
    }

    @Override
    public void onInsertUnitSuccess(String unit) {
        Intent intent = new Intent(ACTION_PICK_UNIT_DONE);
        intent.putExtra(EXTRA_PICK_UNIT_DONE, unit);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }

    @Override
    public void onInsertUnitError() {
        Toast.makeText(this,getString(R.string.error_unit_exist), Toast.LENGTH_SHORT).show();
    }

    public void saveUnit(String text) {
        mPresenter.saveUnit(text);

    }

    public void onEmptyInput() {
        Toast.makeText(this, getString(R.string.error_input_unit_empty), Toast.LENGTH_SHORT).show();
    }
}
