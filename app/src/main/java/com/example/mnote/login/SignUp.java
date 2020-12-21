package com.example.mnote.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mnote.Activitiys.MainActivity;
import com.example.mnote.R;
import com.example.mnote.Utils.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.ProgressDialog;

public class SignUp extends AppCompatActivity
{

    private ProgressDialog dialog;
    private FirebaseAuth mauth;



    @BindView(R.id.register_name) EditText name;
    @BindView(R.id.register_email) EditText email;
    @BindView(R.id.register_pass) EditText pass;
    @BindView(R.id.register) Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        mauth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();


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
//            dialog.setTitle("Create Account");
//            dialog.setMessage("Please wait while we are checking your credentials");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();


            mauth.createUserWithEmailAndPassword(inputEmail,inputPass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                startActivity(intent);
                                Util.toast(SignUp.this,"Succesfully registered");
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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