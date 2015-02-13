package com.beanu.arad.demo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.base.ToolBarActivity;
import com.beanu.arad.demo.fragments.NetWorkHttpFragment;
import com.beanu.arad.demo.support.event.NavEvent;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ToolBarActivity {

    @InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @InjectView(R.id.toolbar_title) TextView toolbar_title;
    @InjectView(R.id.fragment_drawer) FrameLayout mFragmentDrawer;


    ActionBarDrawerToggle mDrawerToggle;

    FragmentsManager fragmentsManager;

    public enum Fragments {
        nav, listView, pic
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        toolbar_title.setText("首页");
        //设置DrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, getActionBarToolbar(), R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        NavFragment navFragment = new NavFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_drawer, navFragment).commit();

        fragmentsManager = new FragmentsManager(this, R.id.main_fragment_container);
        fragmentsManager.addFragment(Fragments.nav.name(), NetWorkHttpFragment.class, null);
        fragmentsManager.addFragment(Fragments.listView.name(), NetWorkHttpFragment.class, null);

        Arad.bus.register(this);
    }

    /**
     * 接收左侧导航栏的点击事件
     *
     * @param event 导航栏点击事件
     */
    public void onEventMainThread(NavEvent event) {
        fragmentsManager.switchFragment(event.getTag());
        mDrawerLayout.closeDrawer(mFragmentDrawer);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Arad.bus.unregister(this);
    }
}
