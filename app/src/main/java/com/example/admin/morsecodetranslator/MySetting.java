package com.example.admin.morsecodetranslator;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.SeekBar;

public class MySetting implements SeekBar.OnSeekBarChangeListener {
    private Home mHome;
    private int mSpeed;
    private boolean mKeyboard;

    public MySetting(Home home) {
        this.mHome = home;
        this.mSpeed = 150000000;
        this.mKeyboard = false;

        this.mHome.getSeekBar(R.id.SeekBarSpeed).setOnSeekBarChangeListener(this);

        this.mHome.getSpinner(R.id.SpinnerLanguage).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                       int selectedItemPosition, long selectedId) {

                if (selectedItemPosition == 0) mHome.getMyArray().rusDeclaringAnArray();
                if (selectedItemPosition == 1) mHome.getMyArray().euDeclaringAnArray();

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Выдает скорость реакции на нажатие в наносекундах
     */
    public int getSpeed() {
        return mSpeed;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mSpeed = 150000000 + progress;
        mHome.getTextView(R.id.TextViewSpeed).setText("Скорость реакции на нажатие (наносек): +" + mSpeed);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * работа с клавой
     */
    private void keyboard() { ///< работает с клавой
        ((InputMethodManager) mHome.getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Вытаскивает клаву
     */
    public void pullKeyboard(){
        if (!mKeyboard) {
            keyboard();
            mKeyboard = true;
        }
    }

    /**
     * Прячет клаву
     */
    public void hideKeyboard(){
        if (mKeyboard) {
            keyboard();
            mKeyboard = false;
        }
    }
}
