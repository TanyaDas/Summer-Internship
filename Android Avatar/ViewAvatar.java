package com.tanyadas.androidAvatar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.os.Bundle;
import android.view.MenuItem;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class ViewAvatar extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Your Android Avatar");
        //getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_view_avatar);

        // Only create new fragments when there is no previously saved state
        if(savedInstanceState == null)
        {
            // Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
            // Use setListindex(int index) to set the list index for all BodyPartFragments

            //Use a FragmentManager and transaction to add fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();

            //Create the BodyPartFragment instance and displaying it using Fragment Manager
            BodyPartFragment headFragment = new BodyPartFragment();

            //Set the list of image id for the head fragment and set the position to second image in the list
            headFragment.setmImageId(AndroidImageAssets.getHeads());
            headFragment.setmListIndex(1);

            // Get the correct index to access in the array of head images from the intent
            // Set the default value to 0
            int headIndex = getIntent().getIntExtra("headIndex", 0);
            headFragment.setmListIndex(headIndex);

            //Fragment transaction
            fragmentManager.beginTransaction().add(R.id.head_container,headFragment).commit();

            //To display body
            BodyPartFragment bodyFragment = new BodyPartFragment();
            bodyFragment.setmImageId(AndroidImageAssets.getBodies());
            int bodyIndex = getIntent().getIntExtra("bodyIndex", 0);
            bodyFragment.setmListIndex(bodyIndex);
            fragmentManager.beginTransaction().add(R.id.body_container, bodyFragment).commit();

            //To display leg
            BodyPartFragment legFragment = new BodyPartFragment();
            legFragment.setmImageId(AndroidImageAssets.getLegs());
            int legIndex = getIntent().getIntExtra("legIndex", 0);
            legFragment.setmListIndex(legIndex);
            fragmentManager.beginTransaction().add(R.id.leg_container, legFragment).commit();
        }



    }
}