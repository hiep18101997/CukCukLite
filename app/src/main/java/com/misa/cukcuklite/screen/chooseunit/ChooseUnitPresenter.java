package com.misa.cukcuklite.screen.chooseunit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.db.model.Unit;

import java.util.List;

public class ChooseUnitPresenter implements IChooseUnitContract.IPresenter {
    private static final String TAG = ChooseUnitPresenter.class.getName();
    private Context mContext;
    private IChooseUnitContract.IView mView;

    public ChooseUnitPresenter(Context context, IChooseUnitContract.IView view) {
        mContext = context;
        mView = view;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getListUnit() {
        new AsyncTask<Void, Void, List<Unit>>() {
            @Override
            protected List<Unit> doInBackground(Void... voids) {
                return DatabaseClient.getInstance(mContext).getAppDatabase().mUnitDAO().getAllUnit();
            }

            @Override
            protected void onPostExecute(List<Unit> units) {
                super.onPostExecute(units);
                mView.onGetUnitSuccess(units);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void saveUnit(final String text) {
        new AsyncTask<Void, Boolean, Void>() {
            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                Unit unit = new Unit(text);
                Unit existUnit = DatabaseClient.getInstance(mContext).getAppDatabase().mUnitDAO().getUnitByName(text);
                if (existUnit == null) {
                    DatabaseClient.getInstance(mContext).getAppDatabase().mUnitDAO().insertUnit(unit);
                    publishProgress(true);
                } else {
                    publishProgress(false);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Boolean... values) {
                super.onProgressUpdate(values);
                if (values[0]) {
                    mView.onInsertUnitSuccess(text);
                } else {
                    mView.onInsertUnitError();
                }
            }
        }.execute();
    }
}
