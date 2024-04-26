package com.victormoyano.circuitcatalunya

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
class HomeAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    val fragments: MutableList<Fragment> = mutableListOf()

    override fun getItemCount(): Int {
        return 5
    }
    override fun createFragment(posicio: Int): Fragment {
        var frag: Fragment

        when (posicio) {
            0-> frag = HomeFragment()
            1-> frag = statsFragment()
            2-> frag = addtaskFragment()
            3-> frag = calendarioFragment()
            else -> frag = chatFragment()
        }
        fragments.add(frag)
        return frag
    }

    fun getFragmentStats(): statsFragment {
        return (fragments.get(1) as statsFragment)
    }
    fun getFragmentHome(): HomeFragment {
        return (fragments.get(0) as HomeFragment)
    }
    fun getFragmentaddTask(): addtaskFragment {
        return (fragments.get(2) as addtaskFragment)
    }
    fun getFragmentCalendario(): calendarioFragment {
        return (fragments.get(3) as calendarioFragment)
    }
    fun getFragmentChat(): chatFragment {
        return (fragments.get(4) as chatFragment)
    }
}