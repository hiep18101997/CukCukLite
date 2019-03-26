package com.misa.cukcuklite.screen.editdish;

public class EditDishPresenter implements IEditDishContract.IPresenter {
    private static final String TAG = EditDishPresenter.class.getName();

    private IEditDishContract.IView mView;

    public EditDishPresenter(IEditDishContract.IView view) {
        mView = view;
    }
}
