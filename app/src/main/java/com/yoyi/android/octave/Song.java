package com.yoyi.android.octave;

import java.io.Serializable;

// Custom Song Class Inheriting Serializable Class to avoid runtime exception
public class Song implements Serializable {

    // Name of the Artist
    private String mArtist;

    // Name of the song
    private String mSongName;

    // Song Poster Image Resource ID
    private int mSongClipArtID;

    // Song Duration
    private int mSongDuration;

    /***
     * Media Class Constructor to initialise Song objects
     * @param artist stores artist's name
     * @param songName stores song's name
     * @param songClipArt stores song's image ID
     * @param songDuration stores song's duration
     */
    Song(String artist, String songName, int songClipArt, int songDuration) {

        this.mArtist = artist;
        this.mSongName = songName;
        this.mSongClipArtID = songClipArt;
        this.mSongDuration = songDuration;
    }

    // Getter Methods of the Song Class

    /***
     * Method retrieves the Song object's Artist name
     * @return Artist name
     */
    public String getmArtist() {
        return mArtist;
    }

    /***
     * Method retrieves the Song object's Song name
     * @return Song name
     */
    public String getmSongName() {
        return mSongName;
    }

    /***
     * Method retrieves the Song object's Poster Image ID
     * @return Song's Poster Image ID
     */
    public int getmSongClipArt() {
        return mSongClipArtID;
    }

    /***
     * Method retrieves the Song object's Song's Duration
     * @return Song's Duration
     */
    public int getmSongDuration() {
        return mSongDuration;
    }
}

