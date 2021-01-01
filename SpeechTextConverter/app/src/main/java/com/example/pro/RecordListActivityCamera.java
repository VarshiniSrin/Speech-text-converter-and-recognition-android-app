package com.example.pro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class RecordListActivityCamera extends AppCompatActivity {

    ListView mListView;
    ArrayList<Model> mList;
    RecordListAdapter mAdapter = null;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list_camera);

        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Saved_Files);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Record List");

        imageButton = (ImageButton)findViewById(R.id.back1);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        mListView = findViewById(R.id.listView);
        mList = new ArrayList<>();
        mAdapter = new RecordListAdapter(this, R.layout.row, mList);
        mListView.setAdapter(mAdapter);

        //get all data from sqlite
        Cursor cursor = TextRecognition.mSQLiteHelper.getData("SELECT * FROM RECORD_CAMERA");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);

            //add to list
            mList.add(new Model(id, name));
        }
        mAdapter.notifyDataSetChanged();
        if (mList.size()==0){
            //if there is no record in table of database which means listview is empty
            //Toast.makeText(this, "No record found...", Toast.LENGTH_SHORT).show();
        }

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                //alert dialog to display options of update and delete
                final CharSequence[] items = {"Update", "Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(RecordListActivityCamera.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0){
                            //update
                            Cursor c = TextRecognition.mSQLiteHelper.getData("SELECT id FROM RECORD_CAMERA");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            //show update dialog
                            showDialogUpdate(RecordListActivityCamera.this, arrID.get(position));
                        }
                        if (i==1){
                            //delete
                            Cursor c = TextRecognition.mSQLiteHelper.getData("SELECT id FROM RECORD_CAMERA");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });


    }

    private void showDialogDelete(final int idRecord) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(RecordListActivityCamera.this);
        dialogDelete.setTitle("Warning!");
        dialogDelete.setMessage("Are you sure to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    TextRecognition.mSQLiteHelper.deleteData(idRecord);
                    Toast.makeText(RecordListActivityCamera.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateRecordList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void showDialogUpdate(Activity activity, final int position){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_dialog);
        dialog.setTitle("Update");

        final EditText edtName = dialog.findViewById(R.id.edtName);

        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

        //set width of dialog
        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*1.0);
        //set hieght of dialog
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.8);
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        //in update dialog click image view to update image


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    SpeechToText.mSQLiteHelper.updateData(
                            edtName.getText().toString().trim(),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception error){
                    Log.e("Update error", error.getMessage());
                }
                updateRecordList();
            }
        });


    }




    private void updateRecordList() {
        //get all data from sqlite
        Cursor cursor = TextRecognition.mSQLiteHelper.getData("SELECT * FROM RECORD_CAMERA");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            mList.add(new Model(id,name));
        }
        mAdapter.notifyDataSetChanged();
    }

    public void back() {
        Intent intent2 = new Intent(this, TextRecognition.class);
        startActivity(intent2);
    }



}