package com.example.pro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Locale;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Homepage extends AppCompatActivity {

    LottieAnimationView animationView1;
    private Button b1;
    private Button b2;
    private Button b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        animationView1 = (LottieAnimationView)findViewById(R.id.lottieAnimationView);
        animationView1.playAnimation();


        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);

        b1 = (Button)findViewById(R.id.buttonTextToSpeech);
        b2 = (Button)findViewById(R.id.buttonSpeechToText);
        b3 = (Button)findViewById(R.id.buttonTextRecognition);

        System.out.println("HI CLICK ON A BUTTON");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTextToSpeech();
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("YOU SELECTED SPEECH TO TEXT");
                openSpeechToText();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTextRecognition();
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
    }

    public void openTextToSpeech() {
        Intent intent = new Intent(this, text_to_speech.class);
        startActivity(intent);
    }

    public void openSpeechToText() {
        System.out.println("Inside speech to text");
        Intent intent = new Intent(this, SpeechToText.class);
        startActivity(intent);
    }

    public void openTextRecognition() {
        Intent intent = new Intent(this, TextRecognition.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.faq:
                startActivity(new Intent(getApplicationContext(), faq.class));
                overridePendingTransition(0,0);
                return true;
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(), settings.class));
                overridePendingTransition(0,0);
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options,menu);
        return true;
    }



}
