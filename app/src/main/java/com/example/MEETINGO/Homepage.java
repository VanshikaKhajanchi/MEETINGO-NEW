package com.example.MEETINGO;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
//import com.pubnub.api.Callback;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();


        //loading the default fragment
//        loadFragment(new AccountFragment());

        BottomNavigationView navigation =findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

    }



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
