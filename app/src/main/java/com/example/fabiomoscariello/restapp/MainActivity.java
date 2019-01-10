package com.example.fabiomoscariello.restapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fabiomoscariello.restapp.RestUtility.Main2Activity;
import com.example.fabiomoscariello.restapp.RestUtility.RestAsClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private TextView restResult;
    private Button restRequestButtonAs;
    private Button restRequestButtonVo;
    private ProgressBar progressBar;
    private static final String TAG = "MainActivity ";
    private JSONArray risultato;
    private JsonObjectRequest jsonObjectRequest;
    private Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, TAG + "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restResult = findViewById(R.id.restResult_id);
        restRequestButtonAs = findViewById(R.id.restRequestButtonAs_id);
        restRequestButtonVo = findViewById(R.id.restRequestButtonVo_id);
        progressBar = findViewById(R.id.progressBar_id);
        restResult.setText("");

        restRequestButtonAs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, TAG + "Bottone Richiesta AS");
                restResult.setText("");
                progressBar.setVisibility(View.VISIBLE);
                RestAsClient.get("/Rest.json", null, new JsonHttpResponseHandler() {
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i(TAG + "REST", "Contenuto:" + response.toString());
                        risultato = response.names();
                        for (int i = 0; i < risultato.length(); i++) {
                            try {
                                progressBar.setVisibility(View.GONE);
                                restResult.append(risultato.get(i).toString() + "\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
        restRequestButtonVo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=Main2Activity.newIntent(MainActivity.this);
                startActivity(i);
            }
        });
        /*restRequestButtonVo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        risultato=response.names();
                        for (int i = 0; i < risultato.length(); i++) {
                            try {
                            progressBar.setVisibility(View.GONE);
                            restResult.append(risultato.get(i).toString() + "\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        }
                    }
                })
            }
        });
    }
}
*/
    }
}