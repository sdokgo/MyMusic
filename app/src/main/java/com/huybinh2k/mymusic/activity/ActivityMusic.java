package com.huybinh2k.mymusic.activity;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huybinh2k.mymusic.R;
import com.huybinh2k.mymusic.Song;
import com.huybinh2k.mymusic.Utils;
import com.huybinh2k.mymusic.adapter.SongsAdapter;
import com.huybinh2k.mymusic.fragment.BaseSongListFragment;

/**
 * Create by BinhBH 5/8/2021
 */
public class ActivityMusic extends AppCompatActivity {
    private RelativeLayout mLayoutPlayBar;
    private ImageView mImagePause;
    private ImageView mImageSong;
    private TextView mTextViewSong;
    private TextView mTextViewSinger;
    private boolean mIsPlaying;
    private int mSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_song,
                new BaseSongListFragment()).commit();
    }

    private void initView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLayoutPlayBar = findViewById(R.id.layoutPlayBar);
        mImagePause = findViewById(R.id.playBar_Pause);
        mImageSong = findViewById(R.id.img_playBar);
        mTextViewSong = findViewById(R.id.song_name_playBar);
        mTextViewSinger = findViewById(R.id.singer_name_playBar);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSort = Utils.getInt(this, SongsAdapter.SORT_BY);
        if (mSort == SongsAdapter.SORT_BY_NAME){
            menu.findItem(R.id.sort_by_name).setTitle(getString(R.string.name));
        }else if (mSort == SongsAdapter.SORT_BY_NAME_DES){
            menu.findItem(R.id.sort_by_name).setTitle(getString(R.string.name_desc));
        }else if (mSort == SongsAdapter.SORT_BY_DURATION){
            menu.findItem(R.id.sort_by_duration).setTitle(getString(R.string.duration));
        }else if (mSort == SongsAdapter.SORT_BY_DURATION_DES){
            menu.findItem(R.id.sort_by_duration).setTitle(getString(R.string.duration_desc));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        initSearchView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Chỉnh sửa hình cho search view và sự kiện khi gõ text để tìm kiếm
     */
    private void initSearchView(Menu menu) {
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        ImageView closeSearch = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeSearch.setImageResource(R.drawable.ic_close);
        searchIcon.setImageResource(R.drawable.ic_search);
        searchView.setQueryHint(getString(R.string.enter_the_song_want_to_find));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FragmentManager fm = getSupportFragmentManager();

                if (fm.findFragmentById(R.id.frame_song) instanceof BaseSongListFragment){
                    BaseSongListFragment fragment = (BaseSongListFragment)fm.findFragmentById(R.id.frame_song);
                    assert fragment != null;
                    fragment.searchSong(newText);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int sort = mSort;
        switch (item.getItemId()){
            case R.id.sort_by_duration:
                if (mSort == SongsAdapter.SORT_BY_DURATION){
                    mSort = SongsAdapter.SORT_BY_DURATION_DES;
                }else {
                    mSort = SongsAdapter.SORT_BY_DURATION;
                }
                break;
            case R.id.sort_by_name:
                if (mSort == SongsAdapter.SORT_BY_NAME){
                    mSort = SongsAdapter.SORT_BY_NAME_DES;
                }else {
                    mSort = SongsAdapter.SORT_BY_NAME;
                }
                break;
        }
        if (sort != mSort){
            Utils.saveInt(this, mSort, SongsAdapter.SORT_BY);
            FragmentManager fm = getSupportFragmentManager();
            if (fm.findFragmentById(R.id.frame_song) instanceof BaseSongListFragment){
                BaseSongListFragment fragment = (BaseSongListFragment)fm.findFragmentById(R.id.frame_song);
                assert fragment != null;
                fragment.sortBy(mSort);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * cập nhật lại giao diên playbar
     */
    public void updateUIPlayBar(Song s) {
        mLayoutPlayBar.setVisibility(View.VISIBLE);
        mIsPlaying = true;
        updateImagePlayPause();
        mImageSong.setImageURI(Uri.parse(s.getImg()));
        mTextViewSinger.setText(s.getArtist());
        mTextViewSong.setText(s.getSongName());
    }

    /**
     * cập nhật image play/pause
     */
    private void updateImagePlayPause() {
        if (!mIsPlaying) {
            mImagePause.setImageResource(R.drawable.ic_play);
        } else {
            mImagePause.setImageResource(R.drawable.ic_pause);
        }
    }


}