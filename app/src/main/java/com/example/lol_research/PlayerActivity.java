package com.example.lol_research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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


    public static final String API_Key = "RGAPI-b0c45cf7-d105-42ba-b3f1-b42ccc19b691";

    public static Integer NBR_Match = 0;
    public static Integer NBR_MatchTotal = 0;
    EditText NBR_MatchHistorique;
    TextView SummonerName;
    ImageView SummonerIcon;
    TextView SummonerLvl;
    TextView SummonerRank;
    TextView SummonerWinrate;
    TextView SummonerTier;
    TextView textViewHidden;
    List<Match> matchList = new ArrayList<Match>();


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        NBR_MatchHistorique = findViewById(R.id.editTextNbrHisto);
        NBR_MatchTotal = 0;

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
        textViewHidden = findViewById(R.id.textViewHidden);
        /** Set The first part of our Table Value : From the previous activity (which was connected to summonerV4 Riot's api */
        Picasso.get().load("https://ddragon.leagueoflegends.com/cdn/11.6.1/img/profileicon/"+Pass_IconID+".png").placeholder(R.drawable.question_mark).into(SummonerIcon);
        SummonerName.setText(Pass_Name);
        SummonerLvl.setText(Pass_Lvl);
        textViewHidden.setText(Pass_AccountID);

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
                    //Toast.makeText(PlayerActivity.this,"No data take back from League V4 Api", Toast.LENGTH_SHORT).show();

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




    }

    private void fillMatchList(int i,String ChampionID,String lane,String queue) {
        List<String> ChampList = Arrays.asList("Aatrox", "Ahri", "Akali", "Alistar","Amumu", "Anivia", "Annie", "Aphelios", "Ashe", "AurelionSol", "Azir", "Bard", "Blitzcrank", "Brand", "Braum", "Caitlyn", "Camille", "Cassiopeia", "Chogath","Corki", "Darius", "Diana", "Dr. Mundo", "Draven", "Ekko", "Elise", "Evelynn", "Ezreal", "Fiddlesticks", "Fiora", "Fizz", "Galio", "Gangplank", "Garen", "Gnar", "Gragas", "Graves", "Hecarim", "Heimerdinger", "Illaoi", "Irelia", "Ivern", "Janna", "JarvanIV", "Jax", "Jayce", "Jhin", "Jinx", "Kaisa", "Kalista", "Karma", "Karthus", "Kassadin", "Katarina", "Kayle", "Kayn", "Kennen", "Khazix", "Kindred", "Kled", "Kog'Maw", "LeBlanc", "Lee Sin", "Leona", "Lillia", "Lissandra", "Lucian", "Lulu", "Lux", "Malphite", "Malzahar", "Maokai", "MasterYi", "MissFortune", "Mordekaiser", "Morgana", "Nami", "Nasus", "Nautilus", "Neeko", "Nidalee", "Nocturne", "Nunu", "Olaf", "Orianna", "Ornn", "Pantheon", "Poppy", "Pyke", "Qiyana");
        Random rand = new Random();
        String randomChamp = ChampList.get(rand.nextInt(ChampList.size()));
        Match matchX = new Match(i,"https://ddragon.leagueoflegends.com/cdn/11.6.1/img/champion/"+randomChamp+".png",queue,lane);
        matchList.add(matchX);
    }

    public void NumberHistorique(View view)
    {
        /** Instantiate the RequestQueue. */
        RequestQueue queue = Volley.newRequestQueue(PlayerActivity.this);
        String Pass_AccountID = textViewHidden.getText().toString();
        if (NBR_MatchHistorique.getText().toString().trim().length() == 0)
        {

            NBR_Match=0;
        }
        else {NBR_Match = Integer.parseInt(NBR_MatchHistorique.getText().toString());
        NBR_MatchTotal = NBR_MatchTotal + NBR_Match;}
        Toast.makeText(PlayerActivity.this,"Ajout de "+NBR_Match+" parties à l'historique", Toast.LENGTH_LONG).show();
        Toast.makeText(PlayerActivity.this,"Historique pour "+NBR_MatchTotal+" parties", Toast.LENGTH_LONG).show();
        /** Connexion to Match V4 for player historique */
        String urlHisto = "https://euw1.api.riotgames.com/lol/match/v4/matchlists/by-account/"+Pass_AccountID+"?api_key="+API_Key+"&endIndex="+NBR_Match;

        /** Request the JSON format of the summoner searched */
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlHisto, null, new Response.Listener<JSONObject>() {
                    JSONArray Match;


                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            /** We stock the data that interest us */
                            Match =  response.getJSONArray("matches");
                            for (int i= 0; i<NBR_Match;i++) {
                                JSONObject MatchX = Match.getJSONObject(i);
                                String lane = MatchX.getString("lane");
                                String queue = MatchX.getString("queue");
                                String ChampionID = MatchX.getString("champion");
                               /* String urlChampion = "https://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion.json";
                                RequestQueue queue2 = Volley.newRequestQueue(PlayerActivity.this);
                                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest  (Request.Method.GET, urlChampion, null, new Response.Listener<JSONObject>()
                                {
                                    JSONArray ChampionList;

                                    @Override
                                    public void onResponse(JSONObject responseChamp) {
                                        try {
                                            ChampionList= responseChamp.getJSONArray("data");
                                            for (int i= 0; i<153;i++)
                                            {
                                                JSONObject ChampionX = ChampionList.getJSONObject(i);
                                            if (ChampionX.getString("key").equals(ChampionID))
                                            {
                                                String ChampionName = ChampionX.getString("name");
                                                Toast.makeText(PlayerActivity.this,ChampionName, Toast.LENGTH_SHORT).show();
                                                i=153;
                                            }
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(PlayerActivity.this,"Api Champion Icone currently not available", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Toast.makeText(PlayerActivity.this,"Connexion Api Match V4 échec", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                queue2.add(jsonObjectRequest2);*/
                                if (queue.equals("420")) {
                                    queue = "Solo Q";
                                } else if (queue.equals("450")) {
                                    queue = "Normal Game";
                                } else {
                                    queue = "Flex Q";
                                }
                                if (lane.equals("NONE")) {
                                    fillMatchList(i,ChampionID, "Rôle : Inconnu", queue);
                                } else {
                                    fillMatchList(i,ChampionID, "Rôle : " + lane, queue);
                                }
                            }
                            // Our recyclerView

                            recyclerView = findViewById(R.id.lv_matchList);
                            recyclerView.setHasFixedSize(true);
                            // use a linear layout manager
                            layoutManager = new LinearLayoutManager(PlayerActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            // specify an adapter
                            mAdapter = new RecycleViewAdapter(matchList, PlayerActivity.this);
                            recyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PlayerActivity.this,"No data take back from Match V4 Api", Toast.LENGTH_SHORT).show();
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
}




