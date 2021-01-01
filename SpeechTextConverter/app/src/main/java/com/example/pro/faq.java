package com.example.pro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.HashMap;

public class faq extends AppCompatActivity
{
    private ImageButton imageButton;
    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<String, GroupInfo>();
    private ArrayList<GroupInfo> deptList = new ArrayList<GroupInfo>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        Toolbar toolbar = (Toolbar) findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.FAQ);

        imageButton = (ImageButton)findViewById(R.id.back2);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        loadData();

        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.simpleExpandableListView);
        // create the adapter by passing your ArrayList data
        listAdapter = new CustomAdapter(faq.this, deptList);
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(listAdapter);

        //expand all the Groups
        //expandAll();

        // setOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //get the child info
                ChildInfo detailInfo =  headerInfo.getProductList().get(childPosition);
                //display it or do something with it
                return false;
            }
        });
        // setOnGroupClickListener listener for group heading click
        simpleExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the group header
                GroupInfo headerInfo = deptList.get(groupPosition);
                //display it or do something with it
                //Toast.makeText(getBaseContext(), " Header is :: " + headerInfo.getName(),
                //Toast.LENGTH_LONG).show();

                return false;
            }
        });


    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            simpleExpandableListView.collapseGroup(i);
        }
    }

    //load some initial data into out list
    private void loadData(){

        addProduct("Text to Speech","How do I convert?" + "\n" + "Enter text in the space provided and then click on the speaker button.");
        addProduct("Text to Speech","How do I change pitch and speed?" + "\n" + "Enter text in the space provided and then adjust pitch and speed using the bar.");
        addProduct("Text to Speech","How do I copy the text?" + "\n" + "Enter text in the space provided and then click on the copy button in the top right corner.");
        addProduct("Text to Speech","How do I delete the text?" + "\n" + "Enter text in the space provided and then click on the delete button in the top right corner.");

        addProduct("Speech to text","How do I convert?" + "\n" + "Enter text in the space provided and then click on the mic button.");
        addProduct("Speech to text","How do I save?" + "\n" + "Enter text in the space provided and then click on the save button in the bottom left corner.");
        addProduct("Speech to text","How do I view saved texts?" + "\n" + "Click on the folder button in the bottom right corner.");

        addProduct("Text Recognition","How do I recognize from camera?" + "\n" + "Click on the camera button and take a photo.");
        addProduct("Text Recognition","How do I recognize from gallery?" + "\n" + "Click on the gallery button and select a photo.");
        addProduct("Text Recognition","How do I save?" + "\n" + "Enter text in the space provided and then click on the save button in the bottom left corner.");
        addProduct("Text Recognition","How do I view saved texts?" + "\n" + "Click on the folder button in the bottom right corner.");



    }



    //here we maintain our products in various departments
    private int addProduct(String department, String product){

        int groupPosition = 0;

        //check the hash map if the group already exists
        GroupInfo headerInfo = subjects.get(department);
        //add the group if doesn't exists
        if(headerInfo == null){
            headerInfo = new GroupInfo();
            headerInfo.setName(department);
            subjects.put(department, headerInfo);
            deptList.add(headerInfo);
        }

        //get the children for the group
        ArrayList<ChildInfo> productList = headerInfo.getProductList();
        //size of the children list
        int listSize = productList.size();
        //add to the counter
        listSize++;

        //create a new child and add that to the group
        ChildInfo detailInfo = new ChildInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;





    }

    public void back() {
        Intent intent2 = new Intent(this, Homepage.class);
        startActivity(intent2);
    }
}
