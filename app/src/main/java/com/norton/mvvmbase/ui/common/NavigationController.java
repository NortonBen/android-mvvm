package com.norton.mvvmbase.ui.common;

import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;

import com.norton.mvvmbase.MainActivity;
import com.norton.mvvmbase.R;
import com.norton.mvvmbase.factory.AuthenFactory;
import com.norton.mvvmbase.factory.HistoryFactory;
import com.norton.mvvmbase.ui.category.CategoryFragment;
import com.norton.mvvmbase.ui.home.HomeFragment;
import com.norton.mvvmbase.ui.login.LoginFragment;
import com.norton.mvvmbase.ui.product.ProductFragment;

import javax.inject.Inject;

/**
 * Created by norton on 08/04/2018.
 */

public class NavigationController {
    private final int containerId;

    private final FragmentManager fragmentManager;

    private final HistoryFactory historyFactory;

    private final AuthenFactory authenFactory;

    private MainActivity mainActivity;

    @Inject
    public NavigationController(MainActivity mainActivity, HistoryFactory historyFactory, AuthenFactory authenFactory) {
        this.historyFactory = historyFactory;
        this.authenFactory = authenFactory;
        this.mainActivity = mainActivity;
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
        historyFactory.getActivity().observe(mainActivity,  fragment -> {
            if (fragment.getClass() == HomeFragment.class) {
                mainActivity.getToolbar().setTitle("Trang Chủ");
            } else if (fragment.getClass() == LoginFragment.class) {
                mainActivity.getToolbar().setTitle("Đăng Nhập");
            } else if (fragment.getClass() == ProductFragment.class) {
                mainActivity.getToolbar().setTitle("Sản Phẩm");
            } else if (fragment.getClass() == CategoryFragment.class) {
                mainActivity.getToolbar().setTitle("Danh Mục");
            }
            fragmentManager.beginTransaction()
                    .replace(containerId, fragment)
                    .commitAllowingStateLoss();
        });

        authenFactory.getStatus().observe(mainActivity, status -> {
            if (status == AuthenFactory.Status.LOGIN) {
                menuLogin();
            }  else if(status == AuthenFactory.Status.NOT_LOGIN){
                menuNotLogin();
            }
        });
    }

    public void toLogin() {

        LoginFragment loginFragment = new LoginFragment();
        historyFactory.to(loginFragment);
    }

    public void toLogout() {
        authenFactory.logout();
    }

    public void toHome() {
        HomeFragment homeFragment = new HomeFragment();
        historyFactory.to(homeFragment);
    }

    public void toCategiryID(int id) {
        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.of(id);
        historyFactory.to(categoryFragment);
    }

    public void toProductID(int id) {
        ProductFragment productFragment = new ProductFragment();
        productFragment.of(id);
        historyFactory.to(productFragment);
    }

    public void back() {
        if (!historyFactory.back()) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

    public void ItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home: {
                toHome();
                break;
            }
            case R.id.nav_cart: {

                break;
            }
        }

    }

    public void menuNotLogin() {
        mainActivity.getNavigationView().getMenu().getItem(2).setVisible(true);
        mainActivity.getNavigationView().getMenu().getItem(3).setVisible(true);
        mainActivity.getNavigationView().getMenu().getItem(4).setVisible(false);
        mainActivity.getNavigationView().getMenu().getItem(6).setVisible(false);
    }

    public void menuLogin() {
        mainActivity.getNavigationView().getMenu().getItem(2).setVisible(false);
        mainActivity.getNavigationView().getMenu().getItem(3).setVisible(false);
        mainActivity.getNavigationView().getMenu().getItem(4).setVisible(true);
        mainActivity.getNavigationView().getMenu().getItem(6).setVisible(true);
    }
}
