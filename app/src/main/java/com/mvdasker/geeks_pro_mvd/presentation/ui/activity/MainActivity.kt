package com.mvdasker.geeks_pro_mvd.presentation.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aghajari.zoomhelper.ZoomHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mvdasker.geeks_pro_mvd.R
import com.mvdasker.geeks_pro_mvd.databinding.ActivityMainBinding
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.malfunctions.ServerStatus
import com.mvdasker.geeks_pro_mvd.presentation.ui.fragments.malfunctions.ServerStatusViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val serverStatusViewModel: ServerStatusViewModel by viewModels()

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

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
            R.id.libraryFragment,R.id.documentsFragment,
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
//        serverStatusViewModel.serverStatus.observe(this) { status ->
//            if (status == ServerStatus.UNAVAILABLE) {
//                if (navController.currentDestination?.id != R.id.malfunctionsFragment &&
//                    navController.currentDestination?.id != R.id.authorizationFragment &&
//                    navController.currentDestination?.id != R.id.splashFragment
//                ) {
//                    navController.navigate(R.id.malfunctionsFragment)
//                }
//            } else {
//                if (navController.currentDestination?.id == R.id.malfunctionsFragment) {
//                    navController.popBackStack()
//                }
//            }
//        }
//
//        serverStatusViewModel.startCheckingServerStatus()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return ZoomHelper.getInstance().dispatchTouchEvent(ev!!, this) || super.dispatchTouchEvent(ev)
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

    override fun onDestroy() {
        super.onDestroy()
        window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }
}