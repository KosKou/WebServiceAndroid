package com.tekshia.wsprueba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        ArrayList id = getIntent().getExtras().getStringArrayList("id");
        ArrayList name = getIntent().getExtras().getStringArrayList("name");
        ArrayList stock = getIntent().getExtras().getStringArrayList("stock");
        ArrayList price = getIntent().getExtras().getStringArrayList("price");
        ArrayList category = getIntent().getExtras().getStringArrayList("category");

        ArrayList<String> textShow = new ArrayList<String>();

        for (int i = 0; i<id.size();i++){
            textShow.add("Id: "+id.get(i)+" Producto: "+name.get(i)+" Stock: "+stock.get(i)+
            " Price: "+price.get(i)+" Categoria: "+category.get(i));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, textShow);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(arrayAdapter);
    }
}
