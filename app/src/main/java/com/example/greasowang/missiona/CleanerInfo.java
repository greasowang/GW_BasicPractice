package com.example.greasowang.missiona;

/**
 * Created by greasowang on 2017/8/9.
 */

public class CleanerInfo {
    public String name;
    public String photo;
    public int votes;
    public boolean voted;

    public void init(String name, String photo, int votes, boolean voted){
        this.name = name;
        this.photo = photo;
        this.votes = votes;
        this.voted = voted;
    }
}
