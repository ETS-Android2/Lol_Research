package com.example.lol_research;

public class Player {

    private int id;
    private long teamId;
    private String SummonerName;


    public Player(int id, String summonerName, long teamId) {
        this.id = id;
        this.SummonerName = summonerName;
        this.teamId = teamId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName(){
        if (teamId ==100)
        {
            return "Team 1";
        }
        else if (teamId ==200)
        {
            return "Team 2";
        }
        else{
            return "Error : Unexpected teamID";
        }
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
