package com.misa.cukcuklite.screen.menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.screen.adddish.AddDishActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuActivity extends AppCompatActivity implements IMenuContract.IView, MenuAdapter.OnItemClick {
    private static final String TAG = MenuActivity.class.getName();
    private IMenuContract.IPresenter mPresenter;
    private MenuAdapter mAdapter;
    private List<Dish> mDishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mPresenter = new MenuPresenter(this);
        mPresenter.getAllDish();
        setupToolbar();
        initComponent();
    }

    private void initComponent() {
        try {
            mDishes = new ArrayList<>();
            String[] images = getAssets().list("images");
            ArrayList<String> listImages = new ArrayList<String>(Arrays.asList(images));
            for (String path : listImages) {
                Dish dish = new Dish.Builder().setName("Cà phê G7").setCost(10000).setSell(false).setColor("#FFB300").setIcon(path).setUnit("Cái").build();
                mDishes.add(dish);
            }
            mPresenter = new MenuPresenter(this);
            mPresenter.getAllDish();
            RecyclerView recyclerView = findViewById(R.id.rvMenu);
            mAdapter = new MenuAdapter(this, mDishes, this);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public void onClick(Dish dish) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(AddDishActivity.getIntent(this));
                break;
            default:
                break;
        }
        return true;
    }

}
