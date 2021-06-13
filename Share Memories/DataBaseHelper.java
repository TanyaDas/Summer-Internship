package com.tanyadas.wedmist.shareMemories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper
{
     String TAG = "DATABASEHELPER";
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "shareMemories";

    //table name
    private static final String USER_DETAILS_TABLE = "user";
    private static final String ADD_PHOTO_TABLE = "photo";


    //Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_PHOTO_USER_ID = "photo_user_id";
    private static final String COLUMN_USERNAME_PHOTO_TABLE = "photo_username";
    private static final String COLUMN_USERNAME_CONTENT_PHOTO_TABLE = "photo_username_content";
    private static final String COLUMN_PHOTO = "image";
    private static final String COLUMN_HASTAG = "hastag";
    private static final String COLUMN_CAPTION = "caption";

    // create user table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + USER_DETAILS_TABLE + "("+ COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // create photo table sql query
    private String CREATE_PHOTO_TABLE = "CREATE TABLE " + ADD_PHOTO_TABLE + "("+ COLUMN_USERNAME_PHOTO_TABLE + " TEXT,"+ COLUMN_USERNAME_CONTENT_PHOTO_TABLE + " TEXT,"
            + COLUMN_PHOTO + " " + " BLOB NOT NULL,"+ COLUMN_CAPTION + " TEXT," + COLUMN_HASTAG + " TEXT" + ")";

    // drop user table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_DETAILS_TABLE;

    // drop photo table sql query
    private String DROP_PHOTO_TABLE = "DROP TABLE IF EXISTS " + ADD_PHOTO_TABLE;

    //Fetch data from photo table
    private String FETCH_DATA = "SELECT * FROM " + ADD_PHOTO_TABLE;


    public DataBaseHelper(@Nullable Context context)//Creating Database
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) //Creating Tables
    {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE); //Create User Table
        sqLiteDatabase.execSQL(CREATE_PHOTO_TABLE);//Create Photo Table
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_USER_TABLE);  //Drop User Table if exist
        sqLiteDatabase.execSQL(DROP_PHOTO_TABLE); //Drop Photo Table if exist
        // Create tables again
        onCreate(sqLiteDatabase);
    }

     // method to create new user account
    public void addUser(UserModal user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, user.getName());
        contentValues.put(COLUMN_USER_EMAIL, user.getEmail());
        contentValues.put(COLUMN_USER_PASSWORD, user.getPassword());
        db.insert(USER_DETAILS_TABLE, null, contentValues);   // Inserting Row in user table
    }


    // method to check if user's account already exists
    public boolean checkUser(String username, String password) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(USER_DETAILS_TABLE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

  //Method to check if email is already registered
    public boolean checkUserEmail(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};

        Cursor cursor = db.query(USER_DETAILS_TABLE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    // Insert the image, caption, hastag to the Database
    public void insertPost(AddPhotoModal addPhotoModal) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME_PHOTO_TABLE, addPhotoModal.getUsername());
        contentValues.put(COLUMN_USERNAME_CONTENT_PHOTO_TABLE, addPhotoModal.getUserNameContent());
        contentValues.put(COLUMN_PHOTO, addPhotoModal.getImage());
        contentValues.put(COLUMN_CAPTION, addPhotoModal.getCaption());
        contentValues.put(COLUMN_HASTAG, addPhotoModal.getHastag());

        db.insert(ADD_PHOTO_TABLE, null, contentValues);   // Inserting Row in photo table
        Log.d(TAG, "onClick: Uploaded" + contentValues);
    }

    public Cursor fetchData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null)
        {
            cursor = db.rawQuery(FETCH_DATA,null);
        }
        return cursor;
    }

}
