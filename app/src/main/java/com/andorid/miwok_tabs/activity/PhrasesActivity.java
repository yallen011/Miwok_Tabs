package com.andorid.miwok_tabs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andorid.miwok_tabs.R;
import com.andorid.miwok_tabs.fragment.PhrasesFragment;

public class PhrasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory);

        //replaces the content in the activity with the NumbersFragment.
        //the layout with the id "container" has match_parent for width and height so the
        //fragment will take the whole screen
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new PhrasesFragment())
                .commit();
    }
}
