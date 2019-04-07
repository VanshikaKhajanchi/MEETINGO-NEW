package com.example.MEETINGO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ChattingDemo extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{


    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private String userid;
    private String usertest;
    TextView textview;
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    ArrayList<String> al1 = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_demo);

        usersList = findViewById(R.id.demousersList);
        noUsersText =findViewById(R.id.demonoUsersText);


        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        userid=firebaseAuth.getCurrentUser().getUid();

        ///firebase reference
        DatabaseReference database= FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref=database.child("users").child(userid);

        pd = new ProgressDialog(ChattingDemo.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://vanshika-ea13a.firebaseio.com/users.json";
        StringRequest request = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(ChattingDemo.this);
        rQueue.add(request);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                User.chatWith = al.get(position);
                startActivity(new Intent(ChattingDemo.this, UserChat.class));
            }
        });


        //including bottomnav bar
        BottomNavigationView navigation =findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

        //methods

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            String userrrr =usertest;

            while(i.hasNext()){
               key = i.next().toString();


                if(!key.equals(User.username))
                {

                    al.add(key);


                    FirebaseDatabase.getInstance().getReference().child("users").child(key)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // Get user information
                                    User user = dataSnapshot.getValue(User.class);

                                    try {
                                        String name = user.Name;
                                        al1.add(name);
                                        /*textview.setText(name);*/
                                        /*usertest=user.Name;*/



                                    }
                                    catch (Exception e)
                                    {
                                        Toast.makeText(ChattingDemo.this, "Detials not Available", Toast.LENGTH_LONG).show();

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {


                                }
                            });

                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }





    //bottom Navigation method
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.navigation_home:
                startActivity(new Intent(getApplicationContext(), CUsers.class));
                break;

            case R.id.navigation_webinar:
                startActivity(new Intent(getApplicationContext(), MyActivity.class));
                break;

            case R.id.navigation_account:
                startActivity(new Intent(getApplicationContext(), UserAccount.class));
                break;

        }

        return false;

    }


}

