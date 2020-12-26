package com.example.mnote.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mnote.Activitiys.HomePage;
import com.example.mnote.R;
import com.example.mnote.Utils.Constant;
import com.example.mnote.Utils.MySharedPreferences;
import com.example.mnote.Utils.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.ProgressDialog;
import android.widget.TextView;

public class SignUp extends AppCompatActivity
{

    private ProgressDialog dialog;
    private FirebaseAuth mauth;
    private MySharedPreferences preferences;


    @BindView(R.id.register_name) EditText name;
    @BindView(R.id.register_email) EditText email;
    @BindView(R.id.register_pass) EditText pass;
    @BindView(R.id.register) Button register;
    @BindView(R.id.Already_exists) TextView alreadyExistsTV;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        mauth = FirebaseAuth.getInstance();
        preferences =  MySharedPreferences.getInstance(this);

        if(preferences.getUserData(Constant.CURRENT_USER_ID).length()>0)
            startActivity(Util.goToMaineActivity(getApplicationContext(), MainActivity.class));


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        alreadyExistsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,LoginActivity.class));
            }
        });




    }


    private void createAccount() {
        String inputName = name.getText().toString();
        String inputEmail = email.getText().toString();
        String inputPass = pass.getText().toString();

        if (TextUtils.isEmpty(inputName))
        {
          name.setError("please provide name");
        }
        else if (TextUtils.isEmpty(inputEmail)) {
            email.setError("please provide mail Id");
        }
        else if (TextUtils.isEmpty(inputPass)) {
            pass.setError("please provide proper password");
        } else {

            mauth.createUserWithEmailAndPassword(inputEmail,inputPass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                String currUser = mauth.getCurrentUser().getUid();
                                preferences.setUserData(Constant.CURRENT_USER_ID , currUser ); // storing  currUser in  CURRENT_USER_ID //
                                startActivity(Util.goToMaineActivity(getApplicationContext(),MainActivity.class));
                               }
                            else
                            {
                                Util.toast(SignUp.this,"error");
                            }
                        }
                    });
        }
    }

}