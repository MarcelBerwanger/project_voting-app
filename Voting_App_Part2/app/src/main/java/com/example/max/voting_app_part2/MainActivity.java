package com.example.max.voting_app_part2;

import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    boolean alreadyVoted;
    boolean isSelected;
    Button voteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        voteButton = (Button) findViewById(R.id.vote_button);

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.restaurant)));
        listView.setOnItemClickListener(this);

        isSelected = false;
        alreadyVoted= false;

    }

    public void onClick(View view){

        //Daten versenden
        Toast.makeText(getBaseContext(), "funktioniert", Toast.LENGTH_SHORT).show();
        alreadyVoted = true;
        voteButton.setClickable(false);
        voteButton.setEnabled(false);

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        isSelected = true;
        TextView textView = (TextView)view;
        String value = listView.getItemAtPosition(position).toString();
        listView.setItemChecked(position,true);
        listView.setItemsCanFocus(true);
        view.setActivated(true);
        Toast.makeText(getBaseContext(),value + "", Toast.LENGTH_SHORT).show();
    }
}
