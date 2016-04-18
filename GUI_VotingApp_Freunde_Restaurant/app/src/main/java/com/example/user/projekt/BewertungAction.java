package com.example.user.projekt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by User on 10.04.2016.
 */
public class BewertungAction extends Activity
{
    Restaurantes r = new Restaurantes();
    TextView tv;
    Button bu;
    RatingBar rb;
    ArrayList<String> array = new ArrayList<>();


    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.bewertung);

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
                Intent i = new Intent(BewertungAction.this,MainActivity.class);
                startActivity(i);
                Toast.makeText(BewertungAction.this,"Mit "+rb.getRating()+" Bewertet",Toast.LENGTH_LONG).show();
            }
        });
    }


}
