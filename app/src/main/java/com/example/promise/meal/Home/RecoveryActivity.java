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
import android.widget.Toast;

import com.example.promise.meal.AboutUs;
import com.example.promise.meal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecoveryActivity extends AppCompatActivity {

    Button recoverPass;
    EditText recEmail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);
        recoverPass = (Button) findViewById(R.id.recoverPass);
        recEmail = (EditText) findViewById(R.id.recEmail);
        firebaseAuth = FirebaseAuth.getInstance();

    }


    public void recoverPass_Click(View V) {
        final ProgressDialog pd = new ProgressDialog(RecoveryActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        if(recEmail.getText().toString().equals("")){
            Toast.makeText(RecoveryActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
        } else {
            (FirebaseAuth.getInstance().sendPasswordResetEmail(recEmail.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RecoveryActivity.this, "Email has been sent", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(RecoveryActivity.this, MainActivity.class);
                        pd.dismiss();
                        startActivity(i);

                    } else {
                        Log.e("ERROR", task.getException().toString());
                        Toast.makeText(RecoveryActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }




    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent i = new Intent(RecoveryActivity.this, AboutUs.class);
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
