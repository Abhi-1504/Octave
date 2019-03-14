package com.yoyi.android.octave;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class now_playing_activity extends AppCompatActivity {

    // Creating all the objects for the Views of Now Playing Screen

    // Constant String Format for Timer TextView
    private static final String FORMAT = "%02d:%02d";
    // Variable to track the current position of seek bar
    int seekBarPositionCatcher;
    private TextView songName;
    private TextView artistName;
    private ImageView songImage;
    private Button playPauseButton;
    private Button forwardButton;
    private Button rewindButton;
    private TextView totalTimeDuration;
    private SeekBar songSeekBar;
    // Variable to check play and pause
    private boolean isPlaying;
    // Variable to track current position song being played or paused
    private int songListPositionTracker;
    // Variable to check first initialisation of countdown timer
    private boolean isFirstTimeIntialisation;
    // Variable to store time in milli seconds for countdown timer
    private long timeInMilliSeconds;
    // Variable to implement CountDownTimer interface
    private CountDownTimer counter;
    // Variable to check seek bar calling a method
    private boolean isCalledBySeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing_activity);

        // Enabled Up Button in Now Playing activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting the title of the Activity to suitable name
        this.setTitle(getResources().getString(R.string.now_playing));

        // Extracting the ArrayList passed from the calling intent in the extras in current intent
        final ArrayList<Song> songList = (ArrayList<Song>) getIntent().getSerializableExtra("songList");

        // Extracting the current position of the ListView Item object
        songListPositionTracker = getIntent().getIntExtra("position", 0);

        // Linking the now playing views to their respective objects
        songName = findViewById(R.id.song_name_now_playing);

        artistName = findViewById(R.id.artist_name_now_playing);

        songImage = findViewById(R.id.song_image_now_playing);

        // Setting the linked views to current Song object Values
        songName.setText(songList.get(songListPositionTracker).getmSongName());

        artistName.setText(songList.get(songListPositionTracker).getmArtist());

        songImage.setImageResource(songList.get(songListPositionTracker).getmSongClipArt());

        // Linking the play/pause, previous, forward Buttons to objects
        rewindButton = findViewById(R.id.rewind_button);

        playPauseButton = findViewById(R.id.play_and_pause_button);

        forwardButton = findViewById(R.id.forward_button);

        totalTimeDuration = findViewById(R.id.total_time_duration_text_view);

        songSeekBar = findViewById(R.id.song_duration_seek_bar);

        isFirstTimeIntialisation = true;

        isPlaying = true;

        // Setting the max duration of the seek bar to the current song total duration
        songSeekBar.setMax(songList.get(songListPositionTracker).getmSongDuration());

        // Displaying a toast message of playing
        Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();

        // Starting the Timer
        startTimer(songList);

        // Setting On Click Listener to the rewind button
        rewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calling the rewind method to perform the rewind functionality
                rewindMethod(songList);
            }
        });

        // Setting On Click Listener to the play and pause button
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calling the play and pause method to perform the rewind functionality
                playAndPauseMethod(songList);
            }
        });

        // Setting On Click Listener to the forward button
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forwardMethod(songList);
            }
        });

        // Setting on click event listener to the seek bar
        songSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean fromTouch) {

                // Checking if the progress in the seek bar was from user
                if (fromTouch) {
                    // Pausing the timer
                    pauseTimer();

                    // Setting seek bar to the position user dragged
                    seekBar.setProgress(position);

                    // Setting variable values for start timer method input and starting time
                    isFirstTimeIntialisation = true;
                    isCalledBySeekBar = true;
                    seekBarPositionCatcher = position;

                    // Starting the Timer
                    startTimer(songList);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Auto generated functions of interface

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Auto generated Functon of interface
            }
        });

    }

    /***
     * Performs the Functionality of rewind button
     * @param songArrayList ArrayList storing the Song Object
     */
    private void rewindMethod(ArrayList<Song> songArrayList) {

        // Checking for the edge case of the first index of ArrayList and moving to the last index accordingly
        if (songListPositionTracker == 0)
            songListPositionTracker = songArrayList.size() - 1;
        else
            songListPositionTracker--;

        // Displaying a toast message of playing previous song
        Toast.makeText(this, "Playing previous song", Toast.LENGTH_SHORT).show();

        // Assigning the Now Playing View values as per the new Song being played
        songName.setText(songArrayList.get(songListPositionTracker).getmSongName());

        artistName.setText(songArrayList.get(songListPositionTracker).getmArtist());

        songImage.setImageResource(songArrayList.get(songListPositionTracker).getmSongClipArt());

        // Setting the max duration of the seek bar to be the total duration of the song being played
        songSeekBar.setMax(songArrayList.get(songListPositionTracker).getmSongDuration());

        // Changing play/pause button state
        playPauseButton.setBackgroundResource(R.drawable.ic_pause);

        // resetting the countdown timer
        resetTimer();

        // Starting the countdown timer
        startTimer(songArrayList);

    }

    /***
     * Performs the functionality of play/pause button
     * @param songArrayList ArrayList storing Song objects
     */
    private void playAndPauseMethod(ArrayList<Song> songArrayList) {

        songSeekBar.setMax(songArrayList.get(songListPositionTracker).getmSongDuration());

        // Checking for Play or Pause state and performing the functionality accordingly
        if (!isPlaying) {
            // Changing the state off the button
            playPauseButton.setBackgroundResource(R.drawable.ic_pause);

            // Starting the countdown timer
            startTimer(songArrayList);

            // Changing the state of the checker variable
            isPlaying = true;

            // Displaying a toast message of playing
            Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();

        } else {
            // Changing the state off the button
            playPauseButton.setBackgroundResource(R.drawable.ic_play_arrow);

            // Pausing the countdown timer
            pauseTimer();

            // Changing the state of the checker variable
            isPlaying = false;

            // Displaying a toast message of pausing
            Toast.makeText(this, "Paused", Toast.LENGTH_SHORT).show();
        }

    }

    /***
     * Performs the Functionality of forward button
     * @param songArrayList
     */
    private void forwardMethod(ArrayList<Song> songArrayList) {
        // Checking for the edge case of the last index of ArrayList and moving to the first index accordingly
        if (songListPositionTracker == songArrayList.size() - 1)
            songListPositionTracker = 0;
        else
            songListPositionTracker++;

        // Displaying toast message of playing forward song
        Toast.makeText(this, "Playing next song", Toast.LENGTH_SHORT).show();

        // Setting Now Playing views to the current Song object values
        songName.setText(songArrayList.get(songListPositionTracker).getmSongName());

        artistName.setText(songArrayList.get(songListPositionTracker).getmArtist());

        songImage.setImageResource(songArrayList.get(songListPositionTracker).getmSongClipArt());

        // Setting the max duration of the seek bar to be the total duration of the song being played
        songSeekBar.setMax(songArrayList.get(songListPositionTracker).getmSongDuration());

        // Changing Play/Pause button state
        playPauseButton.setBackgroundResource(R.drawable.ic_pause);

        // resetting the timer
        resetTimer();

        // starting the timer
        startTimer(songArrayList);

    }

    /***
     * Method starts the countdown timer ,the starting time being either the current Song duration or the seek bar progress changes made by user
     * @param songArrayList ArrayList containing the Song objects
     */
    private void startTimer(final ArrayList<Song> songArrayList) {

        // Calling method to check first time initialisation of timer and assigning values accordingly
        firstTimeIntializationOfTimer(songArrayList.get(songListPositionTracker).getmSongDuration());

        // Implementing CountDownTimer Interface
        counter = new CountDownTimer(timeInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeInMilliSeconds = millisUntilFinished;

                // Changing the Progress of seek bar based on the time lapsed
                songSeekBar.setProgress((int) (songArrayList.get(songListPositionTracker).getmSongDuration() - (timeInMilliSeconds / 1000)));

                // Setting the remaining left time to the total duration text view
                totalTimeDuration.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                // Resetting the timer on finishing of current song and moving to next song
                resetTimer();
                forwardMethod(songArrayList);
            }
        }.start();
        isPlaying = true;
    }

    /***
     * Pauses the countdown timer
     */
    private void pauseTimer() {
        // Cancelling the countdown timer
        counter.cancel();

        // Changing the checker variables state accordingly
        isFirstTimeIntialisation = false;
        isPlaying = false;
    }

    /***
     * Resets the countdown timer
     */
    private void resetTimer() {

        // Cancelling the timer
        counter.cancel();

        // Resetting the seek bar progress
        songSeekBar.setProgress(0);

        // resetting the tracker and checkers variables accordingly
        seekBarPositionCatcher = 0;
        timeInMilliSeconds = 0;
        isPlaying = false;
        isFirstTimeIntialisation = true;
    }

    /***
     * Intialises the start time of timer based new current song being played and change in progress of seek bar by the user
     * @param songDuration song duration of the current song
     */
    private void firstTimeIntializationOfTimer(int songDuration) {

        // Checking the state of first time initialisation and seek bar progress change by the user
        if (isFirstTimeIntialisation && !isCalledBySeekBar) {
            timeInMilliSeconds = (long) (songDuration * 1000);
            isFirstTimeIntialisation = false;
        } else if (isFirstTimeIntialisation && isCalledBySeekBar) {
            timeInMilliSeconds = (long) ((songDuration - seekBarPositionCatcher) * 1000);
            isFirstTimeIntialisation = false;
            isCalledBySeekBar = false;
        }
    }

}
