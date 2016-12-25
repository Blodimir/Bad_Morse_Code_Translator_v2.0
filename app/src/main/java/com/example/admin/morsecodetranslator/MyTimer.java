package com.example.admin.morsecodetranslator;

import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {

    private Home mHome;
    private Timer mTimer;
    private int mTimerAmount; ///< Кол-во пробелов
    private boolean mTimerFlagSchedule; ///< Пропуск для 2 и более пробелов.
    private long mStartTime; ///< Время начала отсчета (кнопка нажата)

    public MyTimer(Home home) {
        this.mHome = home;
        this.mTimerAmount = 1;
        this.mTimerFlagSchedule = true;
        this.mStartTime = 0;
    }

    /**
     * запись текущего времени
     */
    public void setTime() {
        mStartTime = System.nanoTime();
    }

    /**
     * вывод разницы времени
     */
    public long getTime() {
        return System.nanoTime() - mStartTime;
    }

    /**
     * Возврат обектов к дефолтному состофнию и вызов обработки текста
     */
    public void createTimerEditText(){
        mTimerFlagSchedule = true;
        mTimerAmount = 1;
        createTimerEditTextSchedule();
    }

    /**
     * Обработки текста
     */
    private void createTimerEditTextSchedule() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHome.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHome.getEditText(R.id.EditText).append(" ");
                    }
                });

                mTimerAmount++;

                if (mTimerFlagSchedule && (mTimerAmount <= 2)) {
                    dropAllTimer();
                    createTimerEditTextSchedule();
                }
            }
        }, 700);
    }

    /**
     * Обработки виджета FloatingActionButton
     */
    public void createTimerFAB() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHome.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHome.getFAB(R.id.FAB).setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 1000);
    }

    /**
     * Удаление объекта Timer
     */
    public void dropAllTimer() {
        mTimerFlagSchedule = false;
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
