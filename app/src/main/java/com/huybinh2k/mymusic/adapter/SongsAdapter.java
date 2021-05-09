package com.huybinh2k.mymusic.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huybinh2k.mymusic.R;
import com.huybinh2k.mymusic.Song;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Create by BinhBH 5/9/2021
 */
public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {
    private Context mContext;
    private List<Song> mList;
    private int mPlayingId = -1;

    public SongsAdapter(@NonNull Context context, @NonNull List<Song> objects) {
        this.mContext = context;
        this.mList = objects;
    }

    public void setPlayingId(int mPlayingIdProvider) {
        this.mPlayingId = mPlayingIdProvider;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Song song = mList.get(position);
        if (song.getId() == mPlayingId) {
            holder.id.setVisibility(View.INVISIBLE);
            holder.songName.setTypeface(Typeface.DEFAULT_BOLD);
            holder.imageId.setVisibility(View.VISIBLE);
        } else {
            holder.id.setVisibility(View.VISIBLE);
            holder.songName.setTypeface(Typeface.DEFAULT);
            holder.imageId.setVisibility(View.INVISIBLE);
        }
        //BinhBH hiển thị số thứ tứ position từ 0 -> +1
        holder.id.setText(String.valueOf(position+1));
        holder.songName.setText(song.getSongName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.ENGLISH);
        String duration = simpleDateFormat.format(song.getDuration());
        holder.time.setText(duration);
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView songName;
        private TextView time;
        private ImageView imageId;
        private ImageView imageMore;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.text_stt);
            songName = itemView.findViewById(R.id.tenbaihat);
            time = itemView.findViewById(R.id.duration);
            imageId = itemView.findViewById(R.id.img_stt);
            imageMore = itemView.findViewById(R.id.three_dots);
        }
    }

}
