package com.example.promise.meal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promise.meal.CreateProfile.UserProfileActivity;
import com.example.promise.meal.Home.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Transaction;

public class MenuActivity extends AppCompatActivity {
    FirebaseAuth firebase;
    private String messege;
    private Button join;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        join = (Button)findViewById(R.id.join);
        firebase = FirebaseAuth.getInstance();
        messege = getIntent().getExtras().getString("UID");
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignIn(messege);
            }
        });
        //mProfile = (Button)findViewById(R.id.profile);



    }

    public void logout_Click(View V){
        firebase.signOut();
        finish();

    }

    public void onSignIn(String uid)
    {
        Intent i= new Intent(this, MessList.class);
        String message= uid;
        i.putExtra("key",message);
        startActivity(i);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent i = new Intent(MenuActivity.this, AboutUs.class);
                startActivity(i);
                return  true;
            case R.id.logout:
                firebase.signOut();
                finish();
                return true;
            case R.id.editprofile:
                Intent j = new Intent(MenuActivity.this,UserProfileActivity.class);
                startActivity(j);
                return true;
            default:
                Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
                return  true;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu3, menu);

        return true;
    }




}
