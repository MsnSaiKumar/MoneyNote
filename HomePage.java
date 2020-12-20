package com.example.mnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class HomePage extends AppCompatActivity
{
    private EditText name,amount;
    private Button add,statement;
    private DatabaseReference ref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        name = (EditText) findViewById(R.id.editText);
        amount = (EditText) findViewById(R.id.editText2);
        add = (Button) findViewById(R.id.add);
        statement = (Button) findViewById(R.id.stmnt);

        ref = FirebaseDatabase.getInstance().getReference().child("Users");



statement.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view)
    {
        Intent in =new Intent(getApplicationContext(),StatementActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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


            Calendar calforDate = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
            final String CurrentDate = sdf.format(calforDate.getTime());

            Calendar calforTime = Calendar.getInstance();
            SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
            final String currentTime = time.format(calforDate.getTime());





            int  cAmt = Integer.parseInt(amt);
            int total =0;
            total = total + cAmt;

            addIntoDatabase(noteName,amt,CurrentDate,currentTime,total);



        }
    }

    private void addIntoDatabase(final String noteName, final String amt, final String thisDate,final String thisTime,final int total)
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
                   add.setText("Add");
                    HashMap<String,Object> map = new HashMap<>(0);
                    map.put("Date : " ,thisDate);
                    map.put("Note",noteName);
                    map.put("Amount",amt);
                    map.put("Total",total);
                    map.put("Value",value);



                    ref.child(thisTime).child(thisDate) .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                else
                {
                   add.setText("Update");

                    int updatedAmt = Integer.parseInt(amt);
                    int updatedBalance = total-updatedAmt;

                    HashMap<String,Object> map = new HashMap<>(0);
                    map.put("Date : " ,thisDate);
                    map.put("Note",noteName);
                    map.put("Amount",amt);
                    map.put("Total",updatedAmt);
                    map.put("Value",value);





                    ref.child(thisTime).child(thisDate).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(HomePage.this, "Note Updated", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(HomePage.this, "Network error", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }







            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
