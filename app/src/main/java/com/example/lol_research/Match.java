package com.example.lol_research;

/**
 * Project "Lol_Research" created by Vincent-BERNET | Némésis on 21/03/2021,
 */
public class Match {
    private int id;
    private String ChampionID;
    private String Queue;
    private String Lane;

    public Match(int id, String championID, String queue, String lane) {
        this.id = id;
        ChampionID = championID;
        Queue = queue;
        Lane = lane;
    }

    public int getId() {
        return id;
    }

    public String getChampionID() {
        return ChampionID;
    }

    public String getQueue() {
        return Queue;
    }

    public String getLane() {
        return Lane;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChampionID(String championID) {
        ChampionID = championID;
    }

    public void setQueue(String queue) {
        Queue = queue;
    }

    public void setLane(String lane) {
        Lane = lane;
    }
}
