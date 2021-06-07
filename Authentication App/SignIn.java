package com.tanyadas.loginUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignIn extends AppCompatActivity {
    private TextInputEditText mUserName, mPassword;
    private TextInputLayout mUserName_textInputLayout, mPassword_textInputLayout;
    private Button signInButton, createNewAccountButton;
    private ScrollView scrollView;
    DataBaseHelper dataBaseHelper;
    private UserModal userModal;
    private String TAG = "SignIn";
    SharedPreferences sharedPreferences;

    @Override
    protected void onStart() {
        super.onStart();
        String nm = sharedPreferences.getString("NAME",null);
        if(nm != null)
        {
            Intent openUserProfile = new Intent(SignIn.this, UserProfile.class);
            openUserProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(openUserProfile);
        }
        else
        {
            //do nothing
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mUserName = (TextInputEditText) findViewById(R.id.userName_signIn);
        mPassword = (TextInputEditText) findViewById(R.id.password_signIn);
        signInButton = (Button) findViewById(R.id.signIn_button);
        createNewAccountButton = (Button) findViewById(R.id.create_new_account);
        mUserName_textInputLayout = (TextInputLayout) findViewById(R.id.userName_textInputLayout_signIn);
        mPassword_textInputLayout = (TextInputLayout) findViewById(R.id.password_textInputLayout_signIn);
        scrollView = (ScrollView) findViewById(R.id.scrollview_signIn);
        dataBaseHelper = new DataBaseHelper(this);
        userModal = new UserModal();
        sharedPreferences = getSharedPreferences("SignIn", MODE_PRIVATE);
        //set OnClickListener on SignIn Button to User Profile Page
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUserName() | !validatePassword()) {
                    return;
                }
                // progressBar.setVisibility(View.VISIBLE);//once user enter their valid details process starts.
                userLogin();
            }
        });

        //Set a clickListener on that view
        createNewAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                Intent createNewAccountButton = new Intent(SignIn.this, SignUp.class);
                startActivity(createNewAccountButton);
                finish();
            }
        });

    }
    private void userLogin() {

        String username = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (dataBaseHelper.checkUser(username,password))
        {
            Bundle bundle = new Bundle();
            if(username != null)
            {
                bundle.putString("name",username);
            }

           SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("NAME",username);

            editor.putString("username", username);
            editor.putString("password", password);
            editor.putBoolean("isLoggedIn", true);
            editor.commit();
            //editor.apply();
            Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
            Intent userProfile = new Intent(getBaseContext(), UserProfile.class);

            userProfile.putExtras(bundle);
            Log.d(TAG, "userLogin: Username Passed"+ bundle);

            startActivity(userProfile);
            reset();
            finish();
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(scrollView, getString(R.string.error_invalid_username_or_password), Snackbar.LENGTH_LONG).show();
        }
    }

    //clears field after successfully login
    private void reset() {
        mUserName.getText().clear();
        mPassword.getText().clear();
    }

    //check if userName field is empty
    private boolean validateUserName() {
        String userNameInput = mUserName_textInputLayout.getEditText().getText().toString().trim();
        // if the email input field is empty
        if (userNameInput.isEmpty()) {
            mUserName_textInputLayout.setError("Field can not be empty");//Field can not be empty
            return false;
        } else {
            mUserName_textInputLayout.setError(null);
            return true;
        }
    }

    //checks if password field is empty
    private boolean validatePassword() {
        String passwordInput = mPassword_textInputLayout.getEditText().getText().toString().trim();
        // if password field is empty
        // it will display error message "Field can not be empty"
        if (passwordInput.isEmpty()) {
            mPassword_textInputLayout.setError("Field can not be empty");
            return false;
        } else {
            mPassword_textInputLayout.setError(null);
            return true;
        }
    }
}