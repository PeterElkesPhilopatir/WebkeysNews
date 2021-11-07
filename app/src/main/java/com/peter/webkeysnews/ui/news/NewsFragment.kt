package com.peter.webkeysnews.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.peter.webkeysnews.databinding.FragmentNewsBinding
import com.peter.webkeysnews.network.ApiService
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlin.toString

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    lateinit var mainListAdapter: PhotoGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater)
//        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        viewModel =
            ViewModelProvider(
                this,
                NewsViewModelFactory(ApiService.getApiService())
            )[NewsViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupList()
        setupView()
//        binding.photosGrid.adapter = PhotoGridAdapter(OnClickListener {
//            viewModel.displayPropertyDetails(it)
//        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                findNavController().navigate(
                    NewsFragmentDirections.actionNewsFragmentToDetailsFragment(
                        it
                    )
                )
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }

    private fun setupView() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                mainListAdapter.submitData(it)
            }
        }
    }

    private fun setupList() {
        mainListAdapter = PhotoGridAdapter(OnClickListener {
            viewModel.displayPropertyDetails(it)
        })
        binding.photosGrid.apply {
            adapter = mainListAdapter
        }
        mainListAdapter.addLoadStateListener {
            if (it.refresh == LoadState.Loading) {
                Toast.makeText(context,"Loading",Toast.LENGTH_SHORT).show()
            }

        }
    }

}