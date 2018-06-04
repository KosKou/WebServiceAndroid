package com.tekshia.wsprueba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        String id = getIntent().getExtras().getString("id");
        String name = getIntent().getExtras().getString("username");
        String nivel = getIntent().getExtras().getString("nivel");

        TextView codigoV = findViewById(R.id.lblUser);

        codigoV.setText("Usuario Logueado: "+name+" ID: "+id+
        " Nivel: "+nivel);

        // Find the View that shows the numbers category
        TextView client = (TextView) findViewById(R.id.clients);

        // Find the View that shows the family category
        TextView product = (TextView) findViewById(R.id.products);

//        On Click Methods
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientList();
            }
        });

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productList();
            }
        });

    }

    private void productList() {
        final ArrayList estado = new ArrayList();
        final ArrayList id = new ArrayList();
        final ArrayList name = new ArrayList();
        final ArrayList stock = new ArrayList();
        final ArrayList price = new ArrayList();
        final ArrayList category = new ArrayList();

        AsyncHttpClient product = new AsyncHttpClient();
        String url =
                "https://sitiorandomfanboy.000webhostapp.com/WSPrueba/CONTROLLER/ProductoControlador.php?op=2";
        final ProgressDialog progress = new ProgressDialog(ControlActivity.this);
        progress.setMessage("Cargando Datos...");
        progress.show();
        RequestParams params = new RequestParams();
        RequestHandle post = product.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progress.cancel();
                System.out.println("Ya Entro en el Success");
                try {
                    JSONArray jsonArray = new JSONArray(new String(responseBody));

                    for (int i = 0; i < jsonArray.length(); i++){
                        estado.add(jsonArray.getJSONObject(i).getString("estado"));
                        id.add(jsonArray.getJSONObject(i).getString("codigo"));
                        name.add(jsonArray.getJSONObject(i).getString("nombre"));
                        stock.add(jsonArray.getJSONObject(i).getString("stock"));
                        price.add(jsonArray.getJSONObject(i).getString("precio"));
                        category.add(jsonArray.getJSONObject(i).getString("categoria"));
                    }

                    String response = estado.get(0).toString();
                    System.out.println("El response del estado: "+response);

                    if (response.contains("success")){
                        Intent intent = new Intent(ControlActivity.this, ProductActivity.class);
                        //IDK Why?
                        //Start my intentsExtras
                        intent.putStringArrayListExtra("id",id);
                        intent.putStringArrayListExtra("name",name);
                        intent.putStringArrayListExtra("stock",stock);
                        intent.putStringArrayListExtra("price",price);
                        intent.putStringArrayListExtra("category",category);
                        startActivity(intent);
                        progress.dismiss();
                    }else if (response.contains("failed")){
                        CredencialesIncorrectas();
                    }else {
                        ProblemasTecnicos();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void clientList() {
        final ArrayList estado = new ArrayList();
        final ArrayList id = new ArrayList();
        final ArrayList name = new ArrayList();

        AsyncHttpClient client = new AsyncHttpClient();
        String url =
                "https://sitiorandomfanboy.000webhostapp.com/WSPrueba/CONTROLLER/ClientControlador.php?op=1";
        final ProgressDialog progress = new ProgressDialog(ControlActivity.this);
        progress.setMessage("Cargando Datos...");
        progress.show();
        RequestParams params = new RequestParams();
        RequestHandle post = client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progress.cancel();
                System.out.println("Ya Entro en el Success");
                try {
                    JSONArray jsonArray = new JSONArray(new String(responseBody));

                    for (int i = 0; i < jsonArray.length(); i++){
                        estado.add(jsonArray.getJSONObject(i).getString("estado"));
                        id.add(jsonArray.getJSONObject(i).getString("id"));
                        name.add(jsonArray.getJSONObject(i).getString("username"));
                    }

                    String response = estado.get(0).toString();
                    System.out.println("El response del estado: "+response);

                    if (response.contains("success")){
                        Intent intent = new Intent(ControlActivity.this, ClientActivity.class);
                        //IDK Why?
                        //Start my intentsExtras
                        intent.putStringArrayListExtra("id",id);
                        intent.putStringArrayListExtra("username",name);
                        startActivity(intent);
                        progress.dismiss();
                    }else if (response.contains("failed")){
                        CredencialesIncorrectas();
                    }else {
                        ProblemasTecnicos();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


//    ERRORES
    private void ProblemasTecnicos() {
        Crouton.makeText(this,"PROBLEMAS DE RED", Style.INFO).show();
    }

    private void CredencialesIncorrectas() {
        Crouton.makeText(this,"CREDENCIALES INCORRECTAS", Style.ALERT).show();
    }
}
