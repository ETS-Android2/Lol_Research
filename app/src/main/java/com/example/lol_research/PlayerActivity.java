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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String Name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        String Lvl = intent.getStringExtra(MainActivity.EXTRA_SUMMONERLVL);
        String SummonerIconID = intent.getStringExtra(MainActivity.EXTRA_ICONID);

        SummonerName = findViewById(R.id.textViewName);
        SummonerIcon = findViewById(R.id.imageViewIcon);
        SummonerLvl = findViewById(R.id.textViewLvl);

        Picasso.get().load("https://ddragon.leagueoflegends.com/cdn/11.6.1/img/profileicon/"+SummonerIconID+".png").placeholder(R.drawable.question_mark).into(SummonerIcon);
        SummonerName.setText(Name);
        SummonerLvl.setText(Lvl);
        Toast.makeText(PlayerActivity.this,Name, Toast.LENGTH_SHORT).show();
        // Update the activity with previous extra parameters

    }
}