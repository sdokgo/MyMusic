package com.huybinh2k.mymusic.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huybinh2k.mymusic.R;
import com.huybinh2k.mymusic.database.FavoriteSongDAO;

public class FavoriteSongsFragment extends BaseSongListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListSongs = mFavoriteSongDAO.listFavorite();
        mAdapter.setIsFavorite(true);
        mAdapter.setList(mListSongs);
        mAdapter.notifyDataSetChanged();
        if (mListSongs.isEmpty()){
            Toast.makeText(getContext(), R.string.add_song_to_favorite, Toast.LENGTH_SHORT).show();
        }
    }



}
