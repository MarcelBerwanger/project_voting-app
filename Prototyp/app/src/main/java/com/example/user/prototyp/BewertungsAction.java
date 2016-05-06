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
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BewertungsAction extends AppCompatActivity {

    Restaurantes r = new Restaurantes();
    TextView tv;
    Button bu;
    RatingBar rb;
    ArrayList<String> array = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bewertungs_action);

        tv = (TextView)findViewById(R.id.Name);
        bu = (Button)findViewById(R.id.button);
        Intent i = getIntent();
        array = i.getStringArrayListExtra("name");
        rb = (RatingBar)findViewById(R.id.ratingBar);
        tv.setText(array.get(0));
        ButtonClick();
    }

    public void ButtonClick()
    {
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                PendingIntent pendingIntent = PendingIntent.getActivity(BewertungsAction.this,0,intent,0);
                Notification notification = new Notification.Builder(BewertungsAction.this)
                        .setTicker("Die APP")
                        .setContentTitle("Bewertuns info")
                        .setContentText("Bewertet mit "+rb.getRating())
                        .setSmallIcon(R.drawable.ic_info_24dp)
                        .setContentIntent(pendingIntent).getNotification();
                notification.flags=Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0,notification);

            Intent i = new Intent(BewertungsAction.this,HomeScreen.class);
            startActivity(i);
        }
        });
    }

}
