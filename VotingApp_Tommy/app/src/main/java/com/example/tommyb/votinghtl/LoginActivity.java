package com.example.tommyb.votinghtl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    final EditText etUsername = (EditText) findViewById(R.id.etUsername);
    final EditText etPassword = (EditText) findViewById(R.id.etPassword);
    final Button bLogin = (Button) findViewById(R.id.bLogin);
    final TextView tvRegister = (TextView) findViewById(R.id.tvRegister);
    final RadioButton rbRememberMe = (RadioButton) findViewById(R.id.rbRememberMe);
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    String eingabeText;
    String eingabePasswort;
    String suchenText;
    String suchenPasswort;
    String schlüssel = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = this.getSharedPreferences("prefsDatei", MODE_PRIVATE);
        prefsEditor = prefs.edit();

        tvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v){
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v){
                Intent loginIntent = new Intent(LoginActivity.this, UserAreaMainActivity.class);
                LoginActivity.this.startActivity(loginIntent);
                onClickView(rbRememberMe);
            }
        });
    }
    public void onClickView(View v) {

        switch (v.getId()) {
            case R.id.rbRememberMe: {
                if ((etUsername.getText().length() > 0) && (etPassword.getText().length() > 0)) {
                        eingabeText = etUsername.getText().toString();
                        eingabePasswort = etPassword.getText().toString();
                        prefsEditor.putString(schlüssel, eingabeText);
                        prefsEditor.putString(schlüssel, eingabePasswort);
                        prefsEditor.commit();
                    } else {
                        Toast.makeText(getApplicationContext(), "Geben Sie Ihre Login - Informationen ein!", Toast.LENGTH_SHORT).show();
                    }
                break;
            }
        }
    }
}
