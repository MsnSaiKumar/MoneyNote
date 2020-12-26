package com.example.mnote.Activitiys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.mnote.R;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatementActivity extends AppCompatActivity
{
    @BindView(R.id.id_recycler_view)RecyclerView mRecyclerView;
    DetailsAdapter detailsAdapter;
    LinearLayoutManager layoutManager;
    List<Users> details_list = new ArrayList<>();

    DatabaseReference refererence;
    MySharedPreferences preferences;
    String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        ButterKnife.bind(this);

        initializeData();

        display_all_list();


    }

    private void display_all_list() {

        refererence.keepSynced(true);

        refererence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot sna2 : snapshot.getChildren())
                {
                    for( DataSnapshot snap3 :  sna2.getChildren())
                    {
                        HashMap<String , String > map = (HashMap<String, String>) snap3.getValue();
                        System.out.println("..........result_map "+ map);

                        Users temp_obj = new Users();

                        if(map.containsKey(Constant.DATE))      temp_obj.Date = map.get(Constant.DATE+"")+"";
                        if(map.containsKey(Constant.NOTE))      temp_obj.Note = map.get(Constant.NOTE+"")+"";
                        if(map.containsKey(Constant.AMOUNT))    temp_obj.Amount = map.get(Constant.AMOUNT+"")+"";
                        if(map.containsKey(Constant.ADDED))     temp_obj.added = map.get(Constant.ADDED+"")+"";
                        if(map.containsKey(Constant.DEDUCTED))  temp_obj.deducted = map.get(Constant.DEDUCTED)+"";
                        if(map.containsKey(Constant.TOTAL))     temp_obj.Total = map.get(Constant.TOTAL+"")+"";
                        if(map.containsKey(Constant.VALUE))     temp_obj.Value = map.get(Constant.VALUE+"")+"";

                        details_list.add(temp_obj);

                        detailsAdapter.notifyDataSetChanged();

                        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        mRecyclerView.setAdapter(detailsAdapter);

    }

    private void initializeData() {
        preferences = MySharedPreferences.getInstance(this);
        currentUid = preferences.getUserData(Constant.CURRENT_USER_ID);
        refererence = FirebaseDatabase.getInstance().getReference("Data").child(currentUid);

        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        detailsAdapter = new DetailsAdapter(details_list);


    }
}
