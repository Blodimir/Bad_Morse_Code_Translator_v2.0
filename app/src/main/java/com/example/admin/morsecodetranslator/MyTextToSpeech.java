package com.example.admin.morsecodetranslator;

import android.speech.tts.TextToSpeech;
import android.text.TextUtils;

import java.util.Locale;

public class MyTextToSpeech{

    public Home mHome;
    private TextToSpeech mTextToSpeech;

    public MyTextToSpeech(Home home){
        this.mHome = home;
        setTextToSpeech();
    }

    /**
     * Обработка класса TextToSpeech
     */
    private void setTextToSpeech(){
        mTextToSpeech = new TextToSpeech(this.mHome, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTextToSpeech.setLanguage(
                            (mHome.getSpinner(R.id.SpinnerLanguage).getSelectedItemPosition() == 0)
                                    ? new Locale("ru")
                                    : Locale.US
                    );
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        mHome.toastMakeText("Извините, этот язык не поддерживается!");
                    } else {
                        //ConvertTextToSpeech(getEditText(R.id.EditTextResult).getText().toString());
                    }
                } else {
                    mHome.toastMakeText("Ошибка инициализации!");
                }
            }
        });
    }

    /**
     * Запуск конвертора
     */
    public void stopConvertTextToSpeech(){
        if(mTextToSpeech != null){
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
    }

    /**
     * Остановка конвертора
     */
    public void startConvertTextToSpeech(String text) {
        if(TextUtils.isEmpty(text)) {
            mTextToSpeech.speak((mHome.getSpinner(R.id.SpinnerLanguage).getSelectedItemPosition() == 0)
                            ? "И что же мне озвучивать?"
                            : "And what do I articulate?"
                    , TextToSpeech.QUEUE_FLUSH, null);
        } else {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}