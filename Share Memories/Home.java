package com.tanyadas.wedmist.shareMemories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Home extends MenuForAllActivity {

    //the recyclerview
    RecyclerView recyclerView;
    List<CustomHome> postList;
    CustomAdapterHome customAdapterHome;
    DataBaseHelper dataBaseHelper;
    Cursor cursor;
    MaterialToolbar materialToolbar;
    private static final String TAG = "My log " ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_home);
        materialToolbar = (MaterialToolbar) findViewById(R.id.topAppBar);

        setSupportActionBar(materialToolbar);
        getSupportActionBar().setTitle(" ");
        dataBaseHelper = new DataBaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //myProfile = (ExtendedFloatingActionButton)findViewById(R.id.addPhoto_home);
       postList = new ArrayList<>();
        displayPosts();

        customAdapterHome = new CustomAdapterHome(this, postList);
        recyclerView.setAdapter(customAdapterHome);


    }
    void displayPosts()
    {
        cursor = dataBaseHelper.fetchData();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(this,"No Memories Yet...",Toast.LENGTH_SHORT).show();

        }
        else
        {
            while(cursor.moveToNext())
            {
               postList.add(new CustomHome(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));//num display no.of column

            }
        }
    }
}