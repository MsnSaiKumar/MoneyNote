package com.example.mnote.Activitiys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mnote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HomePage extends AppCompatActivity
{
    private EditText name,amount;
    private Button add,statement;
    private DatabaseReference ref;
    private FirebaseAuth mauth;
    String currentUserId , status="";
    MySharedPreferences preferences;
    long total=0l;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mauth = FirebaseAuth.getInstance();
        currentUserId = mauth.getCurrentUser().getUid();
        preferences = MySharedPreferences.getInstance(this);


        name = (EditText) findViewById(R.id.editText);
        amount = (EditText) findViewById(R.id.editText2);
        add = (Button) findViewById(R.id.add);
        statement = (Button) findViewById(R.id.stmnt);


        Intent intent = getIntent();
        String value = intent.getStringExtra("Note");

        if(value.equals(Constant.UPDATE))  status = Constant.UPDATE;
        else if(value.equals(Constant.ADD))  status = Constant.ADD;

        ref = FirebaseDatabase.getInstance().getReference().child("Data").child(currentUserId);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                validate(name,amount,add,statement);
            }
        });

        statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent in =new Intent(getApplicationContext(), StatementActivity.class);
                in.putExtra("uid",currentUserId);
                startActivity(in);
            }
        });

    }

    private void validate(EditText name, EditText amount, Button add, Button statement)
    {
        String noteName = name.getText().toString();
        String amt = amount.getText().toString();
        long entered_amount = Long.parseLong(amt);

        if (TextUtils.isEmpty(noteName))
        {
            name.setError("please write the name");
            return;
        }
        else if (TextUtils.isEmpty(amt))
        {
            amount.setError("please write the amount");
            return;
        }
        else
        {
            String CurrentDate = Util.getDate();
            String  currentTime = Util.getTime();
            addIntoDatabase(noteName,entered_amount,CurrentDate,currentTime);
        }
    }

    private void addIntoDatabase(final String noteName, final long entered_amt, final String thisDate,final String thisTime)
    {

        HashMap<String,Object> map = new HashMap<>();
        map.put(Constant.DATE,thisDate+"");
        map.put(Constant.NOTE,noteName+"");
        map.put(Constant.AMOUNT,entered_amt+"");

        if(preferences.getUserData(Constant.TOTAL).length()>0)
            total = Long.parseLong(preferences.getUserData(Constant.TOTAL));

        if(status.equals(Constant.ADD)){
            total = total + entered_amt;
            map.put(Constant.TOTAL , total+"");
            map.put(Constant.VALUE ,Constant.ADD);
        }
        else if(status.equals(Constant.UPDATE)){

            if(entered_amt > total){
                 Util.toast(this , "FAILED!!   deducting amount is high");
                 return;
            }
            total = total - entered_amt;

            map.put(Constant.TOTAL , total+"");
            map.put(Constant.VALUE ,Constant.DEDUCTED);
        }

        preferences.setUserData(Constant.TOTAL , total+"");

        ref.child(thisDate).child(thisTime) .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(HomePage.this, "Note added", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(HomePage.this, "Network error", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
