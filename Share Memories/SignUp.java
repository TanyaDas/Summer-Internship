package com.tanyadas.wedmist.shareMemories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {
    private TextInputEditText mUserName, mPassword, mEmailId;
    private TextInputLayout mUserName_textInputLayout, mPassword_textInputLayout, mEmailId_textInputLayout;
    private Button signInButton, registerNew;
    private DataBaseHelper dataBaseHelper;
    private UserModal userModal;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_sign_up);

        mUserName = (TextInputEditText) findViewById(R.id.userName_signUp);
        mPassword = (TextInputEditText) findViewById(R.id.password_signUp);
        mEmailId = (TextInputEditText) findViewById(R.id.email_signUp);
        signInButton = (Button) findViewById(R.id.signIn_from_signUp);
        registerNew = (Button) findViewById(R.id.signUp_button);
        mUserName_textInputLayout = (TextInputLayout) findViewById(R.id.userName_textInputLayout_signUp);
        mPassword_textInputLayout = (TextInputLayout) findViewById(R.id.password_textInputLayout_signUp);
        mEmailId_textInputLayout = (TextInputLayout) findViewById(R.id.email_textInputLayout_signUp);
        scrollView = (ScrollView) findViewById(R.id.scrollview_signUp);
        dataBaseHelper = new DataBaseHelper(this);
        userModal = new UserModal();

        //set OnClickListener on Create New Button
        registerNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() | !validateUserName() | !validatePassword()) {
                    return;
                }
                registerNewAccount();
            }
        });

        //Set a clickListener on that signIn Button(User already have an account)
        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                Intent redirectToSignIn = new Intent(SignUp.this, SignIn.class);
                startActivity(redirectToSignIn);
                finish();

            }
        });

    }


    private void  registerNewAccount()//sending data to database
    {
        String username = mUserName.getText().toString().trim();
        String email = mEmailId.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (!dataBaseHelper.checkUserEmail(email))
        {
            userModal.setName(username);
            userModal.setEmail(email);
            userModal.setPassword(password);
            dataBaseHelper.addUser(userModal);
            // Snack Bar to show success message that account created successfully
            Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            reset();
            Intent userProfile = new Intent(SignUp.this, SignIn.class);
            startActivity(userProfile);
            Toast.makeText(this, "Account Resgistered Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
            {
            // Snack Bar to show error message that email already exists
            Snackbar.make(scrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }

    }
    //clears field after successfully registration
    private void reset() {
        mUserName.getText().clear();
        mPassword.getText().clear();
        mEmailId.getText().clear();
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
    //check if email is valid or not and if field is empty
    private boolean validateEmail() {
        String emailInput = mEmailId_textInputLayout.getEditText().getText().toString().trim();
        // if the email input field is empty
        if (emailInput.isEmpty()) {
            mEmailId_textInputLayout.setError("Field can not be empty");
            return false;
        }
        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            mEmailId_textInputLayout.setError("Please enter a valid email address");
            return false;
        } else {
            mEmailId_textInputLayout.setError(null);
            return true;
        }
    }
}