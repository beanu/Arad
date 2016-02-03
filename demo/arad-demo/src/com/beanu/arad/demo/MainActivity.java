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
import com.beanu.arad.demo.support.event.NavEvent;
import com.beanu.arad.demo.ui.fragments.NetWorkHttpFragment;
import com.beanu.arad.demo.ui.fragments.RxJavaFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;


public class MainActivity extends ToolBarActivity {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.fragment_drawer)
    FrameLayout mFragmentDrawer;


    Subscription rxSubscription;
    ActionBarDrawerToggle mDrawerToggle;
    FragmentsManager fragmentsManager;

    public enum Fragments {
        nav, listView, pic,RxJava
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar_title.setText("首页");
        //设置DrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, getActionBarToolbar(), R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        NavFragment navFragment = new NavFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_drawer, navFragment).commit();

        fragmentsManager = new FragmentsManager(this, R.id.main_fragment_container);
        fragmentsManager.addFragment(Fragments.nav.name(), NetWorkHttpFragment.class, null);
        fragmentsManager.addFragment(Fragments.listView.name(), NetWorkHttpFragment.class, null);
        fragmentsManager.addFragment(Fragments.RxJava.name(), RxJavaFragment.class, null);


        rxSubscription = Arad.bus.toObserverable(NavEvent.class).subscribe(new Action1<NavEvent>() {
            @Override
            public void call(NavEvent navEvent) {
                fragmentsManager.switchFragment(navEvent.getTag());
                mDrawerLayout.closeDrawer(mFragmentDrawer);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //TODO error
            }
        });
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
                mDrawerLayout.openDrawer(Gravity.LEFT);
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
        if (!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }
}
