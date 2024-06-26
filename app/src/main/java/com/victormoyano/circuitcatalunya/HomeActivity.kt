package com.victormoyano.circuitcatalunya

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.victormoyano.circuitcatalunya.databinding.HomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.configuracio -> {
                val intent = Intent(this, ConfiguracioActivity::class.java)
                startActivity(intent)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    companion object {
        private const val EXTRA_ID_LOGAT = "extra_id_logat"
        fun newIntent(context: Context, idLogat: Int): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(EXTRA_ID_LOGAT, idLogat)
            return intent
        }
    }

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

        // Asegúrate de que el archivo XML correcto se está inflando
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
                tab?.let {
                    binding.viewPager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)?.select()
            }
        })

        binding.viewPager.currentItem = 0

        // Asegúrate de que el infoImageView no sea nulo

    }



}
