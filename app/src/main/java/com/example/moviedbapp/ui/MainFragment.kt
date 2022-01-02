package com.example.moviedbapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedbapp.R
import com.example.moviedbapp.databinding.MainFragmentBinding
import com.example.moviedbapp.extensions.openMovie
import com.example.moviedbapp.extensions.showErrorLoadingDialog
import com.example.moviedbapp.ui.rvadapter.MovieCategoryAdapter
import com.example.moviedbapp.viewmodel.AppState
import com.example.moviedbapp.viewmodel.MainViewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val onMovieClick = object : OnMovieClickListener {
        override fun onMovieClick(id: Int) {
            openMovie(id)
        }
    }

    private val onShowMoreClick = object : OnShowMoreClickListener {
        override fun onShowMoreClick(category: String) {
            //TODO("Not yet implemented")
        }
    }

    private val adapter by lazy {
        MovieCategoryAdapter(requireContext(), onMovieClick, onShowMoreClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            initVm()
            initViews()
            initObservers()
            requestData()
        }
        return binding.root
    }

    private fun MainFragmentBinding.initVm() {
        viewModel = ViewModelProvider(this@MainFragment)[MainViewModel::class.java]
    }

    private fun MainFragmentBinding.initViews() {
        mainRv.layoutManager =
            LinearLayoutManager(this.root.context, LinearLayoutManager.VERTICAL, false)
        mainRv.adapter = adapter
    }

    private fun MainFragmentBinding.initObservers() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is AppState.Loading -> {
                    progress.isVisible = true
                }
                is AppState.Error -> {
                    progress.isVisible = false
                    showErrorLoadingDialog(requireContext())
                }
                is AppState.Success -> {
                    progress.isVisible = false
                    adapter.setData(it.data)
                }
            }
        }
    }

    private fun MainFragmentBinding.requestData() {
        viewModel.getData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        adapter.removeClickListener()
        super.onDestroy()
    }
}