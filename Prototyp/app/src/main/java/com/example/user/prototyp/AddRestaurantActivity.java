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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddRestaurantActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button addButton;
    TextView nameEdittext;
    TextView adresseEdittext;
    Spinner kategorieSpinner;
    ArrayAdapter<CharSequence> adapter;
    String name;
    String kategorie;
    String adresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        addButton = (Button) findViewById(R.id.add_button);
        adresseEdittext = (EditText) findViewById(R.id.adresse_editText);
        kategorieSpinner = (Spinner) findViewById(R.id.spinner);
        nameEdittext = (EditText) findViewById(R.id.name_editText);
        adapter = ArrayAdapter.createFromResource(this, R.array.kategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategorieSpinner.setAdapter(adapter);
        kategorieSpinner.setOnItemSelectedListener(AddRestaurantActivity.this);

    }
    public void onClick(View view){

        //Daten lesen
        name = nameEdittext.getText().toString();
        adresse = adresseEdittext.getText().toString();


        //Daten versenden
        Toast.makeText(getBaseContext(), "Restaurantdaten wurden erfolgreich versendet", Toast.LENGTH_SHORT).show();

        //Wenn fertig -> Activity schließen
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        kategorie = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast.makeText(getBaseContext(), "Keine Kategorie gewählt", Toast.LENGTH_SHORT).show();

    }

}
