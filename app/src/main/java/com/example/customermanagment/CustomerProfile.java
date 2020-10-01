package com.example.customermanagment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerProfile extends AppCompatActivity {

    TextView cusun, cname, cemail, cusadd, usph;
    Button edit;
    ImageView back;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        cusun = (TextView) findViewById(R.id.uname);
        cname = (TextView) findViewById(R.id.cusname);
        cemail=(TextView)findViewById(R.id.cusemail);
        cusadd=(TextView)findViewById(R.id.usaddress);
        usph=(TextView)findViewById(R.id.cusphone);
        edit = (Button) findViewById(R.id.editprofile);
        back = (ImageView) findViewById(R.id.back);

        Intent li=getIntent();
        final String username= li.getStringExtra("username");
        viewCustomerDetails();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Edit_CustomerProfile.class);
                Toast.makeText(CustomerProfile.this, "Welcome to Edit Profile", Toast.LENGTH_SHORT).show();
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity3.class);
                startActivity(i);
            }
        });
    }


    private void viewCustomerDetails() {
        Intent i=getIntent();
        String username= i.getStringExtra("username");
        System.out.println(username);
        reff= FirebaseDatabase.getInstance().getReference().child("Member").child(username);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cusuname=snapshot.child("username").getValue(String.class).toString();
                String cusname=snapshot.child("name").getValue(String.class).toString();
                String email=snapshot.child("email").getValue(String.class).toString();
                String cusaddress=snapshot.child("address").getValue(String.class).toString();
                String  cusphone=snapshot.child("phone").getValue(String.class).toString();

                System.out.println(cusaddress+cusuname);

                cusun.setText(cusuname);
                cname.setText(cusname);
                cemail.setText(email);
                cusadd.setText(cusaddress);
                usph.setText(cusphone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}