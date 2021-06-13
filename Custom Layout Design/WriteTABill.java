package com.tanyadas.musicplayer.Work;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tanyadas.musicplayer.R;

public class WriteTABill extends AppCompatActivity {
private TextView viewButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_t_a_bill);
        viewButton = (TextView) findViewById(R.id.viewButton);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openUpdateTABill = new Intent(WriteTABill.this, UpdateTABill.class);
                startActivity(openUpdateTABill);
                finish();
            }
        });
    }
}