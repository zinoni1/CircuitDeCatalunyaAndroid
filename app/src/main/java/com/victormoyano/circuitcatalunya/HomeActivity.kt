package com.victormoyano.circuitcatalunya

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.victormoyano.circuitcatalunya.databinding.HomeBinding
import com.google.android.material.tabs.TabLayout


class HomeActivity() : AppCompatActivity() {
    companion object {
        private const val EXTRA_ID_LOGAT = "extra_id_logat"
        fun newIntent(context: Context, idLogat: Int): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(EXTRA_ID_LOGAT, idLogat)
            return intent
        }}
    object IdLogatHolder {
        private var idLogat: Int = 0

        fun setIdLogat(id: Int) {
            idLogat = id
        }

        fun getIdLogat(): Int {
            return idLogat
        }
    }


    private lateinit var binding: HomeBinding
    private lateinit var pagerAdapter: HomeAdapter
    private var idLogat: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        idLogat = intent.getIntExtra(EXTRA_ID_LOGAT, 0)
        IdLogatHolder.setIdLogat(idLogat)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        pagerAdapter = HomeAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(posicio: Int) {
                super.onPageSelected(posicio)
                binding.tabLayout.getTabAt(posicio)!!.select()
            }
        })
        binding.viewPager.currentItem = 0
        fun getIdLogat(): Int {
            return idLogat
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }


}

