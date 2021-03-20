package com.example.lol_research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class PlayerActivity extends AppCompatActivity {

    TextView SummonerName;
    ImageView SummonerIcon;
    TextView SummonerLvl;
    TextView SummonerRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String Pass_Name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        String Pass_Lvl = intent.getStringExtra(MainActivity.EXTRA_SUMMONERLVL);
        String Pass_IconID = intent.getStringExtra(MainActivity.EXTRA_ICONID);
        String Pass_SummonerID = intent.getStringExtra(MainActivity.EXTRA_SUMMONERID);
        String Pass_AccountID = intent.getStringExtra(MainActivity.EXTRA_ACCOUNTID);

        SummonerName = findViewById(R.id.textViewName);
        SummonerIcon = findViewById(R.id.imageViewIcon);
        SummonerLvl = findViewById(R.id.textViewLvl);
        SummonerRank = findViewById(R.id.textViewRank);
        Toast.makeText(PlayerActivity.this, Pass_Lvl, Toast.LENGTH_SHORT).show();

        Picasso.get().load("https://ddragon.leagueoflegends.com/cdn/11.6.1/img/profileicon/"+Pass_IconID+".png").placeholder(R.drawable.question_mark).into(SummonerIcon);
        SummonerName.setText(Pass_Name);
        SummonerLvl.setText(Pass_Lvl);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(PlayerActivity.this);
        Toast.makeText(PlayerActivity.this, Pass_Name, Toast.LENGTH_SHORT).show();
        Toast.makeText(PlayerActivity.this, Pass_SummonerID, Toast.LENGTH_SHORT).show();
        Pass_SummonerID = "G57MR5MrsO99omoCAgYRB_cqzC2INZg648vhAsGCqo7e6vc";
        // first part : api url (with correct server region  [euw1 / or other] + Summoner_Name (typed in editText) + riot key api (change every 24 hours so need to specify it to our teacher)
        String urlPlayer = "https://euw1.api.riotgames.com/lol/league/v4/entries/by-summoner/"+Pass_SummonerID+"?api_key=RGAPI-06b5f624-c861-40c1-8a7d-5b7f45e3a43a";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlPlayer, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String SummonerTier = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    SummonerTier = cityInfo.getString("tier");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SummonerRank.setText(SummonerTier);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PlayerActivity.this, "Connexion Api League V4 échec", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }}

