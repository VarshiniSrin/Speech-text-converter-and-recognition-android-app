package com.example.pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class privacy_policy extends AppCompatActivity {

    private ImageButton imageButton;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Privacy_Policy);

        animationView = (LottieAnimationView)findViewById(R.id.lottieAnimationView3);
        animationView.playAnimation();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.privacy_policy);

        imageButton = (ImageButton)findViewById(R.id.back3);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Homepage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.privacy_policy:
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

    public void back() {
        Intent intent2 = new Intent(this, Homepage.class);
        startActivity(intent2);
    }
}
