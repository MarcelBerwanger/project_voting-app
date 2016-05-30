package com.example.user.prototyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SingIn extends AppCompatActivity {

    TextView name,age,username,paaswd;
    Button signIn;

    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (TextView)findViewById(R.id.etName);
        age = (TextView)findViewById(R.id.etAge);
        username = (TextView)findViewById(R.id.etUsername);
        paaswd = (TextView)findViewById(R.id.etPassword);
        signIn = (Button)findViewById(R.id.bSignIn);

        SignIn();

    }

    public void SignIn()
    {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((name.getText().toString().equals(""))||(age.getText().toString().equals(""))||(username.getText().toString().equals(""))||(paaswd.getText().toString().equals("")))
                {
                    Toast.makeText(SingIn.this,"Alle Felder Ausfüllen",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(SingIn.this,"Erfolgreich Registriert",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(SingIn.this,HomeScreen.class);
                    startActivity(i);
                }
            }
        });
    }

}
