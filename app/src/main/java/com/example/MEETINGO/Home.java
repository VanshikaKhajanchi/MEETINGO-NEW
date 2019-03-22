package com.example.MEETINGO;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Home extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mref;
    private String userid;

    //definig variables
    private Button signout;
    private TextView username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //getting variables
        username=findViewById(R.id.home_name);



        userid=firebaseAuth.getCurrentUser().getUid();



       /* mref = FirebaseDatabase.getInstance().getReference().child("users").child(userid);*/
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
                            Toast.makeText(Home.this, "Detials not Available", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });




        signout=findViewById(R.id.button);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), FirstScreen.class));
            }
        });


    }
}
