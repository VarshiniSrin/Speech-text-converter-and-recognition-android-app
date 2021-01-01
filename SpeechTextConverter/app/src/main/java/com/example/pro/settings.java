package com.example.pro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Locale;


public class settings extends AppCompatActivity {

    private ImageButton imageButton;
    LottieAnimationView animationView;

    TextView txtv;
    private Button btnToggleDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.action_settings);


        animationView = (LottieAnimationView)findViewById(R.id.lottieAnimationView2);
        animationView.playAnimation();

        btnToggleDark = findViewById(R.id.switch2);

        // Saving state of our app
        // using SharedPreferences
        SharedPreferences sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);

        imageButton = (ImageButton)findViewById(R.id.back4);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });


        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
            btnToggleDark.setText(R.string.Disable_Dark_Mode);
        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            btnToggleDark
                    .setText(R.string.Enable_Dark_Mode);
        }



        Button button = (Button)findViewById(R.id.btn);
        txtv = (TextView) findViewById(R.id.select_language);

        btnToggleDark.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view)
                    {
                        // When user taps the enable/disable
                        // dark mode button
                        if (isDarkModeOn) {

                            // if dark mode is on it
                            // will turn it off
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_NO);
                            // it will set isDarkModeOn
                            // boolean to false
                            editor.putBoolean(
                                    "isDarkModeOn", false);
                            editor.apply();

                            // change text of Button
                            btnToggleDark.setText(R.string.Enable_Dark_Mode);
                        }
                        else {

                            // if dark mode is off
                            // it will turn it on
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_YES);

                            // it will set isDarkModeOn
                            // boolean to true
                            editor.putBoolean(
                                    "isDarkModeOn", true);
                            editor.apply();

                            // change text of Button
                            btnToggleDark.setText(R.string.Disable_Dark_Mode);
                        }
                    }
                });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });

    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {"English","日本人","中文","Français", "Deutsche", "Italiana" ,"한국어", };
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(settings.this);
        mBuilder.setTitle("Choose language");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0) {
                    setLocale("en");
                    recreate();
                }
                if(i == 1) {
                    setLocale("ja");
                    recreate();
                }
                if(i == 2) {
                    setLocale("zh");
                    recreate();
                }
                if(i == 3) {
                    setLocale("fr");
                    recreate();
                }
                if(i == 4) {
                    setLocale("de");
                    recreate();
                }
                if(i == 5) {
                    setLocale("it");
                    recreate();
                }
                if(i == 6) {
                    setLocale("ko");
                    recreate();
                }
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String s) {

        Locale locale = new Locale(s);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My lang", s);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My lang","");
        setLocale(language);
    }

    public void back() {
        Intent intent2 = new Intent(this, Homepage.class);
        startActivity(intent2);
    }
}
