package com.example.aaliyakhan.locationservices;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText user,pass;
    Button login;
    String userstr,passstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        login.setOnClickListener(this);
    }
    void init(){
        user=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        login=findViewById(R.id.login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.login:
                userstr=user.getText().toString().toLowerCase().trim();
                passstr=pass.getText().toString().toLowerCase().trim();
                if(userstr.equals("user")&&passstr.equals("password")) {
                    startActivity(new Intent(LoginActivity.this, NavActivity.class));
                }
                else
                {
                    Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
