package com.butler.takehome.ui.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.butler.takehome.data.model.Route
import com.butler.takehome.databinding.FragmentRouteBinding

class RouteFragment(private val route: Route): Fragment() {

    private var _binding: FragmentRouteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        _binding = FragmentRouteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.routeName.text = "Name: ${route.name}"
        binding.routeType.text = "Type: ${route.type}"
        binding.routeId.text = "ID: ${route.id}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}