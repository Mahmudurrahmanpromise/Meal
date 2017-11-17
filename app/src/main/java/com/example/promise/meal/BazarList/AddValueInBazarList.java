package com.example.promise.meal.BazarList;

import android.app.DatePickerDialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Calendar;

import com.example.promise.meal.Home.MainActivity;
import com.example.promise.meal.ManageList;
import com.example.promise.meal.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddValueInBazarList extends AppCompatActivity {
    private static final String TAG = "AddValueInBazarList";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();
    private String UserID;
    private Button mSubmit;
    private EditText mName;
    private EditText mCost;
    private TextView mDate;
    private String mess_name,UID;

    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_value_in_bazar_list);
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

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddValueInBazarList.this,
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



        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog(AddValueInBazarList.this);
                pd.setMessage("Loading...");
                pd.show();

                String name = mName.getText().toString();
                String cost = mCost.getText().toString();
                String date = mDate.getText().toString();

                myRef.child("transaction").child(mess_name).child(date).child("buyer").setValue(name);
                myRef.child("transaction").child(mess_name).child(date).child("price").setValue(cost);

                Intent i = new Intent(AddValueInBazarList.this,ManageList.class);
                startActivity(i);




            }
        });

    }

}
