package com.tanyadas.wedmist.shareMemories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class UserProfile extends AppCompatActivity {
    private ExtendedFloatingActionButton logout, addPhoto, search,browse;
    private String userChoosenTask;
    private int requestCode;
    private int resultCode;
    private Intent data;
    private TextView userName;
    String TAG = "UserProfile";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_user_profile);
        logout = (ExtendedFloatingActionButton) findViewById(R.id.logout);
        addPhoto = (ExtendedFloatingActionButton) findViewById(R.id.add_photo);
        browse = (ExtendedFloatingActionButton) findViewById(R.id.visit_home);
        userName = (TextView) findViewById(R.id.userName);
        sharedPreferences = getApplicationContext().getSharedPreferences("SignIn",MODE_PRIVATE);

        String name = sharedPreferences.getString("NAME","");
        Log.d(TAG, "onCreate: NAME "+ name);
        userName.setText(name);
        //On Clicking Browse user will redirect to Home Page
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, Home.class);
                startActivity(intent);
            }
        });

        //On Clicking AddPhoto user can select
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        //On Clicking Logout user will logout from application
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLogout();
            }
        });

    }


    public  void selectImage() {
        final CharSequence[] items = {"Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result= Utility.checkPermission(UserProfile.this);

                if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    //Permission to access external storage
                    ActivityCompat.requestPermissions(UserProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
                    galleryIntent();

                } /*else if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    //Permission to access external storage
                    ActivityCompat.requestPermissions(UserProfile.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},23);
                        galleryIntent();

                }*/ else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
        super.onActivityResult(requestCode, resultCode, data);


        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    Uri selectedImageUri = data.getData();
                    // Get the path from the Uri
                    final String path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        File f = new File(path);
                        selectedImageUri = Uri.fromFile(f);
                        //Create the bundle
                        Bundle bundle = new Bundle();
                        //Add your data from getFactualResults method to bundle
                        bundle.putString("Image", String.valueOf(selectedImageUri));
                        //Add the bundle to the intent
                        Intent intent = new Intent(this, MakePost.class);
                        intent.putExtras(bundle);
                        Log.i("UserProfile", String.valueOf(bundle));
                        startActivity(intent);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }

    }


    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);//one is requestCode
    }
    private void cameraIntent()
    {
         Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         startActivityForResult(takePicture, 0);//zero is requestCode
    }

    //logout method
    private void setLogout() {

        if(sharedPreferences.contains("NAME"))
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("NAME");
            editor.putString("message", "Logged out Successfully");
            editor.putBoolean("isLoggedIn", false);
            Toast.makeText(UserProfile.this, "Logged out Successfully", Toast.LENGTH_SHORT).show();
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), SignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }


}