package com.tekshia.wsprueba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        ArrayList id = getIntent().getExtras().getStringArrayList("id");
        ArrayList name = getIntent().getExtras().getStringArrayList("username");

        ArrayList<String> textShow = new ArrayList<String>();

        for (int i = 0; i<id.size();i++){
            textShow.add("Id: "+id.get(i)+" Name: "+name.get(i));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, textShow);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(arrayAdapter);
    }
}
