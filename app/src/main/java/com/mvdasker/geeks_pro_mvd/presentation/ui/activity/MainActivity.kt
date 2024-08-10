package com.mvdasker.geeks_pro_mvd.presentation.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigation
        val navController = findNavController(R.id.nav_host_fragment)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            updateIcon()
            true
        }

        val fragmentsWithoutBottomNav = setOf(
            R.id.homeFragment,
            R.id.libraryFragment,
            R.id.documentsFragment,
            R.id.menuFragment
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (!fragmentsWithoutBottomNav.contains(destination.id)) {
                navView.isVisible = false
                supportActionBar?.hide()
            } else {
                navView.isVisible = true
                supportActionBar?.show()
            }
        }

        navView.setupWithNavController(navController)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            controller?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun updateIcon() {
        val icons = mapOf(
            R.id.homeFragment to R.drawable.home_selector_vectors,
            R.id.libraryFragment to R.drawable.library_vectors,
            R.id.documentsFragment to R.drawable.doc_vectors,
        )

        for ((id, icon) in icons) {
            val menuItem = binding.bottomNavigation.menu.findItem(id)
            menuItem.setIcon(icon)
        }
    }

}