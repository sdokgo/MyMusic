package com.huybinh2k.mymusic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Create by BinhBH 5/10/2021
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "song_database";
    public static final String FAVORITE_SONGS_TABLE = "favorite_song_table";
    public static final String ID = "id";
    public static final String SONG_NAME = "song_name";
    public static final String SONG_PATH = "song_path";
    public static final String SONG_ARTIST = "song_artist";
    public static final String IMAGE_PATH = "image_path";
    public static final String DURATION = "duration";
    public static final String FAVORITE = "favorite";
    public static final int IS_FAVORITE = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + FAVORITE_SONGS_TABLE + "(" +
                ID + " INTEGER UNIQUE PRIMARY KEY," +
                SONG_NAME + " TEXT," +
                SONG_PATH + " TEXT," +
                SONG_ARTIST + " TEXT," +
                IMAGE_PATH + " TEXT," +
                DURATION + " LONG," +
                FAVORITE + " INTEGER);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
