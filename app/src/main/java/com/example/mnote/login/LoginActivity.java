package com.example.mnote.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mnote.Activitiys.Constant;
import com.example.mnote.Activitiys.MainActivity;
import com.example.mnote.Activitiys.MySharedPreferences;
import com.example.mnote.R;
import com.example.mnote.Activitiys.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {




    private DatabaseReference ref;
    private FirebaseAuth mauth;
    @BindView(R.id.login_email) EditText email;
    @BindView(R.id.login_pass) EditText pass;
    @BindView(R.id.login_button) Button login;
    private MySharedPreferences preferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        preferences = MySharedPreferences.getInstance(this);

        if(preferences.getUserData(Constant.CURRENT_USER_ID).length()>0)
            startActivity(Util.goToMaineActivity(getApplicationContext(), MainActivity.class));


           mauth = FirebaseAuth.getInstance();

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();

                }
            });
        }


    private void login() {

        String inputemail = email.getText().toString();

        String inputPass = pass.getText().toString();

        if (TextUtils.isEmpty(inputemail))
        {
            Toast.makeText(this, "Please fill email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(inputPass)) {
            Toast.makeText(this, "Please fill Password", Toast.LENGTH_SHORT).show();
            return;
        } else {



        mauth.signInWithEmailAndPassword(inputemail,inputPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    String currUser = mauth.getCurrentUser().getUid();
                    preferences.setUserData(Constant.CURRENT_USER_ID , currUser );
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

