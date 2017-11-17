package com.example.promise.meal.CreateORJoinmess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.promise.meal.MessList;
import com.example.promise.meal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateMess extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String UID,mess,pass;
    EditText messName,messPass;
    String allMessNames[]=new String[200];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mess);
        //getting the UID from the mess list
        Bundle receivedData= getIntent().getExtras();
        if(receivedData!=null)
        {
            UID=receivedData.getString("UID");
        }
        LinearLayout createMess= (LinearLayout) findViewById(R.id.create_mess);
        createMess.setBackgroundResource(R.drawable.gradient);
        Button createButton=(Button) findViewById(R.id.mess_creation);
        createButton.setBackgroundResource(R.drawable.blackbutton);
        messName=(EditText) findViewById(R.id.create_mess_name);
        messPass=(EditText) findViewById(R.id.create_mess_pass);
    }


    public void onClickCreation(View view){
        //checking if mess with same name already exists
        mess=messName.getText().toString();
        pass=messPass.getText().toString();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Object messNames = dataSnapshot.child("messNames").getValue();
                    String parts[] =messNames.toString().split(",");
                    int x=0,flagFound=0;
                    for(int i=0;i<parts.length;i++) {
                        String partsAgain[]=parts[i].split("=");
                        allMessNames[x]=partsAgain[0];
                        // removing the { sign of the first allMessNames and forward space

                        allMessNames[x]=allMessNames[x].substring(1);
                        if(allMessNames[x].equals(mess)) {
                            flagFound=1;
                        }
                        x++;
                    }
                    for(int i=0;i<x;i++) System.out.println("CreateMess "+allMessNames[i]);
                    if(flagFound==1) toastMessage("Mess name already taken. Try another name.");
                    else
                    {
                        myRef.child("user").child(UID).child("messDetails").child(mess).setValue("member");
                        myRef.child("mess").child(mess).child(UID).child("06-11-17").child("breakfast").setValue(0);
                        myRef.child("mess").child(mess).child(UID).child("06-11-17").child("dinner").setValue(0);
                        myRef.child("mess").child(mess).child(UID).child("06-11-17").child("lunch").setValue(0);
                        myRef.child("messNames").child(mess).setValue(pass);

                        launchMessListAgain();
                    }

                }
                else System.out.print("Chill");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    public void launchMessListAgain()
    {
        myRef.child("transaction").child(mess).child("06-06-06").child("buyer").setValue("0");
        myRef.child("transaction").child(mess).child("06-06-06").child("price").setValue("0");
        Intent i= new Intent(this, MessList.class);
        i.putExtra("key",UID);
        startActivity(i);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}