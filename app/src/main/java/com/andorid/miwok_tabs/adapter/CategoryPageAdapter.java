package com.andorid.miwok_tabs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.andorid.miwok_tabs.fragment.ColorsFragment;
import com.andorid.miwok_tabs.fragment.FamilyFragment;
import com.andorid.miwok_tabs.fragment.NumbersFragment;
import com.andorid.miwok_tabs.fragment.PhrasesFragment;

/**
 * Created by Yvonne on 6/6/2017.
 */

public class CategoryPageAdapter extends FragmentPagerAdapter {

    public CategoryPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new NumbersFragment();
            case 1:
                return new FamilyFragment();
            case 2:
                return new ColorsFragment();
            case 3:
                return new PhrasesFragment();
            default:
                return null;
        }
    }
}
