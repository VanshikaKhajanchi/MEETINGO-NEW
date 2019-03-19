package com.example.MEETINGO;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FirstScreen extends AppCompatActivity {

        private Button login,signup;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        /*finding Buttons*/
        login=findViewById(R.id.login_btn);
        signup=findViewById(R.id.fs_signup_btn);

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), Home.class));
            Toast.makeText(FirstScreen.this,"Current USer DETECTED", Toast.LENGTH_LONG).show();
        }




        /*open login activity if button clicked*/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_login_activity();
            }
        });


        /*open Signup activity if button clicked*/

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_signup_activity();
            }
        });




    }

    private void open_signup_activity() {
        Intent intent=new Intent(this,Register.class);
        startActivity(intent);
    }

    private void open_login_activity() {
        Intent intent=new Intent(this,Login.class);
        startActivity(intent);
    }
}
