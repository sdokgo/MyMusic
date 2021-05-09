package com.huybinh2k.mymusic.adapter;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huybinh2k.mymusic.R;
import com.huybinh2k.mymusic.Song;

import java.io.File;
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
    private static OnItemClickListener mListener;

    public SongsAdapter(@NonNull Context context, @NonNull List<Song> objects) {
        this.mContext = context;
        this.mList = objects;
    }

    public void setPlayingId(int mPlayingIdProvider) {
        this.mPlayingId = mPlayingIdProvider;
    }


    public void setList(List<Song> mList) {
        this.mList = mList;
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
        holder.imageMore.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopupMenu(v, song);
            }
        });
    }

    private void createPopupMenu(View v, final Song song) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
            popupMenu.getMenuInflater().inflate(R.menu.item_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.deleteSong) {
                        deleteSong(song);
                    }
                    return true;
                }
            });
        popupMenu.show();
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    /**
     * @param song to delete
     * Xóa 1 bài hát
     */
    private void deleteSong(final Song song){
        if (song.getId() == mPlayingId){
            Toast.makeText(mContext, R.string.cannot_del, Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.delete_song);
        builder.setMessage(mContext.getString(R.string.question_delete) + song.getSongName() +" ?");
        builder.setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentResolver resolver = mContext.getContentResolver();
                try {
                    Uri uri =  MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    String where = MediaStore.Audio.Media._ID + " = " + song.getId();
                    resolver.delete(uri, where, null);
                    File fileDelete = new File(song.getSongPath());
                    if (fileDelete.exists()) {
                        if (fileDelete.delete()) {
                            Toast.makeText(mContext, "Delete Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                    mList.remove(song);
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Delete Failure", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

}
