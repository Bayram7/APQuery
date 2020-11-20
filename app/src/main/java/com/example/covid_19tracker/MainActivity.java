package com.example.covid_19tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    Button btnPatients;
    Button btnQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);

        btnPatients = (Button) findViewById(R.id.PatientsBtn);
        btnQuery= (Button) findViewById(R.id.queryBtn);


        btnPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,queryAp.class));
            }
        });

    }

//    private void viewPatients() {
//        Cursor res = db.getAllData();
//
//        if(res.getCount()==0){
//            Toast.makeText(this,"no data to show",Toast.LENGTH_SHORT).show();
//        }
//        else {
//            StringBuffer buffer = new StringBuffer();
//            while(res.moveToNext()){
//                buffer.append("ID "+ res.getString(0) + "\n");
//                buffer.append("NAME "+ res.getString(1) + "\n");
//                buffer.append("PHONE_NUMBER "+ res.getString(2) + "\n");
//                buffer.append("MAC_ADDRESS "+ res.getString(3) + "\n");
//
//            }
//            Toast.makeText(this,buffer.toString(),Toast.LENGTH_SHORT).show();
//        }
//    }

}