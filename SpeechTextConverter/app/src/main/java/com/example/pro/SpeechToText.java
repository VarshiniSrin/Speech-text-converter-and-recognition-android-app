package com.example.pro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.Locale;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SpeechToText extends AppCompatActivity {

    private ImageButton imageButton;
    ImageButton mBtnAdd, mBtnList;
    LottieAnimationView animationView1;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    //views from activity
    EditText mTextTv;
    ImageButton mVoiceBtn;

    public static SQLiteHelper mSQLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        animationView1 = (LottieAnimationView)findViewById(R.id.lottieAnimationView);
        animationView1.playAnimation();

        Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Speech_To_text);

        mTextTv = findViewById(R.id.edit_text);

        mBtnAdd = findViewById(R.id.btnAdd);
        mBtnList = findViewById(R.id.btnList);
        System.out.println("Done linking mTextTv with textTv");
        mVoiceBtn = findViewById(R.id.voiceBtn);
        //mVoiceBtn.setBackgroundColor(Color.WHITE);
        System.out.println("Done linking mVoiceBtn with voiceBtn");

        imageButton = (ImageButton)findViewById(R.id.back5);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        //button click to show speech to text dialog
        mVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Inside onClick of the mic");
                speak();
            }
        });

        mSQLiteHelper = new SQLiteHelper(this, "RECORDDB.sqlite", null, 1);

        //creating table in database
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS RECORD(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");



        //add record to sqlite
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mSQLiteHelper.insertData(
                            mTextTv.getText().toString().trim()
                    );
                    Toast.makeText(SpeechToText.this, "Added successfully", Toast.LENGTH_SHORT).show();
                    //reset views
                    mTextTv.setText("");


                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //show record list
        mBtnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start recordlist activity
                startActivity(new Intent(SpeechToText.this, RecordListActivity.class));
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
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
                        startActivity(new Intent(getApplicationContext(), about_us.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });
    }

    private void speak() {
        //intent to show speech to text dialog
        System.out.println("Inside speak the function");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //-----------------------------------------------------------
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now");

        //start intent
        try {
            //if there was no error
            //show dialog
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);

        } catch(Exception e) {
            //if there was some error
            //get message of error and show
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    //receive voice input and handle it
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                //get text array from voice intent
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                //set to text view
                assert result != null;
                mTextTv.setText(result.get(0));
            }
        }
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
                ClipData clipData = ClipData.newPlainText("text", mTextTv.getText());
                manager.setPrimaryClip(clipData);
                Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete:
                this.mTextTv.setText("");
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }


    public void back() {
        Intent intent2 = new Intent(this, Homepage.class);
        startActivity(intent2);
    }

}
