package com.example.admin.morsecodetranslator;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;

public class MyNavigationItem implements
        NavigationView.OnNavigationItemSelectedListener {

    private Home mHome;
    private int mMenuItemId;

    public MyNavigationItem(Home home){
        this.mHome = home;
        this.mHome.getNavigationView(R.id.nav_view).setNavigationItemSelectedListener(this);
        this.mMenuItemId = R.id.translator;

        setVisibilityViewTranslate();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mMenuItemId = item.getItemId();

        mHome.getMyTimer().dropAllTimer();

        if (mMenuItemId == R.id.translator) {
            setVisibilityViewTranslate();
            editFocusable(false);
            setVisibilityViewSetting();

            mHome.getEditText(R.id.EditTextResult).setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
            mHome.getEditText(R.id.EditTextResult).setLines(1);

        } else if (mMenuItemId == R.id.analyze) {
            setVisibilityViewTranslate();
            editFocusable(true);
            setVisibilityViewSetting();

            mHome.getEditText(R.id.EditTextResult).setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            mHome.getEditText(R.id.EditTextResult).setLines(5);

        } else if (mMenuItemId == R.id.setting) {
            setVisibilityViewSetting();
            setVisibilityViewTranslate();
        }

        mHome.getEditText(R.id.EditText).setText("");
        mHome.getDrawerLayout(R.id.drawer_layout).closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Работа с фокусом EditText
     */
    private void editFocusable(boolean focusable){
        mHome.getEditText(R.id.EditText).setFocusableInTouchMode(focusable);
        mHome.getEditText(R.id.EditText).setFocusable(focusable);
        mHome.getEditText(R.id.EditText).setCursorVisible(focusable);
        mHome.getEditText(R.id.EditText).setLongClickable(focusable);
    }

    /**
     * Работа с основными View элементами транслятора
     */
    private void setVisibilityViewTranslate(){
        int visibility = (mMenuItemId == R.id.setting) ? View.INVISIBLE : View.VISIBLE;
        mHome.getEditText(R.id.EditText).setVisibility(visibility);
        mHome.getEditText(R.id.EditTextResult).setVisibility(visibility);
        mHome.getFAB(R.id.FAB).setVisibility((mMenuItemId != R.id.analyze) ? visibility : View.INVISIBLE);
        mHome.getGridView(R.id.GridView).setVisibility((mMenuItemId != R.id.analyze) ? visibility : View.INVISIBLE);
        mHome.getImageButton(R.id.ImageButtonDelete).setVisibility(visibility);
        mHome.getImageButton(R.id.ImageButtonSound).setVisibility(visibility);
    }

    /**
     * Работа с View для настроками транслятора
     */
    private void setVisibilityViewSetting(){
        int visibility = (mMenuItemId == R.id.setting) ? View.VISIBLE : View.INVISIBLE;
        mHome.getSwitchCompat(R.id.SwitchCompatSound).setVisibility(visibility);
        mHome.getSwitchCompat(R.id.SwitchCompatShine).setVisibility(visibility);
        mHome.getSwitchCompat(R.id.SwitchCompatVibration).setVisibility(visibility);
        mHome.getTextView(R.id.TextViewSpeed).setVisibility(visibility);
        mHome.getSeekBar(R.id.SeekBarSpeed).setVisibility(visibility);
        mHome.getTextView(R.id.TextViewLanguage).setVisibility(visibility);
        mHome.getSpinner(R.id.SpinnerLanguage).setVisibility(visibility);
    }

    /**
     * Вытаскивает идентификатор меню
     */
    public int getMenuItemId() {
        return mMenuItemId;
    }
}
