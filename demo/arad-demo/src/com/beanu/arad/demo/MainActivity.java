package com.beanu.arad.demo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.beanu.arad.toolbar.ToolBarActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ToolBarActivity {


    @InjectView(R.id.main_fragment_container) FrameLayout mMainFragmentContainer;
    @InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //设置DrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, getActionBarToolbar(), R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
