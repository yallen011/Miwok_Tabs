package com.andorid.miwok_tabs.fragment;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.andorid.miwok_tabs.R;
import com.andorid.miwok_tabs.Word;
import com.andorid.miwok_tabs.adapter.WordAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    /**Handles playback for all the sound files **/
    private MediaPlayer mMediaPlayer;

    /**Handles Audio Focus for media playback**/
    AudioManager mAudioManager;

    /**Listener for handling audio focus changes **/
    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = getAudioFocusChangeCallback();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);


        //Create and setup the {@link AudioManager} to request audio focus
        //get the activity object instance that encloses the current Fragment
        //{@link MainActivity} first because {@link Fragment} does not have access to system
        //services
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        //made the arraylist final so that it could be accessed inside the onItemClick method.
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        //use getActivity() got get the context(MainActivity) because NumbersFragment is not a
        //valid context
        WordAdapter adapter = new WordAdapter(getActivity(), words);


        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        //listView.setBackgroundColor(R.color.category_numbers);
        listView.setBackgroundColor(Color.parseColor("#FD8E09"));
        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        //plays the audio for number one when any item in the list is clicked click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //release current resources being used for media player if it currently exists
                //because we are about to play a different sound file.
                releaseMediaPlayer();

                //get the word located in the list that is at same position as the item clicked in the list
                Word currentWord = words.get(position);

                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request temporary focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //create the medial player with the audio file that is stored in the list for that word.
                    mMediaPlayer = MediaPlayer.create(getActivity(), currentWord.getmMiwokAudio());
                    //play the file
                    mMediaPlayer.start();

                    //listener to stop and release the media player and resources being used
                    // once the sounds has finished playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }

            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        //stop and release media player when user navigates away from the activity and the activity
        //is stopped
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }

    private AudioManager.OnAudioFocusChangeListener getAudioFocusChangeCallback(){

        return new AudioManager.OnAudioFocusChangeListener() {
            Handler mHandler = new Handler();
            public void onAudioFocusChange(int focusChange) {

                // The AUDIOFOCUS_LOSS case means we've lost audio focus permanently
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    // Stop playback and clean up resources
                    releaseMediaPlayer();
                }
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time.
                else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                    // Pause playback immediately
                    mMediaPlayer.pause();
                    // play the word from the beginning when we resume playback.
                    mMediaPlayer.seekTo(0);
                }
                //The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume.
                else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    // Lower the volume, keep playing
                    mAudioManager.adjustVolume(AudioManager.ADJUST_LOWER, 0);
                }
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    // Raise volume to normal, restart playback if necessary
                    mMediaPlayer.start();
                    mAudioManager.adjustVolume(AudioManager.ADJUST_RAISE, 0);

                }
            }
        };
    }

    /** This listener gets triggered when the {@link MediaPlayer} has completed playing
     * the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
}
