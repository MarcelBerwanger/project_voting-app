package com.example.user.prototyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Freund extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freunde);

        setContentView(R.layout.freunde);
        lv = (ListView)findViewById(R.id.freunesList);
        lv.setAdapter(new ArrayAdapter<String>(Freund.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Benutzer)));
        lv.setOnItemClickListener(Freund.this);

            }

    public void onItemClick(AdapterView<?> parent,View view, int position,long id )
    {
        TextView textView = (TextView) view;
        String Vallue = lv.getItemAtPosition(position).toString();
        String[] array = Vallue.split(" ");
        Toast.makeText(getApplicationContext(), array[1] + " wurde eine Freundschaftsanfrage gesendet", Toast.LENGTH_SHORT).show();

        Intent t = new Intent(Freund.this,HomeScreen.class);
        startActivity(t);

    }



}
