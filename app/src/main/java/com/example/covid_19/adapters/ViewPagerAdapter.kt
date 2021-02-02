package com.example.covid_19.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@Suppress("DEPRECATION")
class ViewPagerAdapter(fragmentManager: FragmentManager, private val mFragmentList: ArrayList<Fragment>
                       , private val mFragmentTitleList: ArrayList<String>):FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentTitleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }
}