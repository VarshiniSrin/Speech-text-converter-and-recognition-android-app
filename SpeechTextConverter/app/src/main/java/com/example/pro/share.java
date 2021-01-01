package com.example.pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class share extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.share);

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
}
