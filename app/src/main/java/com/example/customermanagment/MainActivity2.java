package com.example.customermanagment;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {

    EditText name, email, address, phone, username, password, log;
    DatabaseReference reff;
    Button reg;
    private Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name = (EditText) findViewById(R.id.Name);
        email = (EditText) findViewById(R.id.Email);
        address = (EditText) findViewById(R.id.Address);
        phone = (EditText) findViewById(R.id.Phone);
        username = (EditText) findViewById(R.id.Username);
        password = (EditText) findViewById(R.id.Password);
        reg = (Button) findViewById(R.id.change);
        member = new Member();



        CheckingExistingUserName();

        TextView log = (TextView) findViewById(R.id.Login);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (TextUtils.isEmpty(name.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter the name", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(email.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter the email", Toast.LENGTH_SHORT).show();
                    }else if (TextUtils.isEmpty(address.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter the Address", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(phone.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter the Phone Number", Toast.LENGTH_SHORT).show();
                    }else if (!(phone.length()==10)){
                        Toast.makeText(getApplicationContext(), "invalid phone number", Toast.LENGTH_SHORT).show(); }
                    else if (TextUtils.isEmpty(username.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter the Username", Toast.LENGTH_SHORT).show();
                    }else if (!(username.length()>8)){
                            Toast.makeText(getApplicationContext(), "Username wants at lest 8 characters", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(password.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please Enter the Password", Toast.LENGTH_SHORT).show();
                    }else if ((password.length()<8)){
                        Toast.makeText(getApplicationContext(), "Password wants At least 8 characters", Toast.LENGTH_SHORT).show();
                    }


                    else {
                        member.setName(name.getText().toString().trim());
                        member.setEmail(email.getText().toString().trim());
                        member.setAddress(address.getText().toString().trim());
                        member.setPhone(phone.getText().toString().trim());
                        member.setUsername(username.getText().toString().trim());
                        member.setPassword(password.getText().toString().trim());
                        reff.child(username.getText().toString()).setValue(member);
                        Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                } catch (NumberFormatException e) {

                    Toast.makeText(getApplicationContext(), "Invalid Member", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    //Checking existing username
    private void CheckingExistingUserName() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String uname =username.getText().toString();
            if (uname.length()>8){
                reff = FirebaseDatabase.getInstance().getReference().child("Member");
                reff.orderByChild("username").equalTo(uname).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                              if (dataSnapshot.exists()){
                                  username.setError("Username Already Exist");


}
                    }

                    @Override
                    public void onCancelled( DatabaseError DatabaseError) {

                    }
                });
            }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


}