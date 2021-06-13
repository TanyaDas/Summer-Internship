package com.tanyadas.wedmist.shareMemories;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuForAllActivity extends AppCompatActivity {
    private static final String TAG = " MenuForAllActivity";
    BottomNavigationView bottomNavigationView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_app_bar, menu);
        bottomNavigationView = findViewById(R.id.bottom_navigation);


        //For Bottom Navigation Bar
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.profile:
                                Intent i = new Intent(MenuForAllActivity.this, UserProfile.class);
                                Log.d(TAG, "onNavigationItemSelected: Profile Clicked");
                                startActivity(i);
                                Toast.makeText(MenuForAllActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.home:
                                Toast.makeText(MenuForAllActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.search:
                                Toast.makeText(MenuForAllActivity.this, "Search Clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.addPhoto:
                                Toast.makeText(MenuForAllActivity.this, "AddPhoto Clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.favourite:
                                Toast.makeText(MenuForAllActivity.this, " Favourite Clicked", Toast.LENGTH_SHORT).show();
                                break;
                            // TODO: Other cases
                            // If we got here, the user's action was not recognized.
                            // Invoke the superclass to handle it.
                        }
                        return true;
                    }
                });

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                recreate();
            }

        });


        return true;
    }


    //Set onClick Events for menu items displayed in top Action Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.message_app_bar:
                Toast.makeText(this, "Message Clicked", Toast.LENGTH_SHORT).show();
                break;
            // TODO: Other cases
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
        }
        return super.onOptionsItemSelected(item);
    }


}
