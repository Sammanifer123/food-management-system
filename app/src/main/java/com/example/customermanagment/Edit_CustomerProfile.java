package com.example.customermanagment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_CustomerProfile extends AppCompatActivity {
    TextView Editusername,Editname,Editemail,Editaddress,Editphone;
    Button resetpassword,save, deleteAccount;
    ImageView back;
    ImageButton usernamepencil,namepencil,emailpencil,addresspencil,phonepencil;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__customer_profile);
        Editusername = (TextView) findViewById(R.id.edusername);
        Editname = (TextView) findViewById(R.id.edname);
        Editemail = (TextView) findViewById(R.id.edemail);
        Editaddress = (TextView) findViewById(R.id.edaddress);
        Editphone = (TextView) findViewById(R.id.edphone);

        usernamepencil = (ImageButton) findViewById(R.id.imageButton);
        namepencil = (ImageButton) findViewById(R.id.imageButton2);
        emailpencil = (ImageButton) findViewById(R.id.imageButton3);
        addresspencil = (ImageButton) findViewById(R.id.imageButton4);
        phonepencil = (ImageButton) findViewById(R.id.imageButton5);
        back = (ImageView) findViewById(R.id.back);
        resetpassword = (Button) findViewById(R.id.button6);
         deleteAccount=(Button)findViewById(R.id.button7);


        Intent li = getIntent();
        final String username = li.getStringExtra("username");
        resetpasswordButton();
        back();
        viewCustomerDetails();
        editUsername();
        editname();
        editemail();
        editaddress();
        editphone();
        deleteAccountbutton();

    }

    private void deleteAccountbutton() {
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=getIntent();
                String username=i.getStringExtra("username");
                reff = FirebaseDatabase.getInstance().getReference("Member").child(username);
                reff.removeValue();
                Intent reg = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(reg);
            }
        });
    }

    private void resetpasswordButton() {
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go  =getIntent();
                String username = go.getStringExtra("username");
                Intent goagain = new Intent(getApplicationContext(), Reset_Password.class);
                goagain.putExtra("username", username);
                startActivity(goagain);
                finish();

            }
        });
    }

    private void back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = getIntent();
                String username = go.getStringExtra("username");
                Intent goagain = new Intent(getApplicationContext(), CustomerProfile.class);
                goagain.putExtra("username", username);
                startActivity(goagain);
                finish();
            }
        });
    }


    private void editphone() {
        phonepencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=Editphone.getText().toString();
                updatephone(phone);
            }
        });
    }

    private void updatephone(String phone) {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.update_user_name_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText EditingUserName =(EditText)dialogView.findViewById(R.id.editText5);
        Button cancel =(Button) dialogView.findViewById(R.id.cancel);
        Button change=(Button)dialogView.findViewById(R.id.change);
        dialogBuilder.setTitle("Change Your Phone number");
        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=EditingUserName.getText().toString().trim();

                if (phone.isEmpty()){
                    EditingUserName.setError("change!!");

                }
                else
                {
                    if (phone.length()!=10) {
                        EditingUserName.setError("Invalid number");

                    }
                    else
                        {

                        updatephoneDialog(phone);
                            EditingUserName.setText(phone);
                        alertDialog.dismiss();
                    }
                }


            }


            //change in firebase updated username
            private boolean updatephoneDialog(final String phone) {
                Intent go=getIntent();
                final String username=go.getStringExtra("username");
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Member").child(username).child("phone");

               try {
                   databaseReference.setValue(phone);
                   Editphone.setText(phone);
               }catch (Exception e){
                   Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
               }
                System.out.println(phone);

                databaseReference.orderByChild("username").equalTo(username).addChildEventListener(new ChildEventListener() {


                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                        if (dataSnapshot.exists()){
                            dataSnapshot.getRef().child("username").setValue(phone);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                return true;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }
    private void editaddress() {
        addresspencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address=Editaddress.getText().toString();
                updateaddress(address);
            }
        });
    }

    private void updateaddress(String address) {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.update_user_name_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText EditingUserName =(EditText)dialogView.findViewById(R.id.editText5);
        Button cancel =(Button) dialogView.findViewById(R.id.cancel);
        Button change=(Button)dialogView.findViewById(R.id.change);
        dialogBuilder.setTitle("Change Your Current Address");
        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address=EditingUserName.getText().toString().trim();

                if (address.isEmpty()){
                    EditingUserName.setError("change!!");

                }
                else {

                        updateaddressDialog(address);
                    EditingUserName.setText(address);
                        alertDialog.dismiss();
                    }


                }


            //change in firebase updated username
            private boolean updateaddressDialog(final String address) {
                Intent go=getIntent();
                final String username=go.getStringExtra("username");
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Member").child(username).child("address");
              try {
                   databaseReference.setValue(address);
                   Editaddress.setText(address);
                 }
              catch (Exception e){
                  Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                    }


                System.out.println(address);

                databaseReference.orderByChild("username").equalTo(username).addChildEventListener(new ChildEventListener() {


                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                        if (dataSnapshot.exists()){
                            dataSnapshot.getRef().child("username").setValue(address);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                return true;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    private void editemail() {
        emailpencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =Editemail.getText().toString();
                updateemail(email);

            }
        });
    }


    //update email

    private void updateemail(final String email) {
        //get dialog box to change the user name
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.update_user_name_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText EditingUserName =(EditText)dialogView.findViewById(R.id.editText5);
        Button cancel =(Button) dialogView.findViewById(R.id.cancel);
        Button change=(Button)dialogView.findViewById(R.id.change);
        dialogBuilder.setTitle("Change Your Email Address");
        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        change.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email=EditingUserName.getText().toString().trim();
                String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (email.isEmpty()){
                        EditingUserName.setError("change!!");

                }
                else {
                    if((!email.matches(emailpattern))){
                        EditingUserName.setError("Invalid Email");
                    }
                    else {
                        updateemailDialog(email);
                        EditingUserName.setText(email);
                        alertDialog.dismiss();
                    }


                }
            }

            //change in firebase updated username
            private boolean updateemailDialog(final String email) {
                Intent go=getIntent();
                final String username=go.getStringExtra("username");
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Member").child(username).child("email");
                System.out.println(email);
                try {
                    databaseReference.setValue(email);
                    Editemail.setText(email);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }

                databaseReference.orderByChild("username").equalTo(username).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                        if (dataSnapshot.exists()){
                            dataSnapshot.getRef().child("username").setValue(email);
                        } }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    }
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    }
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                return true;}
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    private void editname() {
        namepencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nam =Editname.getText().toString();
                updatename(nam);
            }
        });
    }
    //update username
    private void updatename(  String nam) {
        //get dialog box to change the user name
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.update_user_name_dialog,null);
        dialogBuilder.setView(dialogView);
        final EditText EditingUserName =(EditText)dialogView.findViewById(R.id.editText5);
        Button cancel =(Button) dialogView.findViewById(R.id.cancel);
        Button change=(Button)dialogView.findViewById(R.id.change);
        dialogBuilder.setTitle("Change Your Name");
        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nam=EditingUserName.getText().toString().trim();
                if (nam.isEmpty()){
                    // update fields are empty
                    EditingUserName.setError("change!!");
                }
                else { updatenameDialog(nam);
                    EditingUserName.setText(nam);
                        alertDialog.dismiss();
                }
            }
            //change in firebase updated name
            private boolean updatenameDialog(final String nam) {
                Intent go = getIntent();
                final String username = go.getStringExtra("username");
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Member").child(username).child("name");
               try {
                   databaseReference.setValue(nam);
                   Editname.setText(nam);
               }catch (Exception e){
                   Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
               }
                System.out.println(nam);

                databaseReference.orderByChild("username").equalTo(username).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                        if (dataSnapshot.exists()) {
                            dataSnapshot.getRef().child("username").setValue(nam);
                        }
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    }
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    }
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                return true; }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    //update username
    private void editUsername() {
        usernamepencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un =Editusername.getText().toString();
                updateusername(un);

            }
        });
    }
    private void updateusername( final String un) {
        //get dialog box to change the user name
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.update_user_name_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText EditingUserName =(EditText)dialogView.findViewById(R.id.editText5);
        Button cancel =(Button) dialogView.findViewById(R.id.cancel);
        Button change=(Button)dialogView.findViewById(R.id.change);
        dialogBuilder.setTitle("Change your Username");
        final AlertDialog alertDialog=dialogBuilder.create();

        alertDialog.show();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un=EditingUserName.getText().toString().trim();
                // checking the fields in alertbox

                if (un.isEmpty()){
                    EditingUserName.setError("change!!");

                }else {
                        updateusernameDialog(un);
                    EditingUserName.setText(un);
                    alertDialog.dismiss();
                }
            }

            //change in firebase updated username
            private boolean updateusernameDialog(final String un) {
                Intent go=getIntent();
                final String username=go.getStringExtra("username");
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Member").child(username).child("username");

                try {
                    databaseReference.setValue(un);
                    Editusername.setText(un);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                }

                System.out.println(username);

                databaseReference.orderByChild("username").equalTo(username).addChildEventListener(new ChildEventListener() {


                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                        if (dataSnapshot.exists()){
                            dataSnapshot.getRef().child("username").setValue(username); }
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    }
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    }
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                return true;
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    //display user details
    private void viewCustomerDetails(){
        Intent i=getIntent();
        String username=i.getStringExtra("username");
        System.out.println(username);
        reff= FirebaseDatabase.getInstance().getReference().child("Member").child(username);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cusuname=snapshot.child("username").getValue(String.class).toString();
                String cusname=snapshot.child("name").getValue(String.class).toString();
                String email=snapshot.child("email").getValue(String.class).toString();
                String cusaddress=snapshot.child("address").getValue(String.class).toString();
                String cusphone=snapshot.child("phone").getValue(String.class).toString();

                //set the values from the database
                System.out.println(cusaddress+cusuname);
                Editusername.setText(cusuname);
                Editname.setText(cusname);
                Editemail.setText(email);
                Editaddress.setText(cusaddress);
                Editphone.setText(cusphone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}