package com.example.mnote.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mnote.Activitiys.MainActivity;
import com.example.mnote.R;
import com.example.mnote.Utils.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {



    private ProgressDialog dialog;
    private DatabaseReference ref;
    private FirebaseAuth mauth;
    @BindView(R.id.login_email) EditText email;
    @BindView(R.id.login_pass) EditText pass;
    @BindView(R.id.login_button) Button login;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        dialog = new ProgressDialog(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();


            }
        });
    }


    private void createAccount() {
        String inputName = email.getText().toString();
//        String inputphone = phone.getText().toString();
        String inputPass = pass.getText().toString();

        if (TextUtils.isEmpty(inputName))
        {
            Toast.makeText(this, "Please fill name", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(inputPass)) {
            Toast.makeText(this, "Please fill Password", Toast.LENGTH_SHORT).show();
            return;
        } else {
            dialog.setTitle("Create Account");
            dialog.setMessage("Please wait while we are checking your credentials");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


        mauth.signInWithEmailAndPassword(inputName,inputPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Util.toast(LoginActivity.this,"Succesfully log In");
                }
                else
                {
                    Util.toast(LoginActivity.this,"error");
                }
            }
        });






        }
    }



}

