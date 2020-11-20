package com.example.covid_19tracker;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class queryAp extends AppCompatActivity {
    EditText editTextIpAddress;
    Button btn;
    TextView textView;
    ArrayList<String> connectivityList = new ArrayList<String>();
    DatabaseHelper db;
    final ArrayList<String> patients = new ArrayList<>();
    final ArrayList<String> Phone_number = new ArrayList<>();
    final ArrayList<String> MacAddress = new ArrayList<>();
    final ArrayList<String> patientsAtAP= new ArrayList<>();
    ListView listView ;


    int numberOfPatients=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_ap);
        editTextIpAddress =  findViewById(R.id.editTextIpAddress);
        btn= findViewById(R.id.buttonQuery);
        textView=  findViewById(R.id.textViewAP);
        listView =  findViewById(R.id.listViewOfDetectedPatients);
        db = new DatabaseHelper(this);
        Cursor res= db.getAllData();
        while(res.moveToNext())
        {
            patients.add(res.getString(1));
            Phone_number.add(res.getString(2));
            MacAddress.add(res.getString(3));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread T= new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try{

                            Socket s=new Socket(editTextIpAddress.getText().toString(),5000);
//                            DataInputStream dis=new DataInputStream(s.getInputStream());
//						    final String  str= dis.readUTF();
                            ObjectInputStream dis = new ObjectInputStream(s.getInputStream()); //Error Line!
                            try {
                                Object object = dis.readObject();
                                connectivityList =  (ArrayList<String>) object;

                            } catch (ClassNotFoundException e) {

                                e.printStackTrace();
                            }

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    for(int i = 0; i <MacAddress.size(); i++){
                                        for(int j=0;j<connectivityList.size();j++){

                                        if (MacAddress.get(i).equals(connectivityList.get(j)))
                                        {
                                            numberOfPatients++;
                                            patientsAtAP.add(patients.get(i));
                                        }
                                        }
                                    }
                                    if (numberOfPatients!=0){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(queryAp.this);
                                        builder.setCancelable(true);
                                        builder.setTitle("WARNING");
                                        builder.setMessage(String.valueOf(numberOfPatients)+" infected people connected to this AP. Rest in PIECES");

                                        builder.setPositiveButton("SHIT IS GETTIN REAL", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                listView.setAdapter(new ArrayAdapter<>(queryAp.this,android.R.layout.simple_list_item_1,patientsAtAP));
                                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        Toast.makeText(queryAp.this,"Mac Address: "+ MacAddress.get(patients.indexOf(patientsAtAP.get(position))) + "\n"
                                                                +"Phone Number: "+Phone_number.get(patients.indexOf(patientsAtAP.get(position))),Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        });
                                    builder.show();
                                 //   textView.setText(String.valueOf(numberOfPatients));
                                    }
                                    else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(queryAp.this);
                                        builder.setCancelable(true);
                                        builder.setTitle("YOU'RE SAFE");
                                        builder.setMessage("NO infected people connected to this AP");

                                        builder.setNegativeButton("YAY", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        builder.show();
                                    }
                                }
                            });
						    dis.close();

                        }catch(UnknownHostException e){
                            Toast.makeText(queryAp.this,e.getMessage()+ "1",Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e){
                            Toast.makeText(queryAp.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        catch (NumberFormatException e) {
                            Toast.makeText(queryAp.this,e.getMessage()+ "3",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                T.start();
            }

        });
    }
}







