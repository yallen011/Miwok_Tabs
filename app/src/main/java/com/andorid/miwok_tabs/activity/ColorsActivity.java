package com.andorid.miwok_tabs.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andorid.miwok_tabs.R;
import com.andorid.miwok_tabs.fragment.ColorsFragment;

public class ColorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory);

        //replaces the content in the activity with the NumbersFragment.
        //the layout with the id "container" has match_parent for width and height so the
        //fragment will take the whole screen
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ColorsFragment())
                .commit();
    }
}
