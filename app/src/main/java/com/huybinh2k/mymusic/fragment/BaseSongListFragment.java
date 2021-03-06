package com.huybinh2k.mymusic.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huybinh2k.mymusic.R;
import com.huybinh2k.mymusic.Song;
import com.huybinh2k.mymusic.Utils;
import com.huybinh2k.mymusic.activity.ActivityMusic;
import com.huybinh2k.mymusic.adapter.SongsAdapter;
import com.huybinh2k.mymusic.database.FavoriteSongDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by BinhBH 5/9/2021
 */
public class BaseSongListFragment extends Fragment {
    protected List<Song> mListSongs = new ArrayList<>();
    protected RecyclerView mRecyclerView;
    protected SongsAdapter mAdapter;
    protected Context mContext;
    protected FavoriteSongDAO mFavoriteSongDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_song_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListSongs = getSongList();
        mFavoriteSongDAO = new FavoriteSongDAO(mContext);
        mRecyclerView = view.findViewById(R.id.recycler_music);
        mAdapter = new SongsAdapter(mContext, mListSongs);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        OnItemClickSongListener();
        mAdapter.sort(Utils.getInt(mContext, SongsAdapter.SORT_BY));
    }

    /**
     * Đọc dữ liệu trong máy và add vào list
     */
    public ArrayList<Song> getSongList() {
        ArrayList<Song> arrayList = new ArrayList<>();
        ContentResolver musicResolver = mContext.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null) {
            musicCursor.moveToFirst();
            while (!musicCursor.isAfterLast()) {
                arrayList.add(new Song(musicCursor));
                musicCursor.moveToNext();
            }
            musicCursor.close();
        }
        return arrayList;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    /**
     * Set su kien click 1 item cho adapter
     */
    protected void OnItemClickSongListener(){
        mAdapter.setOnItemClickListener(new SongsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Song song = mListSongs.get(position);
                mAdapter.setPlayingId(song.getId());
                mAdapter.notifyDataSetChanged();
                if (getActivity() instanceof ActivityMusic){
                    ((ActivityMusic)getActivity()).updateUIPlayBar(song);
                }
            }
        });
    }

    /**
     * Đọc Tìm kiếm bài hát
     */
    public void searchSong(String s, boolean isFavorite) {
        ArrayList<Song> arrayList = new ArrayList<>();
        if (isFavorite){
            arrayList = mFavoriteSongDAO.searchFavorite(s);
        }else {
            ContentResolver musicResolver = mContext.getContentResolver();
            Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String where = MediaStore.Audio.Media.TITLE + " LIKE " + "'%"+ s + "%'";
            Cursor musicCursor = musicResolver.query(musicUri, null, where, null, null);
            if (musicCursor != null) {
                musicCursor.moveToFirst();
                while (!musicCursor.isAfterLast()) {
                    arrayList.add(new Song(musicCursor));
                    musicCursor.moveToNext();
                }
                musicCursor.close();
            }
        }
        mListSongs = arrayList;
        mAdapter.setList(arrayList);
        mAdapter.notifyDataSetChanged();
    }

    public void sortBy(int sort){
        mAdapter.sort(sort);
    }
}
