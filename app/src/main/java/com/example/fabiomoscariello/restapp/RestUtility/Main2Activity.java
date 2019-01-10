package com.example.fabiomoscariello.restapp.RestUtility;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fabiomoscariello.restapp.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Main2Activity extends AppCompatActivity {
    private ListView listView;

    private Button requestButton;
    private ProgressBar progressBar;
    private static final String TAG="Main2Activity";
    private JSONArray risultato;
    private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView= findViewById(R.id.lista_utenti_id);
        requestButton=findViewById(R.id.restRequestButtonAs2_id);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                RestAsClient.get("/Rest.json", null, new JsonHttpResponseHandler() {
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i(TAG + "REST", "Contenuto:" + response.toString());
                        risultato = response.names();
                        list=new ArrayList<String>();
                        if(risultato != null){
                        for (int i = 0; i < risultato.length(); i++) {
                        try {
                            list.add(risultato.get(i).toString());
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        }


                        }
                        ArrayAdapter<String> adapter =new ArrayAdapter<String>(Main2Activity.this,R.layout.item,list);
                        progressBar.setVisibility(View.GONE);
                    }

                    });

                }
            });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // recupero il titolo memorizzato nella riga tramite l'ArrayAdapter
                final String utente = (String) parent.getItemAtPosition(position);
                Log.d("List", "Ho cliccato sull'elemento con titolo " + utente);
                RestAsClient.get("Utenti/"+utente+".json",null,new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        String x=(String) response.opt("password");
                        Toast.makeText(getApplicationContext(),x,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
    public static Intent newIntent(Context packageContext){
        Log.d(TAG,"Intent"+TAG+"creato");
        Intent i=new Intent(packageContext,Main2Activity.class);
        return i;
    }
}
