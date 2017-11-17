package com.example.promise.meal.CreateProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.promise.meal.AboutUs;
import com.example.promise.meal.CreateProfile.ProfileInfo;
import com.example.promise.meal.Home.MainActivity;
import com.example.promise.meal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "User_profile";

    private Button btnSubmit;
    private EditText mName,mEmail,mPhoneNum;
    private String userID;


    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef,myRef1,myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile_);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        mName = (EditText) findViewById(R.id.etName);
        mEmail = (EditText) findViewById(R.id.etEmail);
        mPhoneNum = (EditText) findViewById(R.id.etPhone);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef1 = mFirebaseDatabase.getReference();
        myRef2 = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: Submit pressed.");
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String phoneNum = mPhoneNum.getText().toString();

                Log.d(TAG, "onClick: Attempting to submit to database: \n" +
                        "name: " + name + "\n" +
                        "email: " + email + "\n" +
                        "phone number: " + phoneNum + "\n"
                );

                if(!name.equals("") && !email.equals("") && !phoneNum.equals("")){
                    ProfileInfo userInformation = new ProfileInfo(email,name,phoneNum);
                    myRef.child("users").child(userID).setValue(userInformation);
                    myRef1.child("user").child(userID).child("info").setValue(userInformation);
                    myRef2.child("user").child(userID).child("messDetails").child("abcd").setValue("");
                    toastMessage("New Information has been saved.");
                    mName.setText("");
                    mEmail.setText("");
                    mPhoneNum.setText("");


                }else{
                    toastMessage("Fill out all the fields");
                }
            }
        });
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent i = new Intent(UserProfileActivity.this, AboutUs.class);
                startActivity(i);
                return  true;
            default:
                Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
                return  true;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

}
