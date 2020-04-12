package com.menlhk.akps.view.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.menlhk.akps.view.ArsipFragment
import com.menlhk.akps.view.UsulanFragment

class FragmentAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm) {
    private val count  = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment = newInstanceUsulan()
        when (position) {
            0 -> fragment = newInstanceUsulan()
            1 -> fragment = newInstanceArsip()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if(position == 0){
            "Data Usulan"
        } else{
            "Arsip Usulan"
        }
    }
    override fun getCount(): Int {
        return count
    }

    companion object {

        fun newInstanceArsip(): ArsipFragment {
            val bindData = Bundle()

            val ArsipFragment = ArsipFragment()
            ArsipFragment.arguments = bindData
            return ArsipFragment
        }

        fun newInstanceUsulan(): UsulanFragment {
            val bindData = Bundle()

            val UsulanFragment = UsulanFragment()
            UsulanFragment.arguments = bindData
            return UsulanFragment
        }
    }
}