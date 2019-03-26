package com.misa.cukcuklite.screen.chooseunit;

import com.misa.cukcuklite.data.db.model.Unit;

import java.util.List;

interface IChooseUnitContract {
    interface IView {
        void onGetUnitSuccess(List<Unit> units);

        void onInsertUnitSuccess(String unit);

        void onInsertUnitError();
    }

    interface IPresenter {
        void getListUnit();

        void saveUnit(String text);
    }
}
