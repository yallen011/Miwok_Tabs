package com.andorid.miwok_tabs.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andorid.miwok_tabs.R;
import com.andorid.miwok_tabs.adapter.CategoryPageAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new CategoryPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
   }
}
