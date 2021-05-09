package com.huybinh2k.mymusic;

import java.io.Serializable;

/**
 * Create by BinhBH 5/9/2021
 */
public class Song implements Serializable {
    private int id;
    private String songName;
    private String songPath;
    private String imgPath;
    private String artist;
    private long duration;
    private boolean isFavorite = false;

    public Song(int id, String songName, String songPath, String artist, String img, long duration) {
        this.id = id;
        this.songName = songName;
        this.songPath = songPath;
        this.artist = artist;
        this.imgPath = img;
        this.duration = duration;
    }


    public int getId() {
        return id;
    }


    public String getSongName() {
        return songName;
    }


    public String getSongPath() {
        return songPath;
    }


    public String getImg() {
        return imgPath;
    }


    public String getArtist() {
        return artist;
    }


    public long getDuration() {
        return duration;
    }


    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
