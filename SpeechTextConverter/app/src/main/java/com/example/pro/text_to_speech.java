package com.example.pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class text_to_speech extends AppCompatActivity {

    private ImageButton imageButton;
    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private ImageButton mButtonSpeak;
    LottieAnimationView animationView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        animationView1 = (LottieAnimationView)findViewById(R.id.lottieAnimationView);
        animationView1.playAnimation();

        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Text_To_speech);

        mButtonSpeak = findViewById(R.id.button_speak);

        final Locale language = getResources().getConfiguration().locale;
        final String current = language.toString();
        System.out.println("Language chosen is" + current);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    if ("en".equals(current))
                    {
                        result = mTTS.setLanguage(Locale.ENGLISH);
                    }
                    if ("zh".equals(current))
                    {
                        result = mTTS.setLanguage(Locale.CHINESE);
                    }
                    if ("fr".equals(current))
                    {
                        result = mTTS.setLanguage(Locale.FRENCH);
                    }
                    if ("de".equals(current))
                    {
                        result = mTTS.setLanguage(Locale.GERMAN);
                    }
                    if ("it".equals(current))
                    {
                        result = mTTS.setLanguage(Locale.ITALIAN);
                    }
                    if ("ja".equals(current))
                    {
                        result = mTTS.setLanguage(Locale.JAPANESE);
                    }
                    if ("ko".equals(current))
                    {
                        result = mTTS.setLanguage(Locale.KOREAN);
                    }


                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                    else
                    {
                        mButtonSpeak.setEnabled(true);
                    }
                }
                else
                {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        imageButton = (ImageButton)findViewById(R.id.back6);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        mEditText = findViewById(R.id.edit_text);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.privacy_policy:
                        startActivity(new Intent(getApplicationContext(), privacy_policy.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.share:
                        Intent myIntent = new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        String shareBody ="Your body here";
                        String shareSub = "Your Subject here";
                        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(myIntent, "Share Using"));
                        return true;

                    case R.id.about_us:
                        startActivity(new Intent(getApplicationContext(), about_us.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                // //#$_~`•√÷×¶∆£¢€¥^°™✓<> are all not allowed

                if (s.toString().contains("<") || s.toString().contains(">") ||s.toString().contains("#") || s.toString().contains("$") || s.toString().contains("_") || s.toString().contains("~") || s.toString().contains("`") || s.toString().contains("•") || s.toString().contains("√") || s.toString().contains("÷")|| s.toString().contains("×")|| s.toString().contains("¶")|| s.toString().contains("∆")|| s.toString().contains("£")|| s.toString().contains("¢") || s.toString().contains("€") || s.toString().contains("¥") || s.toString().contains("^") || s.toString().contains("°") || s.toString().contains("™") || s.toString().contains("✓")) {
                    printMsg("Those special characters are not allowed");
                }
            }
        });

    }



    private void speak() {
        String text = mEditText.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.copy_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_copy:
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", mEditText.getText());
                manager.setPrimaryClip(clipData);
                Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete:
                this.mEditText.setText("");
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }

    void printMsg(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    public void back() {
        Intent intent2 = new Intent(this, Homepage.class);
        startActivity(intent2);
    }

}
