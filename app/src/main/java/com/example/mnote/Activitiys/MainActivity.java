package com.example.mnote.Activitiys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mnote.Activitiys.HomePage;
import com.example.mnote.R;

public class MainActivity extends AppCompatActivity
{
    private Button make_note,update_note;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        make_note = (Button) findViewById(R.id.button);
        update_note = (Button) findViewById(R.id.button2);



        make_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                intent.putExtra("Note",Constant.ADD);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });


        update_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent intent1 = new Intent(getApplicationContext(),HomePage.class);
                intent1.putExtra("Note",Constant.UPDATE);
//                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
            }
        });

    }
}
