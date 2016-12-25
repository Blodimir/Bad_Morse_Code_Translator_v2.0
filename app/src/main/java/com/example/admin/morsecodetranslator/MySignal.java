package com.example.admin.morsecodetranslator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;

public class MySignal implements SoundPool.OnLoadCompleteListener{

    private Home mHome;
    private int mSound;
    private SoundPool mSoundPool;
    private Camera mCamera;
    private Camera.Parameters mParametersCamera;
    private Vibrator mVibrator;

    public MySignal(Home mHome){
        this.mHome = mHome;
    }

    /**
     * Запуск сигналов относительно настроек
     */
    public void signalPlay(){
        if (mHome.getSwitchCompat(R.id.SwitchCompatSound).isChecked()) {
            soundPoolOn();
        }
        if (mHome.getSwitchCompat(R.id.SwitchCompatShine).isChecked()) {
            cameraOn();
        }
        if (mHome.getSwitchCompat(R.id.SwitchCompatVibration).isChecked()) {
            vibratorOn();
        }
    }

    /**
     * Остановка сигналов относительно настроек
     */
    public void signalStop(){
        if (mHome.getSwitchCompat(R.id.SwitchCompatSound).isChecked()){
            soundPoolOff();
        }
        if (mHome.getSwitchCompat(R.id.SwitchCompatShine).isChecked()){
            cameraOff();
        }
        if (mHome.getSwitchCompat(R.id.SwitchCompatVibration).isChecked()){
            vibratorOff();
        }
    }

    /**
     * Включенеи светового сигнала
     */
    private void cameraOn() {
        createCamera();

        if (mParametersCamera == null || mCamera == null) {
            mHome.toastMakeText("Обнаружена проблема со сведтодиодом!");
            return;
        }

        mParametersCamera.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(mParametersCamera);
        mCamera.startPreview();
    }

    /**
     * Отключение светового сигнала
     */
    private void cameraOff() {
        mParametersCamera.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(mParametersCamera);
        mCamera.stopPreview();
        dropCamera();
    }

    /**
     * Включение звукового сигнала
     */
    private void soundPoolOn() {
        createSoundPool();

        if (mSoundPool == null) {
            mHome.toastMakeText("Обнаружена проблема c динамиком!");
            return;
        }

        mSoundPool.play(mSound, 1, 1, 0, 10000, 2);
    }

    /**
     * Отключение звукового сигнала
     */
    private void soundPoolOff() {
        dropSoundPool();
    }

    /**
     * Включение вибро-сигнала
     */
    private void vibratorOn() {
        createVibrator();

        if (mVibrator == null){
            mHome.toastMakeText("Обнаружена проблема c вибрацией!");
        }

        mVibrator.vibrate(10000);
    }

    /**
     * Отключение вибро-сигнала
     */
    private void vibratorOff() {
        dropVibrator();
    }

    /**
     * Создание объектов для вибро-сигналов
     */
    private void createVibrator() {
        mVibrator = (Vibrator) mHome.getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * Создание объектов для светового сигнала
     */
    private void createCamera() {
        if (!mHome.getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            showCameraAlert();
        } else {
            mCamera = Camera.open();
            mParametersCamera = mCamera.getParameters();
        }
    }

    /**
     * Ошибки при создании объектов звукового сигнала
     */
    private void showCameraAlert() {
        new AlertDialog.Builder(mHome)
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_text)
                .setPositiveButton(R.string.exit_message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHome.finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Создание объектов для звукового сигнала
     */
    private void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createSoundPoolWithBuilder();
        } else {
            createSoundPoolWithConstructor();
        }

        mSoundPool.setOnLoadCompleteListener(this); ///< Устанавливаем слушателя загрузки
        mSound = mSoundPool.load(mHome, R.raw.morse, 1); ///< Загружаем файл
    }

    /**
     * Создание объектов для звукового сигнала (SDK_INT >= LOLLIPOP)
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createSoundPoolWithBuilder() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        mSoundPool = new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(1).build();
    }

    /**
     * Создание объектов для звукового сигнала (SDK_INT < LOLLIPOP)
     */
    @SuppressWarnings("deprecation")
    private void createSoundPoolWithConstructor() {
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }

    /**
     * Удаление всех обектов
     */
    public void dropAllSignal(){
        dropCamera();
        dropSoundPool();
        dropVibrator();
    }

    /**
     * Удаление обектов светового сигнала
     */
    private void dropCamera() {
        if (mParametersCamera != null){
            mParametersCamera.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

            if (mCamera != null) {
                mCamera.setParameters(mParametersCamera);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }

            mParametersCamera = null;
        }
    }

    /**
     * Удаление обектов звукового сигнала
     */
    private void dropSoundPool() {
        if (mSoundPool != null) {
            mSoundPool.release();
            mSoundPool = null;
        }
    }

    /**
     * Удаление обектов вибро-сигнала
     */
    private void dropVibrator() {
        if (mVibrator != null){
            mVibrator.cancel();
            mVibrator = null;
        }
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

    }

}
