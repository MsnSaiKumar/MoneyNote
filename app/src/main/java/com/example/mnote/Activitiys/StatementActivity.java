package com.example.mnote.Activitiys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mnote.Pojo.Users;
import com.example.mnote.R;
import com.example.mnote.Utils.Constant;
import com.example.mnote.Utils.MySharedPreferences;
import com.example.mnote.Utils.Util;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StatementActivity extends AppCompatActivity
{
    DatabaseReference refererence;
    ListView listview;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    MySharedPreferences preferences;
    String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);

        initializeData();


//        refererence.addChildEventListener(new ChildEventListener()
//        {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String dateValues = snapshot.getKey().toString();
//
//
//                System.out.println("..................."+dateValues);
//
//                refererence.child(dateValues).addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        String values = snapshot.getValue().toString();
//                        System.out.println("+?+++++++++++++++?////"+values);
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        String values = snapshot.getValue().toString();
//                        System.out.println("+?+++++++++++++++?////"+values);
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//            }
//
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName)     {
//                //System.out.println("Hiiiii @@@@@@@@@@@@@@@ %%%%%%%%");
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        refererence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot sna2 : snapshot.getChildren())
                {
                   for( DataSnapshot snap3 :  sna2.getChildren())
                    {
                        System.out.println("    2ndKEY  "+snap3.getKey()  +"   2ndVALUE   "+snap3.getValue());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initializeData() {
        preferences = MySharedPreferences.getInstance(this);
        currentUid = preferences.getUserData(Constant.CURRENT_USER_ID);
        refererence = FirebaseDatabase.getInstance().getReference("Data").child(currentUid);
        listview = (ListView) findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        listview.setAdapter(arrayAdapter);
    }
}
