package com.example.promise.meal.CreateORJoinmess;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promise.meal.MessList;
import com.example.promise.meal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinMess extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String UID;
    String allMessNames[]=new String[50];
    EditText searchMessName;
    int flagFound=0;
    LinearLayout joinMessLayout;
    String messToBeSearched;
    private String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_mess);
        //getting the UID from the mess list
        Bundle receivedData= getIntent().getExtras();
        if(receivedData!=null)
        {
            UID=receivedData.getString("UID");
        }
        joinMessLayout= (LinearLayout) findViewById(R.id.join_mess);
        joinMessLayout.setBackgroundResource(R.drawable.gradient);
        Button searchMess = (Button) findViewById(R.id.search_mess_button);
        searchMess.setBackgroundResource(R.drawable.blackbutton);
        searchMessName= (EditText) findViewById(R.id.search_mess_field);
        System.out.println("Hey I am in onCreate");

    }

    public void onClickSearch(View view){

        System.out.println("Hey I am in onCLickSearch");
        messToBeSearched= searchMessName.getText().toString();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Object messNames = dataSnapshot.child("messNames").getValue();
                    System.out.println("MESSNAMES" + messNames.toString());
                    String parts[] =messNames.toString().split(",");
                    int x=0;
                    for(int i=0;i<parts.length;i++) {
                        String partsAgain[]=parts[i].split("=");
                        allMessNames[x]=partsAgain[0];
                        // removing the { sign of the first allMessNames and forward space

                        allMessNames[x]=allMessNames[x].substring(1);
                        if(allMessNames[x].equals(messToBeSearched)) {
                            flagFound=1;
                            pass=partsAgain[1];
                            if(pass.contains("}")){
                                pass=pass.substring(0,pass.length()-1);
                            }
                        }
                        x++;
                    }
                    for(int i=0;i<x;i++) System.out.println(allMessNames[i]);
                    if(flagFound==0) toastMessage("No mess by this name found");
                    else
                    {
                        toastMessage("Mess has been found");
                        giveMessPassword();
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

    public void giveMessPassword(){

        LinearLayout.LayoutParams nameDetails=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        nameDetails.setMargins(0,40,0,40);
        TextView name= new TextView(this);
        name.setText("Enter Code of "+searchMessName.getText().toString());
        name.setTextColor(Color.WHITE);
        joinMessLayout.addView(name,nameDetails);

        final EditText code= new EditText(this);
        code.setHint("Mess Code");
        code.setHintTextColor(Color.WHITE);
        code.setTextColor(Color.WHITE);
        joinMessLayout.addView(code);

        LinearLayout.LayoutParams buttonDetails=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonDetails.setMargins(0,40,0,0);
        Button join=new Button(this);
        join.setBackgroundResource(R.drawable.blackbutton);
        join.setTextColor(Color.WHITE);
        join.setText("Join");
        joinMessLayout.addView(join,buttonDetails);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("UID "+UID +" Password "+ pass+ " code field "+ code.getText());
                if(code.getText().toString().equals(pass)){
                    myRef.child("user").child(UID).child("messDetails").child(messToBeSearched).setValue("member");
                    myRef.child("mess").child(messToBeSearched).child(UID).child("06-11-17").child("breakfast").setValue(0);
                    myRef.child("mess").child(messToBeSearched).child(UID).child("06-11-17").child("dinner").setValue(0);
                    myRef.child("mess").child(messToBeSearched).child(UID).child("06-11-17").child("lunch").setValue(0);

                    launchMessListAgain();
                }
                else toastMessage("Mess Code does not match");
            }
        });

    }
    public void launchMessListAgain()
    {
        Intent i= new Intent(this, MessList.class);
        i.putExtra("key",UID);
        startActivity(i);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}