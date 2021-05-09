package com.huybinh2k.mymusic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import com.huybinh2k.mymusic.R;
import com.huybinh2k.mymusic.fragment.BaseSongListFragment;

import java.util.ArrayList;

/**
 * Create by BinhBH 5/8/2021
 */
public class ActivityMusic extends AppCompatActivity {

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



}