package com.example.myapplication.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

/**
 * Created by brant on 18-12-8.
 */

public class MusicAdapter extends BaseAdapter {

    private Context context;
    private List<Song> songs;

    public MusicAdapter(Context context,List<Song> songs){
        this.context = context;
        this.songs = songs;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Song getItem(int position) {

        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ){
            convertView = LayoutInflater.from(context).inflate(R.layout.music_list_item,null);
        }
        TextView tvSong = convertView.findViewById(R.id.tv_song);
        TextView tvSinger = convertView.findViewById(R.id.tv_singer);
        ImageView ivCover = convertView.findViewById(R.id.iv_cover);
        tvSinger.setText(getItem(position).getSinger());
        tvSong.setText(getItem(position).getSong());
        ivCover.setImageBitmap(getItem(position).getAlbum());
        return convertView;
    }
}
