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

import java.util.ArrayList;

public class Restaurantes extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        lv = (ListView)findViewById(R.id.RestList);
        lv.setAdapter(new ArrayAdapter<String>(Restaurantes.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Resataurantes)));
        lv.setOnItemClickListener(this);

    }
    public void onItemClick(AdapterView<?> parent,View view, int position,long id )
    {

        String value = lv.getItemAtPosition(position).toString();
        ArrayList<String> array = new ArrayList<>();
        String[] arraysplit = value.split(" ");
        array.add(arraysplit[1]);
        Intent intent = new Intent(Restaurantes.this,BewertungsAction.class);
        intent.putStringArrayListExtra("name",array);
        startActivity(intent);

    }

}
