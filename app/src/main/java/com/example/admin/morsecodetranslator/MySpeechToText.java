package com.example.admin.morsecodetranslator;

import ru.yandex.speechkit.*;
import ru.yandex.speechkit.gui.RecognizerActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

public class MySpeechToText extends Activity {

    private Home mHome;
    private static final String API_KEY = "8e0caea0-48b8-497f-828c-c71a6bf73798";
    private static final int REQUEST_CODE = 31;

    public MySpeechToText(Home home) {
        this.mHome = home;
        SpeechKit.getInstance().configure(this.mHome, API_KEY);
    }

    /**
     * Запуск активити SpeechKit
     */
    public void onCreate(){
        mHome.getMySetting().hideKeyboard();
        mHome.startActivityForResult((new Intent(mHome, RecognizerActivity.class)
                .putExtra(RecognizerActivity.EXTRA_MODEL, Recognizer.Model.QUERIES)
                .putExtra(RecognizerActivity.EXTRA_LANGUAGE, Recognizer.Language.RUSSIAN)), REQUEST_CODE);
    }

    /**
     * Обработка результатов SpeechKit
     *
     * Где-то блокируется микрофон!!! SpeechKit говорит, что ничего не слышно при анализе звука.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == RecognizerActivity.RESULT_OK && data != null) {
                final String result = data.getStringExtra(RecognizerActivity.EXTRA_RESULT);
                setTextResult(result);
            } else if (resultCode == RecognizerActivity.RESULT_ERROR){
                String error = ((ru.yandex.speechkit.Error)
                        data.getSerializableExtra(RecognizerActivity.EXTRA_ERROR)).getString();
                setTextResult(error);
            }
        }
        mHome.getMySetting().pullKeyboard();
    }

    /**
     * Запись обработтаной информации
     */
    private void setTextResult(String text){
        mHome.getEditText(R.id.EditText).setText(text);
    }
}
