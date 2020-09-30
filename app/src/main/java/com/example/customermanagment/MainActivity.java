package com.example.customermanagment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText pw, unam, reg;
    Button sign;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login();
        register();

    }

    public void register() {
        TextView reg = (TextView) findViewById(R.id.Register);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(go);
                //finish();
            }
        });

    }

    public void Login() {
        unam =(EditText)findViewById(R.id.Username);
        pw=(EditText)findViewById(R.id.Password);
        sign=(Button)findViewById(R.id.login);
        sign.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reff= FirebaseDatabase.getInstance().getReference().child("Member");
                reff.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String PassWord=dataSnapshot.child("password").getValue(String.class);
                        String USerName=dataSnapshot.child("username").getValue(String.class);
                        String snapshotID = dataSnapshot.getKey();

                        String pass=pw.getText().toString().trim();
                        String unaMe=unam.getText().toString().trim();
                        System.out.println(PassWord + USerName);
                        try {
                            if ((USerName.equals(unaMe)) && (PassWord.equals(pass))) {
                                Toast.makeText(MainActivity.this, "Login is successfully", Toast.LENGTH_SHORT).show();
                                Intent reg = new Intent(getApplicationContext(), MainActivity3.class);
                                reg.putExtra("username", snapshotID);
                                startActivity(reg);
                                finish();
                            } if ((USerName.equals(unaMe)) && (!PassWord.equals(pass))) {
                                Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                            } if ((!USerName.equals(unaMe)) && (PassWord.equals(pass))) {
                                Toast.makeText(MainActivity.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception e){
                            System.out.println("Full case");
                        }

                    }


                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}