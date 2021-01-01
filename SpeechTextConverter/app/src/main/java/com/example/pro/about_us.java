package com.example.pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.Locale;


public class about_us extends AppCompatActivity {

    private ImageButton imageButton;
    Button emailButton;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.About_Us);


        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch (position) {
                    case 0:
                        openBrowser();
                        break;
                    case 1:
                        openEmail();
                        break;
                    case 2:
                        openInsta();
                        break;
                    case 3:
                        openMaps();
                        break;
                    default:
                        break;
                }
            }
        });

        animationView = (LottieAnimationView)findViewById(R.id.lottieAnimationView3);
        animationView.playAnimation();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.about_us);

        imageButton = (ImageButton)findViewById(R.id.back1);

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

                        return true;


                }
                return false;
            }
        });

    }

//SENDING EMAIL
    public void openEmail() {
        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:example@gmail.com")));
    }

//OPENING WEBSITE
    public void openBrowser() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naturalreaders.com/online/"));
        startActivity(browserIntent);
    }

//OPENING INSTA
    public void openInsta() {
        Uri uri = Uri.parse("https://www.instagram.com/dyslexia_in_adults/");
        Intent insta = new Intent(Intent.ACTION_VIEW, uri);
        insta.setPackage("com.instagram.android");
        try{
            startActivity(insta);
        }
        catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/dyslexia_in_adults/")));
        }

    }

//OPENING MAPS
public void openMaps() {
    Uri gmmIntentUri = Uri.parse("geo:0,0?z=4&q=current+location");
    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
    mapIntent.setPackage("com.google.android.apps.maps");
    startActivity(mapIntent);
}


    public void back() {
        Intent intent2 = new Intent(this, Homepage.class);
        startActivity(intent2);
    }
}
