package com.example.customermanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reset_Password extends AppCompatActivity {

    EditText currentpass, newpass, conpass;
    DatabaseReference reff;
    Button save;
    ImageView back;

    private String currentPassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);

        currentpass = (EditText) findViewById(R.id.currentPassword);
        newpass = (EditText) findViewById(R.id.newPassword);
        conpass = (EditText) findViewById(R.id.conPassword);

        reff = FirebaseDatabase.getInstance().getReference("Member");
        save = (Button) findViewById(R.id.save);
        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = getIntent();
                String username = go.getStringExtra("username");
                Intent goagain = new Intent(getApplicationContext(), Edit_CustomerProfile.class);
                goagain.putExtra("username", username);
                startActivity(goagain);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = getIntent().getStringExtra("username");

                String currentPassword = currentpass.getText().toString();
                String newPassword = newpass.getText().toString();
                String confirmPassword = conpass.getText().toString();

                // Check for empty fields
                if (currentPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")){
                    Toast.makeText(Reset_Password.this,"All fields are required",Toast.LENGTH_SHORT).show();
                }

                // Check whether the given password is valid
                if(getCurrentPassword(username) == null || !getCurrentPassword(username).equals(currentPassword)) {
                    Toast.makeText(Reset_Password.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check the given passwords are equal
                if(newPassword.equals(confirmPassword)) {
                    // Check the password is valid
                    if(isValidPassword(newPassword)) {
                        // Update the password
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference();
                        ref.child("Member").child(username).child("password").setValue(newPassword);

                        Toast.makeText(Reset_Password.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(getApplicationContext(),Edit_CustomerProfile.class);
//                        startActivity(i);
                        Reset_Password.super.onBackPressed();

                    } else {
                        Toast.makeText(Reset_Password.this, "Password should contain blah blah...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Reset_Password.this, "Not matched", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public String getCurrentPassword(String userName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("Member").child(userName);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentpassword = snapshot.child("password").getValue(String.class);
                setCurrentPassword(currentpassword);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Password: " + error.getCode());
            }
        });
       return currentPassword;
    }

    private void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    private boolean isValidPassword(String password) {
        String patternString = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}

