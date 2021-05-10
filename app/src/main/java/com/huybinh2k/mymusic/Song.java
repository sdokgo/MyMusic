package com.huybinh2k.mymusic;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.huybinh2k.mymusic.database.DatabaseHelper;

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

    public Song(Cursor cursor){
        songName = cursor.getString(
                cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        artist = cursor.getString(
                cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        songPath = cursor.getString(
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
        id  = cursor.getInt(
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        long albumId = cursor.getLong(
                cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
        Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);
        imgPath = String.valueOf(albumArtUri);
        duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
    }

    public Song(Cursor cursor, boolean cursorFavorite){
        this.id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ID));
        this.songName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SONG_NAME));
        this.songPath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SONG_PATH));
        this.artist = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SONG_ARTIST));
        this.imgPath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.IMAGE_PATH));
        this.duration = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.DURATION));
        this.isFavorite = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.FAVORITE))==1;
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
