package com.example.promise.meal.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promise.meal.AboutUs;
import com.example.promise.meal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity  {

    Button reg;
    TextView email,pass;

    FirebaseAuth firebaseAuth;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        reg=(Button)findViewById(R.id.reg);
        email=(TextView)findViewById(R.id.all_user_email);
        pass=(TextView)findViewById(R.id.pass);

        firebaseAuth=FirebaseAuth.getInstance();
        mAuth= FirebaseAuth.getInstance();


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(RegistrationActivity.this);
                pd.setMessage("Loading...");
                pd.show();

                if(email.getText().toString().equals("")){
                    Toast.makeText(RegistrationActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                }
                else {

                    (firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                //sendEmailVerification();
                                Toast.makeText(RegistrationActivity.this, "SUCCESSFULL", Toast.LENGTH_SHORT).show();

                                Intent i= new Intent(RegistrationActivity.this,MainActivity.class);
                                pd.dismiss();
                                startActivity(i);
                            } else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }


    private void sendEmailVerification() {

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegistrationActivity.this, "Check your email for verification", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });







        }



    }




    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent i = new Intent(RegistrationActivity.this, AboutUs.class);
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
