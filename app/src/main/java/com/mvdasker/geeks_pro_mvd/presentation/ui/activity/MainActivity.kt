package com.mvdasker.geeks_pro_mvd.presentation.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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

        binding.bottomNavigation.setOnItemSelectedListener { _ ->
            updateIcon()
            true
        }

        val fragmentsWithBottomNav = setOf(
            R.id.homeFragment,
            R.id.libraryFragment,
            R.id.documentsFragment,
            R.id.menuFragment
        )

        val fragmentWithoutBottomNav = setOf(
            R.id.splashFragment,
            R.id.authorizationFragment
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isFragmentWithoutBottomNav = fragmentWithoutBottomNav.contains(destination.id)
            navView.visibility = if (isFragmentWithoutBottomNav) View.GONE else View.VISIBLE

            if (fragmentsWithBottomNav.contains(destination.id)) {
                showBottomNavigationView(navView)
                supportActionBar?.show()
            } else {
                hideBottomNavigationView(navView)
                supportActionBar?.hide()
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

    private fun hideBottomNavigationView(view: BottomNavigationView) {
        view.clearAnimation()
        view.animate().translationY(300f).setDuration(300)
    }

    private fun showBottomNavigationView(view: BottomNavigationView) {
        view.clearAnimation()
        view.animate().translationY(0f).setDuration(300)
    }
}