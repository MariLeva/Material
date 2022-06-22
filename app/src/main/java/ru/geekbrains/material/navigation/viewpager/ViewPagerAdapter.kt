package ru.geekbrains.material.navigation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.geekbrains.material.navigation.EarthFragment
import ru.geekbrains.material.navigation.MarsFragment
import ru.geekbrains.material.navigation.SystemFragment
import ru.geekbrains.material.navigation.TehTransferFragment

class ViewPagerAdapter (private val fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val fragment = arrayOf(EarthFragment(), MarsFragment(), TehTransferFragment(), SystemFragment())

    override fun getCount(): Int {
        return fragment.size
    }

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Earth"
            1 -> "Mars"
            2 -> "NASA"
            else -> "System"
        }
    }

}