package com.example.promise.meal;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.promise.meal.BazarList.BazarList;
import com.example.promise.meal.Home.MainActivity;
import com.example.promise.meal.MealList.MealList;
import com.example.promise.meal.MemberInfo.AllMemberInformation;
import com.example.promise.meal.Messenger.Login;
import com.example.promise.meal.Messenger.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageList extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String mess_name,UID;
    private FirebaseDatabase mFirebasedatabase;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);
        LinearLayout manageList = (LinearLayout) findViewById(R.id.manage_list);
        manageList.setBackgroundResource(R.drawable.gradient);
        Button meal_list_btn = (Button) findViewById(R.id.meal_list_button);
        meal_list_btn.setBackgroundResource(R.drawable.blackbutton);
        Button bazaar_list_btn = (Button) findViewById(R.id.bazaar_list_button);
        bazaar_list_btn.setBackgroundResource(R.drawable.blackbutton);
        Button cost_list_btn = (Button) findViewById(R.id.cost_list_button);
        cost_list_btn.setBackgroundResource(R.drawable.blackbutton);
        Button member_info_btn = (Button) findViewById(R.id.member_info_button);
        member_info_btn.setBackgroundResource(R.drawable.blackbutton);
        Button messenger_btn = (Button) findViewById(R.id.messenger_button);
        messenger_btn.setBackgroundResource(R.drawable.blackbutton);
        Button leave_mess_btn = (Button) findViewById(R.id.leave_mess_button);
        leave_mess_btn.setBackgroundResource(R.drawable.blackbutton);

        mAuth = FirebaseAuth.getInstance();
        mFirebasedatabase = FirebaseDatabase.getInstance();
        mRef = mFirebasedatabase.getReference();

        Bundle receivedData= getIntent().getExtras();
        if(receivedData!=null)
        {
            mess_name=receivedData.getString("mess_name");
            UID=receivedData.getString("UID");
        }
        leave_mess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.child("user").child(UID).child("messDetails").child(mess_name).setValue(null);
                Intent i = new Intent(ManageList.this,MessList.class);
                startActivity(i);
            }
        });



        System.out.println("yolo "+ mess_name+" "+UID);


        bazaar_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ManageList.this,BazarList.class);
                i.putExtra("mess_name",mess_name);
                Log.e("myitem", mess_name);
                i.putExtra("UID",UID);
                startActivity(i);

            }
        });


        member_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ManageList.this, AllMemberInformation.class);
                startActivity(i);
            }
        });

        meal_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManageList.this, MealList.class);
                i.putExtra("mess_name",mess_name);
                Log.e("myitem", mess_name);
                i.putExtra("UID",UID);
                startActivity(i);
            }
        });
        messenger_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManageList.this, Login.class);
                startActivity(i);
            }
        });


    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent i = new Intent(ManageList.this, AboutUs.class);
                startActivity(i);
                return  true;
            case R.id.logout:
                mAuth.signOut();
                finish();
                return true;

            default:
                Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
                return  true;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);

        return true;
    }
}