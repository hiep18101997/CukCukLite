package com.misa.cukcuklite.screen.menu;

public class MenuPresenter implements IMenuContract.IPresenter {
    private static final String TAG = MenuPresenter.class.getName();

    private IMenuContract.IView mView;

    public MenuPresenter(IMenuContract.IView view) {
        mView = view;
    }
}
