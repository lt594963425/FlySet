package com.mvp.lt.flyset.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Pair;

import java.util.List;

/**
 * ViewPager适配器
 * @author 刘涛
 * @date 2017/6/2 0002
 */

public class FixPagerAdapter extends FragmentStatePagerAdapter {

    List<Pair<String, Fragment>> mItems;
    public FixPagerAdapter(FragmentManager fm, List<Pair<String, Fragment>> items) {
        super(fm);
        this.mItems =items;
    }




    @Override
    public Fragment getItem(int position) {
        return mItems.get(position).second;
    }



    @Override
    public int getCount() {
        return mItems.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mItems.get(position).first;
    }
}