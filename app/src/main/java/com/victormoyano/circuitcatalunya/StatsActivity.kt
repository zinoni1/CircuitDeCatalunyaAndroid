package com.victormoyano.circuitcatalunya

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class StatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stats)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    /*  override fun onOptionsItemSelected(item: MenuItem): Boolean {
          when (item.itemId) {
              R.id.configuracio -> {
                  val intent = Intent(this, ConfiguracioActivity::class.java)
                  startActivity(intent)
                  finish()
                  return true
              }
              else -> return super.onOptionsItemSelected(item)
          }
      }*/
}