package com.tanyadas.androidAvatar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


// This fragment displays all of the AndroidMe images in one large list
// The list appears as a grid of images
public class MasterListFragment extends Fragment
{
    // Mandatory empty constructor
    public MasterListFragment() {

    }
    /* To keep fragments modular and reusable,
    you cannot have them communicate directly with one another or directly tied to a host activity.
    An interface gives you a way to communicate but also stay modular because it can be implemented by any host activity
    -- this way the fragment is not tied directly to an activity.*/

    //Define a new interface OnImageClickListener that triggers a callback in host activity
    OnImageClickListener mcallBack;

    //OnImageClickListener interface, calls a method in host activity named onImageSelected
    public interface OnImageClickListener
    {
        void onImageSelected(int position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //This makes sure that host activity has implemented the callback interface
        //If not it throws exception
        try {
            mcallBack = (OnImageClickListener) context;

        }catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()  + "must Implement OnImageClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //Inflate ViewAvatar fragment layout
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        // Get a reference to the GridView in the fragment_master_list xml layout file
        GridView gridView = (GridView) rootView.findViewById(R.id.images_grid_view);

        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the image resources to display
        MasterListAdapter mAdapter = new MasterListAdapter(getContext(), AndroidImageAssets.getAll());

        // Set the adapter on the GridView
        gridView.setAdapter(mAdapter);

        //Set a click listener on the grid view and trigger the callback onImageSelected when an item is clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Trigger the callback method and pass in the position that was clicked
                mcallBack.onImageSelected(i);
            }
        });

        return rootView;

    }
}
