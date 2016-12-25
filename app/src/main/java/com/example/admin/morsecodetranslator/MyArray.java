package com.example.admin.morsecodetranslator;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MyArray{

    private Home mHome;
    private String[][] mArray;

    public MyArray(Home mHome){
        this.mHome = mHome;

        rusDeclaringAnArray();
    }

    /**
     * Вывод сообщения на экран
     */
    public String[] DimensionalArray() {
        List<String> bufferStringList;
        bufferStringList = new ArrayList<>();

        for (String[] _mArray : mArray) Collections.addAll(bufferStringList, _mArray);

        String[] bufferStringArray = new String[bufferStringList.size()];
        bufferStringArray = bufferStringList.toArray(bufferStringArray);

        return bufferStringArray;
    }

    /**
     * Поиск значения элемента в массиве
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String SearchArray(String _value) {
        for (String[] aMArray : mArray) {
            if (Objects.equals(aMArray[(mHome.getFAB(R.id.FAB).getVisibility() == View.VISIBLE) ? 1 : 0], _value)){
                return aMArray[(mHome.getFAB(R.id.FAB).getVisibility() == View.VISIBLE) ? 0 : 1];
            }
        }
        return "";
    }

    /**
     * Обработка Morse
     */
    public void MorseToText(String _morse) {
        String bufferMorse = "";
        _morse += "\0";

        for (int i = 0; i < _morse.length() - 1; i++) {
            if (_morse.charAt(i) != ' ') {
                bufferMorse += _morse.charAt(i);
            } else {
                mHome.getEditText(R.id.EditTextResult).append(SearchArray(bufferMorse));
                if (_morse.charAt(i + 1) == ' ') {
                    mHome.getEditText(R.id.EditTextResult).append(" ");
                    i++;
                }
                bufferMorse = "";
            }
        }
    }

    /**
     * Обработка текста
     */
    public void TextToMorse(String _text) {
        _text += "\0";

        for (int i = 0; i < _text.length() - 1; i++) {
            if (_text.charAt(i) == ' ') {
                mHome.getEditText(R.id.EditTextResult).append(" ");
            } else {
                mHome.getEditText(R.id.EditTextResult)
                        .append(SearchArray(String.valueOf(_text.charAt(i)).toUpperCase()) + " ");
            }
        }
    }

    /**
     * Смена языка (RU)
     */
    public void rusDeclaringAnArray() {
        mArray = new String[][]{
                {"А", "._"}, {"Б", "_..."}, {"В", ".__"}, {"Г", "__."}, {"Д", "_.."}, {"Е", "."}, {"Ж", "..._"},
                {"З", "__.."}, {"И", ".."}, {"Й", ".___"}, {"К", "_._"}, {"Л", "._.."}, {"М", "__"}, {"Н", "_."},
                {"О", "___"}, {"П", ".__."}, {"Р", "._."}, {"С", "..."}, {"Т", "_"}, {"У", ".._"}, {"Ф", ".._."},
                {"Х", "...."}, {"Ц", "_._."}, {"Ч", "___."}, {"Ш", "____"}, {"Щ", "__._"}, {"Ь", "_.._"}, {"Ъ", "_.._"},
                {"Ы", "_.__"}, {"Э", ".._.."}, {"Ю", "..__"}, {"Я", "._._"}, {"1", ".____"}, {"2", "..___"}, {"3", "...__"},
                {"4", "...._"}, {"5", "....."}, {"6", "_...."}, {"7", "__..."}, {"8", "___.."}, {"9", "____."}, {"0", "_____"},
                {".", "....."}, {",", "._._._"}, {";", "_._._."}, {":", "___..."}, {"?", "..__.."}, {"!", "__..__"},
                {"-", "_...._"}, {"\"", "._.._."}, {"/", "_.._."}
        };

        setGridView();
    }

    /**
     * Смена языка (EU)
     */
    public void euDeclaringAnArray() {
        mArray = new String[][]{
                {"А", "._"}, {"B", "_..."}, {"C", "_._."}, {"D", "_.."}, {"E", "."}, {"F", ".._."}, {"G", "__."},
                {"H", "...."}, {"I", ".."}, {"J", ".___"}, {"K", "_._"}, {"L", "._.."}, {"M", "__"}, {"N", "_."},
                {"O", "___"}, {"P", ".__."}, {"Q", "__._"}, {"R", "._."}, {"S", "..."}, {"T", "_"}, {"U", ".._"},
                {"V", "..._"}, {"W", ".__"}, {"X", "_.._"}, {"Y", "_.___"}, {"Z", "__.."}, {"1", ".____"}, {"2", "..___"},
                {"3", "...__"}, {"4", "...._"}, {"5", "....."}, {"6", "_...."}, {"7", "__..."}, {"8", "___.."},
                {"9", "____."}, {"0", "_____"}, {".", "....."}, {",", "._._._"}, {";", "_._._."}, {":", "___..."},
                {"?", "..__.."}, {"!", "__..__"}, {"-", "_...._"}, {"\"", "._.._."}, {"/", "_.._."}
         };

        setGridView();
    }

    /**
     * Изменение алфавита
     */
    private void setGridView() {
        mHome.getGridView(R.id.GridView).setAdapter(new ArrayAdapter<>(mHome,
                android.R.layout.simple_list_item_1, DimensionalArray()));
    }
}