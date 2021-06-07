package com.tanyadas.loginUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.w3c.dom.Text;

public class UserProfile extends AppCompatActivity {
    private TextView username;
    private ExtendedFloatingActionButton logout;
    String TAG = "UserProfile";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        username = (TextView) findViewById(R.id.username);
        logout = (ExtendedFloatingActionButton) findViewById(R.id.logout);
        sharedPreferences = getApplicationContext().getSharedPreferences("SignIn",MODE_PRIVATE);

        String name = sharedPreferences.getString("NAME","");
        Log.d(TAG, "onCreate: NAME "+ name);
        username.setText(name);

        //On Clicking Logout user will logout from application
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLogout();
            }
        });

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
        /*SharedPreferences.Editor editor;
        editor = getSharedPreferences("SignIn", MODE_PRIVATE).edit();
        editor.putString("password", "");
        editor.putString("username", "");
        editor.putBoolean("isLoggedIn", false);
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        Toast.makeText(UserProfile.this, "Logged out Successfully", Toast.LENGTH_SHORT).show();
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();*/
    }
}