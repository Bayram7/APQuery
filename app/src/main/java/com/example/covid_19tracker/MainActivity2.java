package com.example.covid_19tracker;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    DatabaseHelper db;
    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        db = new DatabaseHelper(this);
        Cursor res= db.getAllData();
        listView = findViewById(R.id.listView);
        final ArrayList<String> patients = new ArrayList<>();
        final ArrayList<String> Phone_number = new ArrayList<>();
        final ArrayList<String> MacAddress = new ArrayList<>();

       while(res.moveToNext())
       {
           patients.add(res.getString(1));
           Phone_number.add(res.getString(2));
           MacAddress.add(res.getString(3));
       }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,patients);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity2.this,"Mac Address: "+ MacAddress.get(position) + "\n"
                                +"Phone Number: "+Phone_number.get(position),Toast.LENGTH_SHORT).show();
            }
        });




    }
}