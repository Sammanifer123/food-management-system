package com.example.customermanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity3 extends AppCompatActivity {
   Button account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        account=(Button)findViewById(R.id.button);

        Intent li=getIntent();
       final String username= li.getStringExtra("username");

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),CustomerProfile.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });

    }
}