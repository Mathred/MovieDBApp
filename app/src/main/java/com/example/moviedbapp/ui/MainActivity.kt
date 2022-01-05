package com.example.moviedbapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import com.example.moviedbapp.R
import com.example.moviedbapp.databinding.MainActivityBinding
import com.example.moviedbapp.utils.openHome
import com.example.moviedbapp.utils.openSettings

class MainActivity : AppCompatActivity() {

    private var _binding: MainActivityBinding? = null
    private val binding: MainActivityBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater).apply {
            setupTopAppBar()
        }
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun MainActivityBinding.setupTopAppBar() {
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> this@MainActivity.openHome()
                R.id.settings -> this@MainActivity.openSettings()
            }
            menuItem.isChecked = true
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}