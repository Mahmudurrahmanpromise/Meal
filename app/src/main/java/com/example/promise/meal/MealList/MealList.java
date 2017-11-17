package com.example.promise.meal.MealList;

import android.app.DatePickerDialog;
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
import com.example.promise.meal.BazarList.AddValueInBazarList;
import com.example.promise.meal.ManageList;
import com.example.promise.meal.MessList;
import com.example.promise.meal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

public class MealList extends AppCompatActivity {
    private static final String TAG = "MealList";
    private FirebaseDatabase mFirebaseDatebase;
    private DatabaseReference mRef,mRef1;
    private FirebaseAuth mAuth;
    private String UserID,mess;
    private ListView mMealList;
    private EditText mBreakfast;
    private EditText mLunch;
    private EditText mDinner;
    private TextView mDate;
    private Button mSubmit;
    private int totalmeal=0;
    private TextView mMealValue;

    String splitDate[],splitBreakfast[],splitLunch[],splitDinner[];
    int c=0;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ArrayList<MealUserInformation> arrayMeal = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);
        mBreakfast = (EditText)findViewById(R.id.breakfast);
        mLunch = (EditText)findViewById(R.id.lunch);
        mDinner = (EditText)findViewById(R.id.dinner);
        mSubmit = (Button)findViewById(R.id.submit);
        mDate = (TextView)findViewById(R.id.date);
        mMealValue = (TextView)findViewById(R.id.mealvalue);

        Bundle receivedData= getIntent().getExtras();
        if(receivedData!=null)
        {
            mess=receivedData.getString("mess_name");
            Log.e("rcvmess",mess);
        }

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MealList.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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


        mMealList = (ListView) findViewById(R.id.meal_list);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatebase  = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        UserID = user.getUid();
        Log.d(TAG, "IDuser: id "+ UserID);
        Log.d(TAG, "mymess: id "+ mess);


        mRef = mFirebaseDatebase.getReference().child("mess").child(mess).child(UserID);
        mRef1 = mFirebaseDatebase.getReference();




        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: Submit pressed.");
                String breakfast = mBreakfast.getText().toString();
                String lunch = mLunch.getText().toString();
                String dinner = mDinner.getText().toString();
                String date = mDate.getText().toString();

                Log.d(TAG, "onClick: Attempting to submit to database: \n" +
                        "breakfast: " + breakfast + "\n" +
                        "lunch: " + lunch + "\n" +
                        "date: " + date + "\n" +
                        "dinner: " + dinner + "\n"
                );

                if(!breakfast.equals("") && !lunch.equals("") && !dinner.equals("") && !date.equals("")){
                    MealSelect userInformation = new MealSelect(breakfast,lunch,dinner);
                    mRef1.child("mess").child(mess).child(UserID).child(date).setValue(userInformation);
                    toastMessage("New Information has been saved.");
                    Intent i = new Intent(MealList.this, ManageList.class);
                    mBreakfast.setText("");
                    mLunch.setText("");
                    mDinner.setText("");
                    mDate.setText("Date");
                    startActivity(i);




                }else{
                    toastMessage("Fill out all the fields");
                }
            }
        });


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                another(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void another(DataSnapshot dataSnapshot) {

        if (dataSnapshot.getValue() != null) {


            String str = dataSnapshot.getValue().toString();
            Log.d("lastdata", str);

            String parts[] =str.split(",");
            splitDate = new String[parts.length/3];
            splitLunch = new String[parts.length/3];
            splitBreakfast = new String[parts.length/3];
            splitDinner = new String[parts.length/3];
            c=0;
            for(int i=0;i<parts.length;i++){
                if(i%3==0) {
                    String partsAgain[]=parts[i].split("=");
                    splitDate[c]=partsAgain[0];
                    splitDate[c]=splitDate[c].substring(1);
                    splitLunch[c]=partsAgain[2];

                }
                else if(i%3==1) {
                    String partsAgain[]=parts[i].split("=");
                    splitBreakfast[c]=partsAgain[1];
                }
                else {
                    String partsAgain[]=parts[i].split("=");
                    splitDinner[c]=partsAgain[1].substring(0,partsAgain[1].length()-1);
                    if(i==parts.length-1) splitDinner[c]=splitDinner[c].substring(0,partsAgain[1].length()-2);
                    c++;
                }
            }
            MealUserInformation x = new MealUserInformation("Breakfast","Lunch","Dinner","Date");
            arrayMeal.add(x);

            for(int i=0;i<splitDate.length;i++){
                System.out.println("abcddate: "+splitDate[i]+ " breakfast: "+splitBreakfast[i]+ " lunch: "+splitLunch[i]+ " dinner: "+splitDinner[i]);
                MealUserInformation user = new MealUserInformation(splitBreakfast[i],splitLunch[i],splitDinner[i],splitDate[i]);
                arrayMeal.add(user);

            }
            MealListAdapter adapter = new MealListAdapter(this,android.R.layout.simple_list_item_1,arrayMeal);
            mMealList.setAdapter(adapter);


            for(int i = 0;i<splitDate.length;i++){
                totalmeal = totalmeal + Integer.parseInt(splitBreakfast[i]) + Integer.parseInt(splitDinner[i]) + Integer.parseInt(splitLunch[i]);

            }
            mMealValue.setText(totalmeal+"");

        }

    }











    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent i = new Intent(MealList.this, AboutUs.class);
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
