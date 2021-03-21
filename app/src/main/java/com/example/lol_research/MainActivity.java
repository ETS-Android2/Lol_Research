package com.example.lol_research;

import androidx.appcompat.app.AppCompatActivity;

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



public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.Lol_Research.MESSAGE";
    public static final String EXTRA_ACCOUNTID = "com.example.Lol_Research.MESSAGE2";
    public static final String EXTRA_ICONID = "com.example.Lol_Research.MESSAGE3";
    public static final String EXTRA_SUMMONERLVL = "com.example.Lol_Research.MESSAGE4";
    public static final String EXTRA_SUMMONERID = "";
    public static final String EXTRA_GAMETYPE = "";
    public static final String API_Key = "RGAPI-f7c2b622-f661-4d33-957a-9f710af6638c";

    public String summId = "";

    EditText Summoner_Name;
    ImageView Summoner_Icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Summoner_Name = findViewById(R.id.editTextSummonerName);
        Summoner_Icon = findViewById(R.id.ImageView);

    }

    /** Call when research button ("Profile") is taped by the user */
    public void researchPlayer(View view) {
        Intent intent = new Intent(this, PlayerActivity.class);


        /** Instantiate the RequestQueue. */
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        /** First part : api url (with correct server region [euw1 / or other] + Summoner_Name (typed in editText)
         + riot key api (change every 24 hours so need to specify it to our teacher) */
        String url = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + Summoner_Name.getText().toString() + "?api_key="+API_Key;

        /** Request the JSON format of the summoner searched */
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    String SummonerName ="";
                    String SummonerLvl ="";
                    String SummonerIconID ="";
                    String SummonerID="";
                    String AccountID="";

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            /** We stock the data that interest us */
                            SummonerName =  response.getString("name");
                            SummonerLvl = response.getString("summonerLevel");
                            SummonerIconID = response.getString("profileIconId");
                            SummonerID = response.getString("id");
                            AccountID = response.getString("accountId");


                            /** pass those data to the new activity */
                            intent.putExtra(EXTRA_NAME, SummonerName);
                            intent.putExtra(EXTRA_ACCOUNTID, AccountID);
                            intent.putExtra(EXTRA_SUMMONERLVL, SummonerLvl);
                            intent.putExtra(EXTRA_ICONID, SummonerIconID);
                            intent.putExtra(EXTRA_SUMMONERID, SummonerID);

                            /** start the new activity */
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"No data take back from Summoners V4 Api", Toast.LENGTH_SHORT).show();

                        }
                        /** We then load the player icon if he return to the new activity */
                        Picasso.get().load("https://ddragon.leagueoflegends.com/cdn/11.6.1/img/profileicon/"+SummonerIconID+".png").placeholder(R.drawable.question_mark).into(Summoner_Icon);
                        /** Was gone (didn't exist in the app), we now set it to visible */
                        Summoner_Icon.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(MainActivity.this,"Nom d'invocateur incorrect ou clef api déprécié", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
    }


    /** Call when research button ("En jeu ?") is taped by the user */
    public void researchPlayerInGame(View view) {
        Intent intent = new Intent(this, InGameActivity.class);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        // first part : api url (with correct server region  [euw1 / or other] + Summoner_Name (typed in editText) + riot key api (change every 24 hours so need to specify it to our teacher)
        String url = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + Summoner_Name.getText().toString() + "?api_key="+API_Key;

        // Request the JSON format of the summoner searched
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    String SummonerName ="";
                    String SummonerLvl ="";
                    String SummonerIconID ="";
                    String  SID ="";
                    @Override
                    public void onResponse(JSONObject response) {//maybe create another file for the fonction that will fetch acc info
                        try {
                            SummonerName =  response.getString("name");
                            SummonerLvl = response.getString("summonerLevel");
                            SummonerIconID = response.getString("profileIconId");
                            SID = response.getString("id");
                            //we get the link to get information about the player's game if he is currently playing
                            //String urlGame = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + AccountID + "?api_key=RGAPI-261e279f-b4b2-4733-bed7-8ec331ae2678";
                            /*try{
                                response.
                            }*/

                            // pass the summoner name to the new activity
                            /*intent.putExtra(EXTRA_NAME, SummonerName);
                            intent.putExtra(EXTRA_SUMMONERID, SID);*/
                            summId = SID;//we get the account's id

                            String urlGame = "https://euw1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + summId + "?api_key="+API_Key;

                            // Request the JSON format of the summoner searched
                            JsonObjectRequest jsonObjectGameRequest = new JsonObjectRequest
                                    (Request.Method.GET, urlGame, null, new Response.Listener<JSONObject>() {
                                        String SummonerName ="";
                                        String SummonerLvl ="";
                                        String SummonerIconID ="";
                                        String  AccountID="";
                                        String GameType = "";
                                        @Override
                                        public void onResponse(JSONObject response) {//maybe create another file for the function that will fetch acc info
                                            try {
                            /*SummonerName =  response.getString("name");
                            SummonerLvl = response.getString("summonerLevel");
                            SummonerIconID = response.getString("profileIconId");
                            AccountID = response.getString("accountId");*/
                                                GameType = response.getString("gameType");
                                                //intent.putExtra(EXTRA_GAMETYPE, GameType);
                                                intent.putExtra(EXTRA_SUMMONERID, summId);


                                                // pass the summoner name to the new activity
                            /*intent.putExtra(EXTRA_NAME, SummonerName);
                            intent.putExtra(EXTRA_ACCOUNTID, AccountID);
                            intent.putExtra(EXTRA_SUMMONERLVL, SummonerLvl);
                            intent.putExtra(EXTRA_ICONID, SummonerIconID);*/
                                                // start the new activity

                                                startActivity(intent);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            Toast.makeText(MainActivity.this,  " Playing : " + GameType, Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(MainActivity.this, SummonerName + " " + SummonerLvl, Toast.LENGTH_SHORT).show();

                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // TODO: Handle error
                                            Toast.makeText(MainActivity.this,"L'invocateur n'est pas en partie ou clef api déprécié", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            queue.add(jsonObjectGameRequest);
                            // start the new activity

                            //startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, SummonerName + " " + SummonerLvl + " Summ ID : " + summId, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, SummonerName + " " + SummonerLvl, Toast.LENGTH_SHORT).show();
                        Picasso.get().load("https://ddragon.leagueoflegends.com/cdn/11.6.1/img/profileicon/"+SummonerIconID+".png").placeholder(R.drawable.question_mark).into(Summoner_Icon);

                        /*Summoner_Icon.setImageResource(R.drawable.newimage);*/

                        Summoner_Icon.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(MainActivity.this,"Nom d'invocateur incorrect ou clef api déprécié", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonObjectRequest);
        //We will now try to access another API with the ID we just got


    }


}



/** Other Method to make the request to riot Api (in string format or array format)
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Searching the Summoner : " + response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error api", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }}
  Get Array response, not working with riot api
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String cityID = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "Summoner_name" + cityID, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Nom d'invocateur incorrect", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}*/
