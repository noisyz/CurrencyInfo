package com.noisyz.mvvmbase.ui.activity.abs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.noisyz.mvvmbase.R;


public abstract class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, FragmentManager.OnBackStackChangedListener {

    protected DrawerLayout drawer;
    protected ActionBarDrawerToggle toggle;
    protected NavigationView navigationView;
    protected View header;

    protected String lastTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initToolbarAndDrawer();
        initNavView();
    }


    private void initToolbarAndDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                updateHeaderView(header);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        toolbar.setNavigationOnClickListener(this);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        setFragment(item.getItemId());
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void initNavView() {
        navigationView.inflateMenu(getMenuId());
        header = LayoutInflater.from(this).inflate(getHeaderLayoutId(), null);
        navigationView.addHeaderView(header);
        navigationView.setCheckedItem(getStartId());
        setFragment(getStartId());

        updateHeaderView(header);
    }

    protected abstract void updateHeaderView(View header);


    @Override
    public void onBackStackChanged() {
        if (getSupportActionBar() != null) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                toggle.syncState();
                setTitle(lastTitle);
            }
        }
    }

    @Override
    public void onClick(View v) {
        handleBackArrowButtonClick();
    }

    public void setFragment(int navId) {
        lastTitle = navigationView.getMenu().findItem(navId).getTitle().toString();
        setTitle(lastTitle);
        navigationView.getMenu().findItem(navId).setChecked(true);
        Fragment fragment = getFragmentById(navId);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.content_main, fragment).commit();
        }
    }

    private void handleBackArrowButtonClick() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    protected abstract Fragment getFragmentById(int navId);

    protected abstract int getMenuId();

    protected abstract int getHeaderLayoutId();

    protected abstract int getStartId();
}
