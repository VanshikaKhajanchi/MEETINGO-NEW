package com.example.MEETINGO;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class UserChat extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);



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
