package com.example.admin.morsecodetranslator;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.czt.mp3recorder.MP3Recorder;
import com.shuyu.waveview.AudioPlayer;
import com.shuyu.waveview.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class MyMorseToText {

    private Home mHome;

    private  MP3Recorder mRecorder;
    private AudioPlayer audioPlayer;

    private String filePath;

    private boolean mIsRecord = false;
    private boolean mIsPlay = false;

    private int duration;
    private int curPosition;

    public MyMorseToText(Home home) {
        this.mHome = home;

        audioPlayer = new AudioPlayer(mHome, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case AudioPlayer.HANDLER_CUR_TIME:
                        curPosition = (int) msg.obj;
                        break;
                    case AudioPlayer.HANDLER_COMPLETE:
                        mIsPlay = false;
                        break;
                    case AudioPlayer.HANDLER_PREPARED:
                        duration = (int) msg.obj;
                        break;
                    case AudioPlayer.HANDLER_ERROR:
                        resolveResetPlay();
                        break;
                }
            }
        });
    }

    /**
     * Запуск алгоритмов
     */
    public void start(){
        startUI();
        resolveRecord();
    }

    /**
     * Остановка работы алгоритмов
     */
    public void stop(){
        stopUI();
        resolveStopRecord();
        signalAnalysis(); ///< ...
        resolveResetPlay();
    }

    /**
     * Анализ звука
     */
    private void signalAnalysis(){
        //...не смог реализовать, т.к. что-то не так с микрофоном
        //...я так и не разобрался, что его блокирует :(
    }

    private void startUI() {
        mHome.getAudioWaveView(R.id.audioWave).setVisibility(View.VISIBLE);
        mHome.getGridView(R.id.GridView).setVisibility(View.INVISIBLE);
    }

    private void stopUI() {
        mHome.getAudioWaveView(R.id.audioWave).setVisibility(View.INVISIBLE);
        mHome.getGridView(R.id.GridView).setVisibility(View.VISIBLE);
    }

    /**
     * Старт записи аудио-потока
     */
    private void resolveRecord() {
        filePath = FileUtils.getAppPath();
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                mHome.toastMakeText("Не удалось создать файл");
                return;
            }
        }
        filePath = FileUtils.getAppPath() + UUID.randomUUID().toString() + ".mp3";
        mRecorder = new MP3Recorder(new File(filePath));
        int size = getScreenWidth(mHome) / dip2px(mHome, 1);
        mRecorder.setDataList(mHome.getAudioWaveView(R.id.audioWave).getRecList(), size);
        mRecorder.setErrorHandler(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == MP3Recorder.ERROR_TYPE) {
                    mHome.toastMakeText("Нет разрешения на использование микрофона"); // !!! WTF ?!
                    resolveError();
                }
            }
        });

        //audioWave.setBaseRecorder(mRecorder);

        try {
            mRecorder.start();
            mHome.getAudioWaveView(R.id.audioWave).startView();
        } catch (IOException e) {
            e.printStackTrace();
            mHome.toastMakeText("Запись прошла с внутренней ошибкой");
            resolveError();
            return;
        }
        mIsRecord = true;
    }

    /**
     * Возобнавление записи
     */
    private void resolvePlayRecord() {
        if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
            mHome.toastMakeText("Файл не существует");
            return;
        }
        mIsPlay = true;
        audioPlayer.playUrl(filePath);
    }

    /**
     * Остановить запись
     */
    private void resolveStopRecord() {
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.setPause(false);
            mRecorder.stop();
            mHome.getAudioWaveView(R.id.audioWave).stopView();
        }
        mIsRecord = false;
    }

    /**
     * Пауза
     */
    private void resolvePause() {
        if (!mIsRecord) {
            return;
        }
        if (mRecorder.isPause()) {
            mRecorder.setPause(false);
        } else {
            mRecorder.setPause(true);
        }
    }

    /**
     * Сброс
     */
    private void resolveResetPlay() {
        filePath = "";
        if (mIsPlay) {
            mIsPlay = false;
            audioPlayer.pause();
        }
    }

    /**
     * Исключение при записи
     */
    private void resolveError() {
        FileUtils.deleteFile(filePath);
        filePath = "";
        if (mRecorder != null && mRecorder.isRecording()) {
            mRecorder.stop();
        }
    }

    /**
     * Определяет ширину акрана
     */
    private static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * Определяет высоту экрана
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    private static int dip2px(Context context, float dipValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * fontScale + 0.5f);
    }
}