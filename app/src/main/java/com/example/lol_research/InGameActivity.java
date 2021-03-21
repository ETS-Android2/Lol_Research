package com.example.lol_research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InGameActivity extends AppCompatActivity {

    public static final String API_Key = "RGAPI-f25f07d1-2e4d-46bb-8e92-5be43b255f9b";

    TextView SummonerName;
    TextView GameType;
    TextView GameMode;
    TextView TVSumm1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        /** Get the Intent that started this activity and extract the string */
        Intent intent = getIntent();
        String Pass_SummonerID = intent.getStringExtra(MainActivity.EXTRA_SUMMONERID);
        //String Pass_GameType = intent.getStringExtra(MainActivity.EXTRA_GAMETYPE);

        /** Initiate our View Variable */
        GameMode = findViewById(R.id.textViewGameMode);
        TVSumm1 = findViewById(R.id.textViewSumm1);

        /** Set The first part of our Table Value : From the previous activity **/
        //GameType.setText(Pass_GameType);

        /** Instantiate the RequestQueue. */
        RequestQueue queue = Volley.newRequestQueue(InGameActivity.this);

        /** First part : api url (with correct server region [euw1 / or other] + Summoner_Name (typed in editText)
         + riot key api (change every 24 hours so need to specify it to our teacher) */
        String urlPlayer = "https://euw1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + Pass_SummonerID + "?api_key="+API_Key;

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, urlPlayer, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String Summoner_Name1 = "";
                String game_mode = "";
                //String Summoner_Win = "", Summoner_Loose="", Summoner_Rank="", Summoner_Lp="";
                try {
                    game_mode = response.getString("gameMode");
                    /*Summoner_Tier = CoolData.getString("tier");
                    Summoner_Win = CoolData.getString("wins");
                    Summoner_Loose = CoolData.getString("losses");
                    Summoner_Rank = CoolData.getString("rank");
                    Summoner_Lp = CoolData.getString("leaguePoints");*/
                    JSONArray Players = response.getJSONArray("participants");
                    //JSONObject player = Players.getJSONObject(0);
                    Summoner_Name1 = Players.getJSONObject(0).getString("summonerName");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GameMode.setText(game_mode);
                TVSumm1.setText(Summoner_Name1);
                /*if (Summoner_Tier!="") {
                    SummonerTier.setText(Summoner_Tier + " " + Summoner_Rank + " | " + Summoner_Lp + "points");
                    SummonerWinrate.setText(Summoner_Win +"V | "+Summoner_Loose +"D");
                    SummonerRank.setText("Midlaner");
                }
                else{
                    SummonerWinrate.setText("0V | 0D");
                    SummonerTier.setText("Non Classé");
                    SummonerRank.setText("Inconnu");}*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InGameActivity.this, "Connexion Api Spectator V4 échec :" + Pass_SummonerID + " error :" +error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
/*
        JsonArrayRequest requestPlayers = new JsonArrayRequest(Request.Method.GET, urlPlayer, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String Summoner_Tier = "";
                String Summoner_Win = "", Summoner_Loose="", Summoner_Rank="", Summoner_Lp="";
                try {
                    JSONArray Players = response.getJSONObject(0);
                    Summoner_Tier = Players.getString("tier");
                    Summoner_Win = Players.getString("wins");
                    Summoner_Loose = Players.getString("losses");
                    Summoner_Rank = Players.getString("rank");
                    Summoner_Lp = CoolData.getString("leaguePoints");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InGameActivity.this, "Connexion Api Spectator V4 échec", Toast.LENGTH_SHORT).show();
            }
        });

*/


    }
}