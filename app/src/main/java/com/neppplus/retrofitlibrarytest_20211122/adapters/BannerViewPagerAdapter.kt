package com.neppplus.retrofitlibrarytest_20211122.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.neppplus.retrofitlibrarytest_20211122.datas.BannerData
import com.neppplus.retrofitlibrarytest_20211122.fragments.BannerFragment

class BannerViewPagerAdapter(fm: FragmentManager, val mBannerList: List<BannerData>) : FragmentPagerAdapter(fm) {
    override fun getCount() = mBannerList.size

    override fun getItem(position: Int): Fragment {

        return BannerFragment(mBannerList[position])

    }
}