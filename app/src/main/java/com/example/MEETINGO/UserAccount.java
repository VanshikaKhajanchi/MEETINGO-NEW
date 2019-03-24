package com.example.MEETINGO;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccount extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private String userid;

    //definig variables
    private Button signout;
    private TextView username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //getting variables
        username=findViewById(R.id.acc_name);

        userid=firebaseAuth.getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("users").child(userid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information
                        User user = dataSnapshot.getValue(User.class);
                        /*String name = user.Name;*/
                        try {
                            username.setText(user.Name);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(UserAccount.this, "Detials not Available", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });


        signout=findViewById(R.id.acc_logout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), FirstScreen.class));
            }
        });



        //including bottomnav bar
        BottomNavigationView navigation =findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }





    //bottom Navigation method
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.navigation_home:
                startActivity(new Intent(getApplicationContext(), UserChat.class));
                break;

            case R.id.navigation_webinar:
                startActivity(new Intent(getApplicationContext(), Webinar.class));
                break;

            case R.id.navigation_account:
                startActivity(new Intent(getApplicationContext(), UserAccount.class));
                break;

        }

        return false;

    }


}

