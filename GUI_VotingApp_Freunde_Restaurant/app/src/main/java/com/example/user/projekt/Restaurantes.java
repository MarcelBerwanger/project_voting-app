package com.example.user.projekt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by User on 10.04.2016.
 */
public class Restaurantes extends Activity implements AdapterView.OnItemClickListener
{


    ListView lv;

    protected void onCreate(Bundle bundle)
    {

        super.onCreate(bundle);
        setContentView(R.layout.restaurant);

        lv = (ListView)findViewById(R.id.RestList);
        lv.setAdapter(new ArrayAdapter<String>(Restaurantes.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Resataurantes)));
        lv.setOnItemClickListener(this);


    }
    public void onItemClick(AdapterView<?> parent,View view, int position,long id )
    {
        TextView textView = (TextView) view;
        String value = lv.getItemAtPosition(position).toString();
        ArrayList<String> array = new ArrayList<>();
        String[] arraysplit = value.split(" ");
        array.add(arraysplit[1]);
        Intent intent = new Intent(Restaurantes.this,BewertungAction.class);
        intent.putStringArrayListExtra("name",array);
        startActivity(intent);

    }
}
