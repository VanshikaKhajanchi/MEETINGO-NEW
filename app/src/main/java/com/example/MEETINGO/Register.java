package com.example.MEETINGO;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity
{


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    //defining objects and variables;
    private EditText editTextEmail,editTextPassword,phone,name;
    private Button btn_signup;
    private String Name,Email,Phone,Userid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();



        //initializing views
        editTextEmail =findViewById(R.id.singup_emailid);
        editTextPassword =findViewById(R.id.signup_passwrod);
        phone=findViewById(R.id.signup_phone);
        name=findViewById(R.id.signup_name);

        btn_signup = (Button) findViewById(R.id.SignUp_button);




        /*progressDialog = new ProgressDialog(this);*/

        //attaching listener to button
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validform()==true)
                {
                    registerUser();
                }

            }
        });

    }

    private void adduserdb(FirebaseUser user) {
        /*converting to String*/
        Name=name.getText().toString().trim();
        Email=editTextEmail.getText().toString().trim();
        Phone=phone.getText().toString().trim();

        Userid=user.getUid();
        User us=new User(Name,Email,Userid,Phone);
        mDatabase.child("users").child(Userid).setValue(us);
    }




    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


             //creating a new user
             firebaseAuth.createUserWithEmailAndPassword(email, password)
                     .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {

                             //checking if success
                             if (task.isSuccessful()) {
                                 adduserdb(task.getResult().getUser());
                                 //display some message here
                                 Toast.makeText(Register.this, "Successfully registered", Toast.LENGTH_LONG).show();

                                 finish();
                                 //opening profile activity
                                 startActivity(new Intent(getApplicationContext(), Home.class));
                             } else {
                                 //display some message here
                                 editTextEmail.setError("Enter valid Email");
                                 Toast.makeText(Register.this, "Registration Error", Toast.LENGTH_LONG).show();
                             }

                         }
                     });



    }

    private boolean validform()
    {/*
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";*/

        if(name.getText().toString().trim().length()==0)
        {
            name.setError("Enter Name");
        }
        else  if(phone.getText().toString().trim().length()==0)
        {
            phone.setError("Enter Phone Number");
        }
        else  if(phone.getText().toString().trim().length()<=10)
        {
            phone.setError("Enter Valid Phone Number");
        }
        /*else  if(editTextEmail.getText().toString()== emailPattern)
        {
            editTextEmail.setError("Enter valid Email");
        }*/
        else if(editTextPassword.getText().toString().trim().length()==0)
        {
            editTextPassword.setError("Enter Password");
        }
        else if(editTextPassword.getText().toString().trim().length()<=8)
        {
            editTextPassword.setError("Password should be greater than 8 digits");
        }
        else{return true;}
        return false;
    }


}
