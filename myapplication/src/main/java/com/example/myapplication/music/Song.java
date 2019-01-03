package com.example.myapplication.music;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by brant on 18-12-8.
 */

public class Song {
    private String song;        // song
    private String singer;      // singer
    private Bitmap album;          // album

    public Song(){}

    public Song(String song, String singer, Bitmap album) {
        this.song = song;
        this.singer = singer;
        this.album = album;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Bitmap getAlbum() {
        return album;
    }

    public void setAlbum(Bitmap album) {
        this.album = album;
    }
}
