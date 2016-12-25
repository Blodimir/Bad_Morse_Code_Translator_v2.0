package com.example.admin.morsecodetranslator;

import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

public class MyToolBar {
    private Home mHome;

    public MyToolBar(Home home){
        this.mHome = home;

        this.mHome.setSupportActionBar(this.mHome.getToolbar(R.id.ToolBar));

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this.mHome,
                this.mHome.getDrawerLayout(R.id.drawer_layout), this.mHome.getToolbar(R.id.ToolBar),
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                if ((mHome.getEditText(R.id.EditText).getVisibility() == View.VISIBLE)
                        && (mHome.getFAB(R.id.FAB).getVisibility() == View.INVISIBLE)) {

                    mHome.getMySetting().pullKeyboard();
                }

            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                mHome.getMySetting().hideKeyboard();
            }
        };

        this.mHome.getDrawerLayout(R.id.drawer_layout).setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
