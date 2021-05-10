package com.huybinh2k.mymusic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huybinh2k.mymusic.Song;

import java.util.ArrayList;

/**
 * Create by BinhBH 5/10/2021
 */
public class FavoriteSongDAO {
    private final SQLiteDatabase mDatabase;

    public FavoriteSongDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        this.mDatabase = helper.getWritableDatabase();
    }

    /** thêm bài hát yêu thích
     * @return true nếu thêm thành công, false nếu thất bại
     */
    public boolean insertFavorite(Song song) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ID, song.getId());
        values.put(DatabaseHelper.SONG_NAME, song.getSongName());
        values.put(DatabaseHelper.SONG_PATH, song.getSongPath());
        values.put(DatabaseHelper.SONG_ARTIST, song.getArtist());
        values.put(DatabaseHelper.IMAGE_PATH, song.getImg());
        values.put(DatabaseHelper.DURATION, song.getDuration());
        values.put(DatabaseHelper.FAVORITE, DatabaseHelper.IS_FAVORITE);
        return mDatabase.insert(DatabaseHelper.FAVORITE_SONGS_TABLE, null, values) > 0;
    }

    /** sửa bài hát yêu thích
     * @return true nếu sửa thành công, false nếu thất bại
     */
    public boolean updateFavorite(Song song) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FAVORITE, song.isFavorite() ? 1 : 0);
        String whereClause = DatabaseHelper.ID + "=?";
        String[] whereArgs = {String.valueOf(song.getId())};
        return mDatabase.update(DatabaseHelper.FAVORITE_SONGS_TABLE, values, whereClause, whereArgs) > 0;
    }

    /** xóa bài hát yêu thích
     * @return true nếu xóa thành công, false nếu thất bại
     */
    public boolean delete(int idProvider) {
        String whereClause = DatabaseHelper.ID + "=?";
        String whereArgs[] = {String.valueOf(idProvider)};
        return mDatabase.delete(DatabaseHelper.FAVORITE_SONGS_TABLE, whereClause, whereArgs) > 0;
    }

    /**
     * @return dánh sách bài hát yêu thích
     */
    public ArrayList<Song> listFavorite() {
        ArrayList<Song> list = new ArrayList<>();
        String where = DatabaseHelper.FAVORITE + "=?";
        String[] args = {"1"};
        Cursor cursor = mDatabase.query(DatabaseHelper.FAVORITE_SONGS_TABLE, null, where, args, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new Song(cursor, true));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    /**
     * Tìm kiếm bài hát trong favorites
     */
    public ArrayList<Song> searchFavorite(String s) {
        ArrayList<Song> list = new ArrayList<>();
        String where = DatabaseHelper.SONG_NAME + " LIKE ?";
        String[] args = {"%"+ s + "%"};
        Cursor cursor = mDatabase.query(DatabaseHelper.FAVORITE_SONGS_TABLE, null, where, args, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new Song(cursor, true));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
}
