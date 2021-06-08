package com.tanyadas.androidAvatar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;



//This activity is responsible for displaying the master list of all images
public class AndroidMe extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    //Variables to store the value for the list index of selected images
    //Default index value will be zero
    private int headIndex, bodyIndex, legIndex;

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getSupportActionBar().setTitle("Select Android Parts");
        //getSupportActionBar().setIcon(R.drawable.logo);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_android_me);

        // Determine if you're creating a two-pane or single-pane display
        if (findViewById(R.id.android_me_linear_layout) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            // Change the GridView to space out the images more on tablet
            GridView gridView = (GridView) findViewById(R.id.images_grid_view);
            gridView.animate().start();
            //gridView.setNumColumns(2);

            // Getting rid of the "Next" button that appears on phones for launching a separate activity
          Button next = (Button) findViewById(R.id.next_button);
            next.setVisibility(View.GONE);


            //Create new body part fragments
            // Only create new fragments when there is no previously saved state
            if (savedInstanceState == null) {

                //Use a FragmentManager and transaction to add fragment to the screen
                FragmentManager fragmentManager = getSupportFragmentManager();

                //Create the BodyPartFragment instance and displaying it using Fragment Manager
                BodyPartFragment headFragment = new BodyPartFragment();

                //Set the list of image id for the head fragment and set the position to second image in the list
                headFragment.setmImageId(AndroidImageAssets.getHeads());
                //headFragment.setmListIndex(1);


                //Fragment transaction
                fragmentManager.beginTransaction().add(R.id.head_container, headFragment).commit();

                //Create new body fragment
                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setmImageId(AndroidImageAssets.getBodies());
                fragmentManager.beginTransaction().add(R.id.body_container, bodyFragment).commit();

                //Create new leg fragment
                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setmImageId(AndroidImageAssets.getLegs());
                fragmentManager.beginTransaction().add(R.id.leg_container, legFragment).commit();
            }


        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }


    }

    @Override
    public void onImageSelected(int position) {
        Button nextButton = (Button) findViewById(R.id.next_button);
       // Toast.makeText(this, "Position Clicked " + position, Toast.LENGTH_LONG).show();

        // Based on where a user has clicked, store the selected list index for the head, body, and leg BodyPartFragments
        // bodyPartNumber will be = 0 for the head fragment, 1 for the body, and 2 for the leg fragment
        // Dividing by 12 gives us these integer values because each list of images resources has a size of 12
        int bodyPartNumber = position / 12;

        // Store the correct list index no matter where in the image list has been clicked
        // This ensures that the index will always be a value between 0-11
        int listIndex = position - 12 * bodyPartNumber;

        // Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        if (mTwoPane) {

            //handle twoPane Case
            BodyPartFragment newFragment = new BodyPartFragment();
            // Set the currently displayed item for the correct body part fragment
            switch (bodyPartNumber) {
                case 0:    // A head image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setmImageId(AndroidImageAssets.getHeads());
                    newFragment.setmListIndex(listIndex);
                    // Replace the old head fragment with a new one
                    getSupportFragmentManager().beginTransaction().replace(R.id.head_container, newFragment).commit();
                    break;

                case 1: // A body image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setmImageId(AndroidImageAssets.getBodies());
                    newFragment.setmListIndex(listIndex);
                    // Replace the old body fragment with a new one
                    getSupportFragmentManager().beginTransaction().replace(R.id.body_container, newFragment).commit();
                    break;

                case 2: // A leg image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setmImageId(AndroidImageAssets.getLegs());
                    newFragment.setmListIndex(listIndex);
                    // Replace the old head fragment with a new one
                    getSupportFragmentManager().beginTransaction().replace(R.id.leg_container, newFragment).commit();
                    break;

                default:
                    break;

            }


        } else {
            // Set the currently displayed item for the correct body part fragment
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;

            }

            // Put this information in a Bundle and attach it to an Intent that will launch an ViewAvatar
            Bundle b = new Bundle();
            b.putInt("headIndex", headIndex);
            b.putInt("bodyIndex", bodyIndex);
            b.putInt("legIndex", legIndex);



            //Get a reference to the "Next" button and launch the intent when this button is clicked
            // The "Next" button launches ViewAvatar
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        //Put this information in a Bundle and attach it to an Intent that will launch an ViewAvatar
                        //Attach the Bundle to an intent

                        final Intent intent = new Intent(AndroidMe.this, ViewAvatar.class);
                        intent.putExtras(b);
                        Log.d("AndroidMe", "onClick: "+b);
                        startActivity(intent);


                }
            });
        }


    }


}