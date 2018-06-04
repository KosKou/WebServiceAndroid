package com.tekshia.wsprueba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends AppCompatActivity {
    private Button LogIn;

    private ArrayList estado = new ArrayList();
    private ArrayList id = new ArrayList();
    private ArrayList name = new ArrayList();
    private ArrayList nivel = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogIn = findViewById(R.id.btnLogIn);
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enviar datos y recibe del webService
                IngresarLogin(getUsername(), getPassword());
            }
        });
    }

    private void IngresarLogin(final String username, String password) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url =
                "https://sitiorandomfanboy.000webhostapp.com/WSPrueba/CONTROLLER/UserControlador.php?op=1";
        final ProgressDialog progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("Cargando Datos...");
        progress.show();
        RequestParams params = new RequestParams();
        params.add("Usuario",username);
        params.add("Password",password);
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
                        nivel.add(jsonArray.getJSONObject(i).getString("nivel"));
                    }

                    String response = estado.get(0).toString();
                    System.out.println("El response del estado: "+response);

                    if (response.contains("success")){
                        Intent intent = new Intent(MainActivity.this, ControlActivity.class);
                        //IDK Why?
                        //Start my intentsExtras
                        intent.putExtra("id",id.get(0).toString());
                        intent.putExtra("username",name.get(0).toString());
                        intent.putExtra("nivel",nivel.get(0).toString());
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

    private void ProblemasTecnicos() {
        Crouton.makeText(this,"PROBLEMAS DE RED",Style.INFO).show();
    }

    private void CredencialesIncorrectas() {
        Crouton.makeText(this,"CREDENCIALES INCORRECTAS", Style.ALERT).show();
    }

    public String getUsername() {
        EditText username = findViewById(R.id.edtUsername);
        return username.getText().toString();
    }

    public String getPassword() {
        EditText password = findViewById(R.id.edtPassword);
        return password.getText().toString();
    }
}
