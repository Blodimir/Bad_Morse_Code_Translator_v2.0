package com.example.admin.morsecodetranslator;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;

public class MyView {

    private Home mHome;

    public MyView(Home home){
        this.mHome = home;
        addView();
    }

    /**
     * Обработка View
     */
    public void addView(){
        addImageButtonDelete();
        addEditText();
        addGridView();
        addImageButtonSound();
        addFloatingActionButton(); //FAB
    }

    /**
     * Обработка ImageButtonDelete
     */
    private void addImageButtonDelete(){
        mHome.getImageButton(R.id.ImageButtonDelete).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN &&
                        mHome.getEditText(R.id.EditText).getText().toString().length() != 0) {
                    mHome.getEditText(R.id.EditText).setText("");
                    mHome.getMyTimer().dropAllTimer();

                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if (mHome.getMyNavigationItem().getMenuItemId() == R.id.translator){
                        //mHome.toastMakeText("Че-т у меня не получается");

                        mHome.getMyMorseToText().start();

                    } else if (mHome.getMyNavigationItem().getMenuItemId() == R.id.analyze){;
                        mHome.getSpeechToText().onCreate();
                    }

                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (mHome.getMyNavigationItem().getMenuItemId() == R.id.translator){

                        mHome.getMyMorseToText().stop();

                    } else if (mHome.getMyNavigationItem().getMenuItemId() == R.id.analyze){
                        //...
                    }

                    return true;
                }

                return false;
            }
        });
    }

    /**
     * Обработка EditText
     */
    private void addEditText(){
        mHome.getEditText(R.id.EditText).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mHome.getEditText(R.id.EditTextResult).setText("");

                if (mHome.getMyNavigationItem().getMenuItemId() == R.id.translator){
                    mHome.getMyArray().MorseToText(mHome.getEditText(R.id.EditText).getText().toString());
                } else if (mHome.getMyNavigationItem().getMenuItemId() == R.id.analyze){
                    mHome.getMyArray().TextToMorse(mHome.getEditText(R.id.EditText).getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mHome.getEditText(R.id.EditText).getText().toString())) {
                    mHome.getImageButton(R.id.ImageButtonDelete).setImageResource(R.drawable.ic_mic_black_24px);
                } else {
                    mHome.getImageButton(R.id.ImageButtonDelete).setImageResource(R.drawable.ic_cancel_black_24px);
                }
            }
        });
    }

    /**
     * Обработка GridView
     */
    private void addGridView(){
        mHome.getGridView(R.id.GridView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mHome.getFAB(R.id.FAB).setVisibility(View.INVISIBLE);
                    mHome.getMyTimer().dropAllTimer();
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mHome.getMyTimer().createTimerFAB();
                    return true;
                }

                return false;
            }
        });
    }

    /**
     * Обработка ImageButtonSound
     */
    private void addImageButtonSound(){
        mHome.getImageButton(R.id.ImageButtonSound).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mHome.getImageButton(R.id.ImageButtonSound).setImageResource(R.drawable.ic_volume_off_black_24px);

                    if(mHome.getMyNavigationItem().getMenuItemId() == R.id.translator) {
                        mHome.getMyTextToSpeech().startConvertTextToSpeech(mHome.getEditText(R.id.EditTextResult).getText().toString());
                    } else if (mHome.getMyNavigationItem().getMenuItemId() == R.id.analyze) {
                        mHome.getMyMorseToSignal().startConvertMorseToSignal(mHome.getEditText(R.id.EditTextResult).getText().toString());
                    }

                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mHome.getImageButton(R.id.ImageButtonSound).setImageResource(R.drawable.ic_volume_up_black_24px);

                    return true;
                }

                return false;
            }
        });
    }

    /**
     * Обработка FloatingActionButton
     */
    private void addFloatingActionButton(){
        mHome.getFAB(R.id.FAB).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mHome.getMySignal().signalPlay();
                    mHome.getMyTimer().dropAllTimer();
                    mHome.getMyTimer().setTime();
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    if (mHome.getMyTimer().getTime() <= (mHome.getMySetting().getSpeed())){ ///< Speed - Settings
                        mHome.getEditText(R.id.EditText).append(".");
                    } else {
                        mHome.getEditText(R.id.EditText).append("_");
                    }

                    mHome.getMySignal().signalStop();
                    mHome.getMyTimer().createTimerEditText();
                    return true;
                }

                return false;
            }
        });
    }
}
