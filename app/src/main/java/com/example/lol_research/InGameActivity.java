package com.example.lol_research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InGameActivity extends AppCompatActivity {


    public static final String API_Key = "RGAPI-b0c45cf7-d105-42ba-b3f1-b42ccc19b691";


    TextView SummonerName;
    TextView GameType;
    TextView GameMode;
    TextView TVSumm1;

    List<Player> playerList = new ArrayList<Player>();//List that will contain the players' info

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

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
        TVSumm1 = findViewById(R.id.textView_gameTimer);

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
                long gameLength = 0;
                long teamId = 0;
                //String Summoner_Win = "", Summoner_Loose="", Summoner_Rank="", Summoner_Lp="";
                try {
                    game_mode = response.getString("gameMode");
                    gameLength = response.getLong("gameLength");
                    JSONArray Players = response.getJSONArray("participants");
                    for (int i = 0; i<10; i++) {//we stop at 10 because that is usually the number of players in a game
                        Summoner_Name1 = Players.getJSONObject(i).getString("summonerName");
                        teamId = Players.getJSONObject(i).getLong("teamId");
                        playerList.add(new Player(0, Summoner_Name1,teamId));//we add every json object we got to the list, ie the 10 players
                    }
                    //here we'll set up the RecycleViewAdapter so we can put the elements in the recycle list
                    recyclerView = findViewById(R.id.lv_playerList);//define the recycle view that will contain the player list
                    recyclerView.setHasFixedSize(true);
                    // use a linear layout manager
                    layoutManager = new LinearLayoutManager(InGameActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    // specify an adapter
                    mAdapter = new RecycleViewAdapterPlayer(playerList, InGameActivity.this);
                    recyclerView.setAdapter(mAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GameMode.setText(game_mode);
                //just for the sake of having a nice looking time
                gameLength += 180;//The spec timer is approx 3 mins behind the real in game timer
                if (gameLength<0) {
                    TVSumm1.setText("Game timer : " + 0 + ":0" +0);//in game time in mins:sec
                }
                else if(gameLength%60 <10){
                    TVSumm1.setText("Game timer : " + gameLength/60 + ":0"+gameLength%60);//in game time in mins:sec
                }
                else
                {
                    TVSumm1.setText("Game timer : " + gameLength/60 + ":"+gameLength%60);//in game time in mins:sec
                }
                //
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InGameActivity.this, "Connexion Api Spectator V4 échec :" + Pass_SummonerID + " error :" +error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }
}