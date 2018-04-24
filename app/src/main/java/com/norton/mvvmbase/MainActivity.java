package com.norton.mvvmbase;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;

import com.norton.mvvmbase.factory.AuthenFactory;
import com.norton.mvvmbase.ui.common.NavigationController;

import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity  implements HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    NavigationController navigationController;
    @Inject
    AuthenFactory authenFactory;

    Toolbar toolbar;
    NavigationView navigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            authenFactory.getUser().observe(this, userResult -> {
                if (userResult != null) {
                    ((TextView)navigationView.findViewById(R.id.nv_header_name)).setText(userResult.getFullName());
                    ((TextView)navigationView.findViewById(R.id.nv_header_email)).setText(userResult.getEmail());


                    navigationView.findViewById(R.id.nv_header_email).setVisibility(View.VISIBLE);
                } else {
                    ((TextView)navigationView.findViewById(R.id.nv_header_name)).setText("FoodFast");
                    navigationView.findViewById(R.id.nv_header_email).setVisibility(View.INVISIBLE);
                }
            });

            authenFactory.getStatus().setValue(AuthenFactory.Status.UNKNOWN);
            navigationController.toHome();


        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            navigationController.back();
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        navigationController.ItemSelected(item);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationController.ItemSelected(item);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
