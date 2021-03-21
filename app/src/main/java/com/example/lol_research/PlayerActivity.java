package com.example.lol_research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    public static final String API_Key = "RGAPI-f7c2b622-f661-4d33-957a-9f710af6638c";
    public static final String NBR_Match = "5";

    TextView SummonerName;
    ImageView SummonerIcon;
    TextView SummonerLvl;
    TextView SummonerRank;
    TextView SummonerWinrate;
    TextView SummonerTier;
    List<Match> matchList = new ArrayList<Match>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // Our recyclerView
        fillMatchList();
        recyclerView = findViewById(R.id.lv_matchList);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter
        mAdapter = new RecycleViewAdapter(matchList);
        recyclerView.setAdapter(mAdapter);

        /** Get the Intent that started this activity and extract the string */
        Intent intent = getIntent();
        String Pass_Name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        String Pass_Lvl = intent.getStringExtra(MainActivity.EXTRA_SUMMONERLVL);
        String Pass_IconID = intent.getStringExtra(MainActivity.EXTRA_ICONID);
        String Pass_SummonerID = intent.getStringExtra(MainActivity.EXTRA_SUMMONERID);
        String Pass_AccountID = intent.getStringExtra(MainActivity.EXTRA_ACCOUNTID);

        /** Initiate our View Variable */
        SummonerName = findViewById(R.id.textViewName);
        SummonerIcon = findViewById(R.id.imageViewIcon);
        SummonerLvl = findViewById(R.id.textViewLvl);
        SummonerRank = findViewById(R.id.textViewRank);
        SummonerWinrate= findViewById(R.id.textViewWinrate);
        SummonerTier = findViewById(R.id.textViewTier);

        /** Set The first part of our Table Value : From the previous activity (which was connected to summonerV4 Riot's api */
        Picasso.get().load("https://ddragon.leagueoflegends.com/cdn/11.6.1/img/profileicon/"+Pass_IconID+".png").placeholder(R.drawable.question_mark).into(SummonerIcon);
        SummonerName.setText(Pass_Name);
        SummonerLvl.setText(Pass_Lvl);

        /** Instantiate the RequestQueue. */
        RequestQueue queue = Volley.newRequestQueue(PlayerActivity.this);

        /** First part : api url (with correct server region [euw1 / or other] + Summoner_Name (typed in editText)
         + riot key api (change every 24 hours so need to specify it to our teacher) */
        String urlProfile = "https://euw1.api.riotgames.com/lol/league/v4/entries/by-summoner/"+Pass_SummonerID+"?api_key="+API_Key;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlProfile, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String Summoner_Tier = "";
                String Summoner_Win = "", Summoner_Loose="", Summoner_Rank="", Summoner_Lp="";
                try {
                    JSONObject CoolData = response.getJSONObject(0);
                    Summoner_Tier = CoolData.getString("tier");
                    Summoner_Win = CoolData.getString("wins");
                    Summoner_Loose = CoolData.getString("losses");
                    Summoner_Rank = CoolData.getString("rank");
                    Summoner_Lp = CoolData.getString("leaguePoints");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (Summoner_Tier!="") {
                    SummonerTier.setText(Summoner_Tier + " " + Summoner_Rank + " | " + Summoner_Lp + "points");
                    SummonerWinrate.setText(Summoner_Win +"V | "+Summoner_Loose +"D");
                    SummonerRank.setText("Midlaner");
                }
                else{
                    SummonerWinrate.setText("0V | 0D");
                    SummonerTier.setText("Non Classé");
                    SummonerRank.setText("Inconnu");}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PlayerActivity.this, "Connexion Api League V4 échec", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
        Toast.makeText(PlayerActivity.this, Pass_AccountID, Toast.LENGTH_SHORT).show();
        String urlHisto = "https://euw1.api.riotgames.com/lol/match/v4/matchlists/by-account/"+Pass_AccountID+"?api_key="+API_Key+"&endIndex="+NBR_Match;

        /** Request the JSON format of the summoner searched */
         JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
         (Request.Method.GET, urlHisto, null, new Response.Listener<JSONObject>() {
         JSONArray Match;
         String lane = "";



         @Override
         public void onResponse(JSONObject response) {
         try {
            /** We stock the data that interest us */
            Match =  response.getJSONArray("matches");
            JSONObject Match1  = Match.getJSONObject(0);
            lane = Match1.getString("lane");
             Toast.makeText(PlayerActivity.this,lane, Toast.LENGTH_SHORT).show();
         } catch (JSONException e) {
         e.printStackTrace();
         Toast.makeText(PlayerActivity.this,"No data take back", Toast.LENGTH_SHORT).show();
         }

         }
         }, new Response.ErrorListener() {

         @Override
         public void onErrorResponse(VolleyError error) {
         // TODO: Handle error
         Toast.makeText(PlayerActivity.this,"Connexion Api Match V4 échec", Toast.LENGTH_SHORT).show();
         }
         });
         queue.add(jsonObjectRequest);





    }

    private void fillMatchList() {
        Match match1 = new Match(0,"1","420","yo");
        Match match2 = new Match(0,"1","420","yo");
        Match match3 = new Match(0,"1","420","yo");
        Match match4 = new Match(0,"1","420","yo");
        Match match5 = new Match(0,"1","420","yo");

        matchList.add(match1);
    }
}




