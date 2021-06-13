package com.tanyadas.wedmist.shareMemories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MakePost extends AppCompatActivity {
    private ImageView image;
    private MultiAutoCompleteTextView caption, hastag;
    private ExtendedFloatingActionButton save_Image;
    private DataBaseHelper dataBaseHelper;
    private AddPhotoModal addPhotoModal;
    private ScrollView scrollView;
    String TAG = "Make Post";
    SharedPreferences sharedPreferences;
    Bundle bundle;
    String imageFetched, name, captionSend, hastag_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        image = (ImageView) findViewById(R.id.photo_imageView);
        caption = (MultiAutoCompleteTextView) findViewById(R.id.caption_multiAutoCompleteTextView);
        hastag = (MultiAutoCompleteTextView) findViewById(R.id.hastag_multiAutoCompleteTextView);
        save_Image = (ExtendedFloatingActionButton) findViewById(R.id.upload);
        scrollView = (ScrollView) findViewById(R.id.scrollview_makePost);
        dataBaseHelper = new DataBaseHelper(this);
        addPhotoModal = new AddPhotoModal();
        sharedPreferences = getApplicationContext().getSharedPreferences("SignIn", MODE_PRIVATE);


        //upload image
        save_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData();
                addPhotoModal.setUsername(name);
                addPhotoModal.setUserNameContent(name);
                addPhotoModal.setImage(imageFetched);
                addPhotoModal.setCaption(captionSend);
                addPhotoModal.setHastag(hastag_send);

                dataBaseHelper.insertPost(addPhotoModal);
                Log.d(TAG, "onClick: Uploaded" + addPhotoModal);
                // Toast to show success message that Photo Uploaded successfully
                Toast.makeText(MakePost.this, R.string.success_photo_upload, Toast.LENGTH_SHORT).show();
                // reset();
                Intent addNewPost = new Intent(MakePost.this, Home.class);
                startActivity(addNewPost);
                Toast.makeText(MakePost.this, "Loading...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void fetchData() {
        bundle = getIntent().getExtras();
        //Extract the data…
        imageFetched = bundle.getString("Image");
        image.setImageURI(Uri.parse(imageFetched));

        name = sharedPreferences.getString("NAME", "");
        Log.d(TAG, "Make Post NAME:" + name);
        captionSend = caption.getText().toString().trim();
        hastag_send = hastag.getText().toString().trim();
        Log.d(TAG, "Make Post Caption:" + captionSend);
        Log.d(TAG, "Make Post Hastag:" + hastag_send);
    }

    //clears field after successfully registration
    private void reset() {
        caption.getText().clear();
        hastag.getText().clear();
        image.setImageURI(null);
    }
}