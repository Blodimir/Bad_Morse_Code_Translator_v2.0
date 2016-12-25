package com.example.admin.morsecodetranslator;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MyMorseToSignal {
    private Home mHome;
    private String mText;
    private boolean mFlag;
    private Thread mThread;
    private Runnable mRunnable;
    private Handler mHandler;

    public MyMorseToSignal(Home home){
        this.mHome = home;
        mFlag = true;
        mText = "";

        mRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if(mText.length() == 0) return;
                    for (int i = 0; i < mText.length() && mFlag; i++){
                        if (mText.charAt(i) == '.'){
                            mHome.getMySignal().signalPlay();
                            Thread.sleep(50);
                            mHome.getMySignal().signalStop();

                        } else if (mText.charAt(i) == '_'){
                            mHome.getMySignal().signalPlay();
                            Thread.sleep(200);
                            mHome.getMySignal().signalStop();

                        } else if (mText.charAt(i) == ' '){
                            Thread.sleep(300);
                        }
                    }
                } catch (Exception ex){
                    mHandler.sendEmptyMessage(0);
                    stopConvertMorseToSignal();
                }
            }
        };

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mHome.toastMakeText("Возникла непредвиденная ошибка");
            }
        };
    }

    /**
     * Запуск конвертора
     */
    public void startConvertMorseToSignal(String text) {
        stopConvertMorseToSignal();
        this.mThread = new Thread(mRunnable);
        this.mText = text;
        this.mFlag = true;
        mThread.start();
    }

    /**
     * Остановка конвертора
     */
    public void stopConvertMorseToSignal() {
        mFlag = false;
        if (mThread != null){
            Thread bufferThread = mThread;
            mThread = null;
            bufferThread.interrupt();
        }
    }
}

/*
                        */