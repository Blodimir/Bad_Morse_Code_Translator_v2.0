package com.example.admin.morsecodetranslator;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shuyu.waveview.AudioWaveView;

public class Home extends AppCompatActivity {

    private MyView mView;
    private MyArray mArray;
    private MyTimer mTimer;
    private MySignal mSignal;
    private MySetting mSetting;
    private MyToolBar mToolBar;
    private MyMorseToText mMorseToText;
    private MySpeechToText mSpeechToText;
    private MyTextToSpeech mTextToSpeech;
    private MyMorseToSignal mMorseToSignal;
    private MyNavigationItem mNavigationItem;

    public FloatingActionButton getFAB(int id) { return (FloatingActionButton) findViewById(id); }
    public NavigationView getNavigationView(int id) { return (NavigationView) findViewById(id); }
    public AudioWaveView getAudioWaveView(int id) {return (AudioWaveView) findViewById(id); }
    public SwitchCompat getSwitchCompat(int id) { return (SwitchCompat) findViewById(id); }
    public DrawerLayout getDrawerLayout(int id) { return (DrawerLayout) findViewById(id); }
    public ImageButton getImageButton(int id) { return (ImageButton) findViewById(id); }
    public EditText getEditText(int id) { return (EditText) findViewById(id); }
    public TextView getTextView(int id) { return (TextView) findViewById(id); }
    public GridView getGridView(int id) { return (GridView) findViewById(id); }
    public SeekBar getSeekBar(int id) { return (SeekBar) findViewById(id); }
    public Spinner getSpinner(int id) { return (Spinner) findViewById(id); }
    public Toolbar getToolbar(int id) { return (Toolbar) findViewById(id); }

    public MyNavigationItem getMyNavigationItem(){return mNavigationItem;}
    public MyMorseToSignal getMyMorseToSignal(){return mMorseToSignal;}
    public MyTextToSpeech getMyTextToSpeech(){return mTextToSpeech;}
    public MySpeechToText getSpeechToText(){return mSpeechToText;}
    public MyMorseToText getMyMorseToText(){return mMorseToText;}
    public MySetting getMySetting(){return mSetting;}
    public MyToolBar getToolBar(){return mToolBar;}
    public MySignal getMySignal(){return mSignal;}
    public MyArray getMyArray(){return mArray;}
    public MyTimer getMyTimer(){return mTimer;}
    public MyView getView(){return mView;}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNavigationItem = new MyNavigationItem(this);
        mMorseToSignal = new MyMorseToSignal(this);
        mTextToSpeech = new MyTextToSpeech(this);
        mSpeechToText = new MySpeechToText(this); //?
        mMorseToText = new MyMorseToText(this);
        mToolBar = new MyToolBar(this);
        mSetting = new MySetting(this);
        mSignal = new MySignal(this);
        mTimer = new MyTimer(this);
        mArray = new MyArray(this);
        mView = new MyView(this);
    }

    /**
     * Вывод сообщения на экран
     */
    public void toastMakeText(String text){
        Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (getDrawerLayout(R.id.drawer_layout).isDrawerOpen(GravityCompat.START))
            getDrawerLayout(R.id.drawer_layout).closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    private static long back_pressed;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) return false;

        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            toastMakeText("Нажмите еще раз, чтобы выйти!");
        }
        back_pressed = System.currentTimeMillis();

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

        Stop();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void Stop(){
        mSetting.hideKeyboard();
        mSignal.dropAllSignal();
        mTextToSpeech.stopConvertTextToSpeech();
    }
}

/*
try {
                        Uri notify = RingtoneManager.getDefaultUri(123);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notify);
                        r.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
 */

//Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//        .setAction("Action", null).show();

//Toast.makeText(getApplicationContext(),
//        "Молодой человек, не прикасайтесь ко мне!",
//        Toast.LENGTH_SHORT).show();

//Toast.makeText(getBaseContext(), "Press once again to exit!",
//Toast.LENGTH_SHORT).show();

/*//view.playSoundEffect(android.view.SoundEffectConstants.CLICK);*/

/*
new Thread(new Runnable() {
@Override
public void run() {

        }
        }).start();
        */

/*@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ...

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mSnackbar = Snackbar.make(view, "Покормил кота?", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Да", snackbarOnClickListener);
            mSnackbar.show();
        }
    });
}

View.OnClickListener snackbarOnClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "Молодец!", Toast.LENGTH_LONG).show();
    }
};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            startActivity(mIntentSettingsActivity);
            return true;
        }

return super.onOptionsItemSelected(item);
        }
        }*/