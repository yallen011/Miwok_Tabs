package com.andorid.miwok_tabs.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.andorid.miwok_tabs.R;
import com.andorid.miwok_tabs.fragment.ColorsFragment;
import com.andorid.miwok_tabs.fragment.FamilyFragment;
import com.andorid.miwok_tabs.fragment.NumbersFragment;
import com.andorid.miwok_tabs.fragment.PhrasesFragment;



/**
 * Created by Yvonne on 6/6/2017.
 * {@link CategoryPageAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
 * each list item based on a data source which is a list of {@link com.andorid.miwok_tabs.Word} objects.
 */

public class CategoryPageAdapter extends FragmentPagerAdapter {


    /**
     * Create a new {@link CategoryPageAdapter} object.
     *
     * @param fm is the fragment manager that will keep each fragment's state in the adapter
     *           across swipes.
     */
    public CategoryPageAdapter(FragmentManager fm) {
        super(fm);
        //get the resource from context to get the category names in strings.xml
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 4;
    }


    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
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

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return  "Numbers";
            case 1:
                return "Family";
            case 2:
                return "Colors";
            case 3:
                return "Phrases";
            default:
                return null;
        }
    }


}
