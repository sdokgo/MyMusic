package com.huybinh2k.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huybinh2k.mymusic.R;
import com.huybinh2k.mymusic.Song;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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