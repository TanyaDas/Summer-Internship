package com.tanyadas.androidAvatar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class BodyPartFragment extends Fragment {

    private static final String TAG = "BodyPartFragment";

    //Final strings to store state information about the list of images and list index
    public static final String IMAGE_ID_LIST = "image_id";
    public static final String LIST_INDEX = "list_index";


    //Variables to store a list of image resources and index of image that this fragment displays
    private List<Integer> mImageId;
    private int mListIndex;

    // Setter methods for keeping track of the list images this fragment can display and which image in the list is currently being displayed
    public void setmImageId(List<Integer> mImageId) {
        this.mImageId = mImageId;
    }

    public void setmListIndex(int mListIndex) {
        this.mListIndex = mListIndex;
    }

    public BodyPartFragment() //Mandatory Constructor for instantiating the fragment
    {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)//Inflates the fragment layout and sets any image resoruces
    {
        //load the saved state(the list of images and list index)if there is one
        if (savedInstanceState != null) {
            mImageId = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);

        }
        //Inflate ViewAvatar fragment layout
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        //Get a reference to the ImageView in fragment layout
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        if (mImageId != null) {
            //Set the image resource to display
            imageView.setImageResource(mImageId.get(mListIndex));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Increment position as long as index remains <= size of image id list
                    if (mListIndex < mImageId.size() - 1) {
                        mListIndex++;
                    } else {
                        // The end of list has been reached, so return to beginning index
                        mListIndex = 0;
                    }
                    //Set the image resource to the new list item
                    imageView.setImageResource(mImageId.get(mListIndex));
                }
            });
        } else {
            //log a message saying that the imageId list is null
            Log.v(TAG, "This fragment has a null list if image id");
            //Set the image resource to display
            imageView.setImageResource(AndroidImageAssets.getHeads().get(0));
            imageView.setImageResource(AndroidImageAssets.getBodies().get(0));
            imageView.setImageResource(AndroidImageAssets.getLegs().get(0));

        }


        //Return rootView
        return rootView;

        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    //Save current state of this fragment
    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageId);
        currentState.putInt(LIST_INDEX, mListIndex);

    }

    public BodyPartFragment(int contentLayoutId) {
        super(contentLayoutId);
    }


}
