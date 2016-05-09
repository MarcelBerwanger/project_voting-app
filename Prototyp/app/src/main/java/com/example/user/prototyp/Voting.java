package com.example.user.prototyp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Voting extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView listView;
    boolean alreadyVoted;
    boolean isSelected;
    Button voteButton;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

        listView = (ListView) findViewById(R.id.listView);
        voteButton = (Button) findViewById(R.id.vote_button);

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.Resataurantes)));
        listView.setOnItemClickListener(Voting.this);

        isSelected = false;
        alreadyVoted= false;

    }



    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        isSelected = true;
        TextView textView = (TextView)view;
        String value = listView.getItemAtPosition(position).toString();
        name = value;
        listView.setItemChecked(position, true);
        listView.setItemsCanFocus(true);
        view.setActivated(true);
        Toast.makeText(getBaseContext(),value + "", Toast.LENGTH_SHORT).show();
    }
    public void onClick(View view){

        //Daten versenden
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(Voting.this, 0, intent, 0);
        Notification notification = new Notification.Builder(Voting.this)
                .setTicker("Die APP")
                .setContentTitle("Voting info")
                .setContentText("Heutiges Voting abgegeben für:  "+name)
                .setSmallIcon(R.drawable.ic_info_24dp)
                .setContentIntent(pendingIntent).getNotification();
        notification.flags=Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
        alreadyVoted = true;
        voteButton.setClickable(false);
        voteButton.setEnabled(false);
        Intent i = new Intent(Voting.this,HomeScreen.class);
        startActivity(i);
    }
}
