package com.andorid.miwok_tabs.fragment;


import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.andorid.miwok_tabs.R;
import com.andorid.miwok_tabs.Word;
import com.andorid.miwok_tabs.adapter.WordAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {


    /**Handles playback for all the sound files **/
    private MediaPlayer mMediaPlayer;

    /**Handles Audio Focus for media playback**/
    AudioManager mAudioManager;

    /**Listener for handling audio focus changes **/
    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = getAudioFocusChangeCallback();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);


        //Create and setup the {@link AudioManager} to request audio focus
        //get the activity object instance that encloses the current Fragment
        //{@link NumbersActivity} first because {@link Fragment} does not have access to system
        //services
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        words.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        //use getActivity() got get the context(NumbersActivity) because NumbersFragment is not a
        //valid context
        WordAdapter adapter = new WordAdapter(getActivity(), words);


        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setBackgroundColor(Color.parseColor("#8800A0"));



        return rootView;
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
