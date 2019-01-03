package com.example.myapplication.music;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brant on 18-12-8.
 */

public class MusicUtil {

    public static List<Song> getSongList(Context context){
        List<Song> songs = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        if( cursor != null ) {
            while (cursor.moveToNext()) {
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                int albumArt = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID));
                Log.e("MusicUtil","displayName : "+displayName+"  artist : "+artist+"  albumArt : "+albumArt);
                Bitmap bmCover = getAlbumArt(context,albumArt);
                Song song = new Song(displayName,artist,bmCover);
                songs.add(song);
            }
        }
        return songs;
    }

    public static Bitmap getAlbumArt(Context context,int albumId){
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = context.getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(albumId)), projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        Bitmap bm = null;
        if (album_art != null) {
            bm = BitmapFactory.decodeFile(album_art);
        } else {
            bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
        }
        return bm;

    }
}
