package com.example.lol_research;

public class Player {

    private int id;
    private String SummonerName;

    public Player(int id, String summonerName) {
        this.id = id;
        this.SummonerName = summonerName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSummonerName() {
        return SummonerName;
    }

    public void setSummonerName(String summonerName) {
        SummonerName = summonerName;
    }
}
