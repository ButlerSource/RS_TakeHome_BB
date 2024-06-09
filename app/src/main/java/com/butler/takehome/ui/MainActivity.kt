package com.butler.takehome.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.butler.takehome.R
import com.butler.takehome.databinding.ActivityMainBinding
import com.butler.takehome.ui.driverlist.DriverListFragment
import com.butler.takehome.ui.route.RouteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    //TODO Use Navigation Jetpack library
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, DriverListFragment())
        ft.addToBackStack(null)
        ft.commit()

        viewModel.routeForDriver.observe(this){
            val ftRoute = supportFragmentManager.beginTransaction()
            ftRoute.replace(R.id.fragment_container, RouteFragment(it))
            ftRoute.addToBackStack("RouteScreen")
            ftRoute.commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            } else -> super.onOptionsItemSelected(item)

        }
    }
}