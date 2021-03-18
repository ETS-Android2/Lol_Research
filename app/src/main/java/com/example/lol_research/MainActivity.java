package com.example.lol_research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.Lol_Research.MESSAGE";
    EditText Summoner_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Summoner_Name = findViewById(R.id.editTextSummonerName);

    }

    /** Call when research button ("Go !") is taped by the user */
    public void researchPlayer(View view) {
        /**
         * Intent intent = new Intent(this, PlayerActivity.class);
        // pass the summoner name to the new activity
        intent.putExtra(EXTRA_MESSAGE, Summoner_Name.getText().toString());
        // start the new activity
        startActivity(intent);**/
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // first part : api url (with correct server region  [euw1 / or other] + Summoner_Name (typed in editText) + riot key api (change every 24 hours so need to specify it to our teacher)
        String url ="https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+Summoner_Name.getText().toString()+"?api_key=RGAPI-b55ca9cb-01d9-41a6-b9e3-ce1d3fb22c3d";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Searching the Summoner : "+response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error api", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        //Toast.makeText(MainActivity.this, "Searching the Summoner : "+Summoner_Name.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}