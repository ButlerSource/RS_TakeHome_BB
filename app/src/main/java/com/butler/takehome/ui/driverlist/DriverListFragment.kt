package com.butler.takehome.ui.driverlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.butler.takehome.R
import com.butler.takehome.domain.model.Driver
import com.butler.takehome.databinding.FragmentDriverListBinding
import com.butler.takehome.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriverListFragment : Fragment() {

    private lateinit var driverAdapter: DriverAdapter
    private var _binding: FragmentDriverListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = FragmentDriverListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        driverAdapter = DriverAdapter { driver -> adapterOnClick(driver) }
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    //TODO: Setup Empty Placeholder when Driver list is empty
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbarMenu()
        binding.driverList.adapter = driverAdapter

        viewModel.driver.observe(viewLifecycleOwner) {
            driverAdapter.addDrivers(it)
        }
    }

    override fun onResume() {
        super.onResume()
        driverAdapter.clearList()
        viewModel.loadDrivers()
    }

    private fun setupToolbarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.driver_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.sortByName -> {
                        driverAdapter.clearList()
                        viewModel.onSortByLastName()
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner)
    }

    private fun adapterOnClick(driver: Driver) {
        viewModel.onDriverSelected(driver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}