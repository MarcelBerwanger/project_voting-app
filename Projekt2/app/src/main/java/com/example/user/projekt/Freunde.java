package com.example.user.projekt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by User on 10.04.2016.
 */
public class Freunde extends Activity implements AdapterView.OnItemClickListener
{
    ListView lv;

    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.freunde);
        lv = (ListView)findViewById(R.id.freunesList);
        lv.setAdapter(new ArrayAdapter<String>(Freunde.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Benutzer)));
        lv.setOnItemClickListener(Freunde.this);

    }

    public void onItemClick(AdapterView<?> parent,View view, int position,long id )
    {
        TextView textView = (TextView) view;
        String Vallue = lv.getItemAtPosition(position).toString();
        String[] array = Vallue.split(" ");
        Toast.makeText(getApplicationContext(), array[1]+ " wurde eine Freundschaftsanfrage gesendet", Toast.LENGTH_SHORT).show();

        Intent t = new Intent(Freunde.this,MainActivity.class);
        startActivity(t);

    }


}
