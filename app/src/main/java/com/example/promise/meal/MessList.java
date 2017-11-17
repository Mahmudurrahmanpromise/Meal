package com.example.promise.meal;



import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promise.meal.CreateORJoinmess.CreateMess;
import com.example.promise.meal.CreateORJoinmess.JoinMess;
import com.example.promise.meal.CreateProfile.UserProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessList extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String UID;
    private FirebaseAuth firebase;
    final String generalMessNames[]=new String[20];
    /*    String date[];
        String price[];
        String buyer[];
        int c=0;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_list);
        final LinearLayout messList = (LinearLayout) findViewById(R.id.mess_list);
        messList.setBackgroundResource(R.drawable.gradient);
        Button create_mess_btn = (Button) findViewById(R.id.create_mess_button);
        create_mess_btn.setBackgroundResource(R.drawable.blackbutton);
        Button join_mess_btn = (Button) findViewById(R.id.join_mess_button);
        join_mess_btn.setBackgroundResource(R.drawable.blackbutton);
        firebase = FirebaseAuth.getInstance();

        //getting the UID from the main activity
        Bundle receivedData= getIntent().getExtras();
        if(receivedData!=null)
        {
            UID=receivedData.getString("key");
        }

        //reading the mess names this user belongs in

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Object messNames = dataSnapshot.child("user").child(UID).child("messDetails").getValue();
                    //System.out.println("SUP" + messNames.toString());
                    //toastMessage(messNames.toString());
                    String parts[] =messNames.toString().split(",");
                    int x=0;
                    for(int i=0;i<parts.length;i++) {
                        String partsAgain[]=parts[i].split("=");

                            generalMessNames[x] = partsAgain[0];
                            // removing the { sign of the first GeneralMessName and others first space
                            generalMessNames[x] = generalMessNames[x].substring(1);
                            x++;

                    }

                    for(int i=0;i<x;i++) System.out.println(generalMessNames[i]);

                    createListView();
                }
                else System.out.print("Chill");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

/*
       // retrieving data for transaction

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Object bazar = dataSnapshot.child("transaction").child("CSEDU20").getValue();
                    System.out.println("Transaction " + bazar.toString());
                    toastMessage(bazar.toString());

                    String parts[] =bazar.toString().split(",");
                    date=new String[parts.length/2];
                    price=new String[parts.length/2];
                    buyer=new String[parts.length/2];
                    for(int i=0;i<parts.length;i++){
                        if(i%2==0) {
                            String partsAgain[]=parts[i].split("=");
                            date[c]=partsAgain[0];
                            if(c==0) date[c]=date[c].substring(1);
                            price[c]=partsAgain[2];

                        }
                        else {
                            String partsAgain[]=parts[i].split("=");
                            buyer[c]=partsAgain[1].substring(0,partsAgain[1].length()-1);
                            if(i==parts.length-1) buyer[c]=buyer[c].substring(0,partsAgain[1].length()-2);
                            c++;
                        }
                    }
                    printNow();

                }
                else System.out.print("Chill");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
*/

    }

    public void launchManageList(String selected_mess)
    {
        Intent i = new Intent(this, ManageList.class);
        i.putExtra("mess_name",selected_mess);
        Log.e("myitem", selected_mess);
        i.putExtra("UID",UID);

        startActivity(i);
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    //myRef.child("mess").child("CSEDU22").child("fahad").child("23-10-17").child("breakfast").setValue(1);
    public void onClickCreate(View view){
        Intent i = new Intent(this, CreateMess.class);
        i.putExtra("UID",UID);
        startActivity(i);
    }

    public void onClickJoin(View view){
        Intent i = new Intent(this, JoinMess.class);
        i.putExtra("UID",UID);
        startActivity(i);
    }

    public void createListView(){
        // Get reference of widgets from XML layout
        final ListView lv = (ListView) findViewById(R.id.my_mess_list_view);
        // Create a List from String Array elements
        final List<String> mess_list = new ArrayList<String>(Arrays.asList(generalMessNames));
        final List<String> list = new ArrayList<String>();
        for(String s : generalMessNames) {
            if(s != null && s.length() > 0) {
                list.add(s);
                System.out.println("Inside list: "+s);
            }
        }
        // Create an ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);
                // Generate ListView Item using TextView
                return view;
            }
        };
        // DataBind ListView with items from ArrayAdapter
        lv.setAdapter(arrayAdapter);
        // Set an item click listener for ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                launchManageList(selectedItem);
            }
        });
    }

/*
    //used for transaction
    public void printNow() {
        for(int i=0;i<date.length;i++) {
            System.out.println("Printing Transaction arrays: " + date[i]+" "+ buyer[i]+ " " + price[i]);
        }
    }*/

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent i = new Intent(MessList.this, AboutUs.class);
                startActivity(i);
                return  true;
            case R.id.logout:
                firebase.signOut();
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