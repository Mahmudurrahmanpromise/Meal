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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promise.meal.AboutUs;
import com.example.promise.meal.MenuActivity;
import com.example.promise.meal.Messenger.Register;
import com.example.promise.meal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText user;
    private EditText pass;
    private TextView recover;
    private Button logout;
    private ProgressBar mBar;

    //private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button log=(Button)findViewById(R.id.log);
        Button reg = (Button) findViewById(R.id.reg);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        firebaseAuth = FirebaseAuth.getInstance();
        recover = (TextView)findViewById(R.id.recover);
        logout = (Button)findViewById(R.id.logout);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,RegistrationActivity.class);
                startActivity(i);

            }

        });

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,RecoveryActivity.class);
                startActivity(i);
            }
        });





    }




    public void Login_Click(View V){
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.show();

        if(user.getText().toString().equals("")){
            Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
        else {

            (firebaseAuth.signInWithEmailAndPassword(user.getText().toString(), pass.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                               // checkIfEmailVerified();


                                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                                pd.dismiss();
                                i.putExtra("UID", firebaseAuth.getCurrentUser().getUid());
                                user.getText().clear();
                                pass.getText().clear();
                                startActivity(i);

                            } else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });


        }

    }



    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {

            Toast.makeText(MainActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();


        }
        else{
            FirebaseAuth.getInstance().signOut();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent i = new Intent(MainActivity.this, AboutUs.class);
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