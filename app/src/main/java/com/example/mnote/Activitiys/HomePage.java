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
    String currentUserId;


   static int total =0;
     static int updatedBalance;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);





   mauth = FirebaseAuth.getInstance();
   currentUserId = mauth.getCurrentUser().getUid();


        name = (EditText) findViewById(R.id.editText);
        amount = (EditText) findViewById(R.id.editText2);
        add = (Button) findViewById(R.id.add);
        statement = (Button) findViewById(R.id.stmnt);


        Intent intent = getIntent();
        String value = intent.getStringExtra("Note");

        if(value.equals("add"))
        {
            add.setText("add");
        }
        else
        {
            add.setText("update");
        }


        ref = FirebaseDatabase.getInstance().getReference().child("Data").child(currentUserId);







statement.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view)
    {
        Intent in =new Intent(getApplicationContext(), StatementActivity.class);
//       in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        in.putExtra("uid",currentUserId);
        startActivity(in);


    }
});



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                validate(name,amount,add,statement);
            }
        });

    }

    private void validate(EditText name, EditText amount, Button add, Button statement)
    {
        String noteName = name.getText().toString();
        String amt = amount.getText().toString();



        if (TextUtils.isEmpty(noteName))
        {

            name.setError("please write the name");

        }
        else if (TextUtils.isEmpty(amt))
        {

            amount.setError("please write the amount");

        }
        else
        {


            String CurrentDate = Util.getDate();
             String  currentTime = Util.getTime();







            int  cAmt = Integer.parseInt(amt);



            addIntoDatabase(noteName,cAmt,CurrentDate,currentTime);



        }
    }

    private void addIntoDatabase(final String noteName, final int amt, final String thisDate,final String thisTime)
    {






        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {


                Intent intent = getIntent();
                String value = intent.getStringExtra("Note");

                System.out.println("hiiiiiiiiiiii man    kfknngnb"+value);

                if(value.equals("add"))
                {
                    total = total + amt;
                    updatedBalance = total;

                    HashMap<String,Object> map = new HashMap<>(0);
                    map.put(Constant.DATE,thisDate);
                    map.put(Constant.NOTE,noteName);
                    map.put(Constant.AMOUNT,amt+"");
                    map.put(Constant.TOTAL,total+"");
                    map.put(Constant.VALUE,value);

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

                else {





                    if (amt > updatedBalance) {
                        amount.setError("Ur balance is less");
                    }

                   else
                    {
                        updatedBalance = total - amt;
                    total = updatedBalance;


                    HashMap<String, Object> map = new HashMap<>(0);
                        map.put(Constant.DATE,thisDate);
                        map.put(Constant.NOTE,noteName);
                        map.put(Constant.AMOUNT,amt+"");
                        map.put(Constant.TOTAL,total+"");
                        map.put(Constant.VALUE,value);


                    ref.child(thisDate).child(thisTime).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(HomePage.this, "Note Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HomePage.this, "Network error", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
                }







            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
