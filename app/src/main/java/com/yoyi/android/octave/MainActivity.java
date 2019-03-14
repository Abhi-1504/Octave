package com.yoyi.android.octave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // Creating ArrayList to store the Song class objects
    private ArrayList<Song> songList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting the title of the Activity to suitable name
        this.setTitle(getResources().getString(R.string.Songs));

        // Calling songAdder function to add the songs in the ArrayList
        songAdder();


        // Creating ListView Object
        ListView songListView = findViewById(R.id.song_list_view);

        // Creating Custom ArrayAdapter(SongAdapter) object to store the ArrayList object
        final SongAdapter songAdapter = new SongAdapter(this, songList);

        // Attaching the custom ArrayAdapter to the ListView Object
        songListView.setAdapter(songAdapter);

        // Setting on Click event listeners to the ListView
        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {

                // Creating intent to be called on clicking the ListView Item
                Intent intent = new Intent(MainActivity.this, now_playing_activity.class);

                // Adding the ArrayList and the position of the ListView item in the extras of intent to pass it to the new activity
                intent.putExtra("songList", songList);
                intent.putExtra("position", position);

                // Starting the new activity
                startActivity(intent);

            }
        });

    }

    /***
     * Method Creates new Song class objects and simultaneously adds them to the ArrayList
     */
    public void songAdder() {
        songList.add(new Song("The Chainsmokers", "All We know", R.drawable.all_we_know, 196));
        songList.add(new Song("Fall Out Boy", "Irresistible", R.drawable.irresistble, 271));
        songList.add(new Song("Gajendra Varma", "Ik Kahani", R.drawable.ik_kahani, 207));
        songList.add(new Song("Sean Paul", "Mad Love", R.drawable.mad_love, 200));
        songList.add(new Song("Louis Fonsi and Demi Levato", "Enchame la Culpa", R.drawable.enchame_la_culpa, 211));
        songList.add(new Song("A. R. Rehman", "Dil Se Re", R.drawable.dil_se_re, 425));
        songList.add(new Song("Sukhwinder Singh", "Chaiya Chaiya", R.drawable.chaiya, 391));
        songList.add(new Song("Sia", "Chandelier", R.drawable.chandelier, 232));
        songList.add(new Song("Usher", "Without You", R.drawable.without_you, 191));
        songList.add(new Song("Coldplay", "Hymn for the Weekend", R.drawable.hymn_for_the_weekend, 261));
    }
}
