package com.tanyadas.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
ListView listView;
String animal[]= {"Dog", "Cat", "Duck", "Sparrow", "Swan", "Cow", "Goat", "Bear", "Donkey", "Lion", "Tiger"
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,animal);
        listView.setAdapter(arrayAdapter);
    }
}