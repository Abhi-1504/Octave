package com.yoyi.android.octave;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// SongAdapter - Custom ArrayAdapter Class
public class SongAdapter extends ArrayAdapter<Song> {

    /***
     * Custom ArrayAdapter (SongAdapter) constructor
     * @param context To set the context as per the Super Class ArrayAdapter constructor requirement
     * @param songArrayList ArrayList to be stored in the custom ArrayAdapter
     */
    public SongAdapter(Activity context, ArrayList<Song> songArrayList) {

        // Calling Super Class Constructor
        super(context, 0, songArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Creating Song object to hold current object stored in the custom ArrayAdapter
        Song currentSong = getItem(position);

        View listItemView = convertView;

        // Constant string format
        final String FORMAT = "%02d:%02d";

        // Checking if the ListView is empty and inflating it with custom view
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.song_list_activity, parent, false);
        }

        // Creating Objects for the custom views and setting Values as per the Current position Song Object stored in adapter
        TextView songNameTextView = listItemView.findViewById(R.id.song_name);

        songNameTextView.setText(currentSong.getmSongName());

        TextView artistNameTextView = listItemView.findViewById(R.id.artist_name);

        artistNameTextView.setText(currentSong.getmArtist());

        ImageView songImage = listItemView.findViewById(R.id.song_image);

        songImage.setImageResource(currentSong.getmSongClipArt());

        TextView songDuration = listItemView.findViewById(R.id.song_duration);

        // Converting total song duration into Minutes and Seconds and setting in mm:ss format tothe TextView
        songDuration.setText("" + String.format(FORMAT, currentSong.getmSongDuration() / 60, currentSong.getmSongDuration() % 60));

        // returns the inflated ListView ith items
        return listItemView;
    }
}
