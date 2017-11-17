package com.example.promise.meal.MemberInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;


import com.example.promise.meal.AboutUs;
import com.example.promise.meal.Home.MainActivity;
import com.example.promise.meal.MessList;
import com.example.promise.meal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllMemberInformation extends AppCompatActivity {
    private static final String TAG = "AllMemberInformation";
    private ListView mUserList;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private String UserID;
    private ArrayList<AllUserInformation> arrayUser = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_member_information);
        mUserList = (ListView)findViewById(R.id.user_list);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        UserID = user.getUid();
        mRef = mFirebaseDatabase.getReference().child("users");


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showData(DataSnapshot dataSnapshot) {

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            AllUserInformation info = new AllUserInformation();

            info.setEmail(ds.getValue(AllUserInformation.class).getEmail());
            info.setName(ds.getValue(AllUserInformation.class).getName());
            info.setPhone_num(ds.getValue(AllUserInformation.class).getPhone_num());

            arrayUser.add(info);
        }

        for(AllUserInformation info: arrayUser) {
            Log.d(TAG, "showData: name: " + info.getName());
            Log.d(TAG, "showData: email: " + info.getEmail());
            Log.d(TAG, "showData: phone_num: " + info.getPhone_num());
        }

        AdapterAllUser adapter= new AdapterAllUser(this,android.R.layout.simple_list_item_1,arrayUser);
        mUserList.setAdapter(adapter);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent i = new Intent(AllMemberInformation.this, AboutUs.class);
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