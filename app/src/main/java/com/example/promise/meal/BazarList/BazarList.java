package com.example.promise.meal.BazarList;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promise.meal.AboutUs;
import com.example.promise.meal.Home.MainActivity;
import com.example.promise.meal.ManageList;
import com.example.promise.meal.MessList;
import com.example.promise.meal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BazarList extends AppCompatActivity {
    private static final String TAG = "BazarList";
    private ListView mListview;
    private TextView mResultCost;
    private Button mAddValue;
    public int costResult = 0;
    private String mess_name,UID;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private FirebaseAuth mAuth;
    String date[];
    String cost[];
    String name[];
    int c=0;
    private ArrayList<ObjectofBazarList> dataofbazarlist = new ArrayList<>();

    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();
    private Button mSubmit;
    private EditText mName;
    private EditText mCost;
    private TextView mDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazar_list);
        Log.d(TAG, "onCreate: Started.");
        mAuth = FirebaseAuth.getInstance();

        Bundle receivedData= getIntent().getExtras();
        if(receivedData!=null)
        {
            mess_name=receivedData.getString("mess_name");
            UID=receivedData.getString("UID");
        }


        mSubmit = (Button)findViewById(R.id.submit);
        mName = (EditText)findViewById(R.id.all_user_name);
        mCost = (EditText)findViewById(R.id.cost);
        mDate = (TextView) findViewById(R.id.date_of);
        mListview = (ListView) findViewById(R.id.listview);
        mResultCost = (TextView) findViewById(R.id.costvalue);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        BazarList.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm-dd-yyy: " + month + "-" + day + "-" + year);

                String date = month + "-" + day + "-" + year;
                mDate.setText(date);
            }
        };

        ObjectofBazarList n1 = new ObjectofBazarList("Date","Name","Cost");

        dataofbazarlist.add(n1);




        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Object bazar = dataSnapshot.child("transaction").child(mess_name).getValue();
                   // System.out.println("Transaction " + bazar.toString());
                    //toastMessage(bazar.toString());

                    String parts[] =bazar.toString().split(",");
                    date = new String[parts.length/2];
                    cost = new String[parts.length/2];
                    name = new String[parts.length/2];
                    for(int i=0;i<parts.length;i++){
                        if(i%2==0) {
                            String partsAgain[]=parts[i].split("=");
                            date[c]=partsAgain[0];
                            if(c==0) date[c]=date[c].substring(1);
                            cost[c]=partsAgain[2];

                        }
                        else {
                            String partsAgain[]=parts[i].split("=");
                            name[c]=partsAgain[1].substring(0,partsAgain[1].length()-1);
                            if(i==parts.length-1) name[c]=name[c].substring(0,partsAgain[1].length()-2);
                            c++;
                        }
                    }
                    printNow();
                    printCost();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mName.getText().toString();
                String cost = mCost.getText().toString();
                String date = mDate.getText().toString();

                myRef.child("transaction").child(mess_name).child(date).child("buyer").setValue(name);
                myRef.child("transaction").child(mess_name).child(date).child("price").setValue(cost);

                Intent i = new Intent(BazarList.this,ManageList.class);
                startActivity(i);




            }
        });


    }
    public void printNow() {
        for(int i=0;i<date.length;i++){

            ObjectofBazarList x = new ObjectofBazarList(date[i],name[i],cost[i]);
            dataofbazarlist.add(x);

        }


        BazarListAdapter adapter = new BazarListAdapter(this,R.layout.adapter_bazarlist,dataofbazarlist);
        mListview.setAdapter(adapter);



    }

    public void printCost (){
        for(int i=0;i<cost.length;i++)
        System.out.println("elements of array cost: " +cost[i]);

        for(int i=0;i<cost.length;i++){
            costResult = costResult + Integer.parseInt(cost[i]);
        }


        mResultCost.setText(costResult+"/-");
    }


    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent i = new Intent(BazarList.this, AboutUs.class);
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

