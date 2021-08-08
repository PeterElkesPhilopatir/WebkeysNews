package com.peter.webkeysnews.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.peter.webkeysnews.databinding.FragmentNewsBinding


class NewsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewsBinding.inflate(inflater)
       val viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.photosGrid.adapter = PhotoGridAdapter(OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToDetailsFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }

}