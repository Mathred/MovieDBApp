package com.example.moviedbapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedbapp.databinding.FragmentMovieHistoryBinding
import com.example.moviedbapp.ui.rvadapter.MovieHistoryAdapter
import com.example.moviedbapp.utils.showErrorLoadingDialog
import com.example.moviedbapp.viewmodel.LoadState
import com.example.moviedbapp.viewmodel.MovieHistoryViewModel

class MovieHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = MovieHistoryFragment()
    }

    private lateinit var viewModel: MovieHistoryViewModel

    private var _binding : FragmentMovieHistoryBinding? = null
    private val binding: FragmentMovieHistoryBinding get() = _binding!!

    private val adapter by lazy {
        MovieHistoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieHistoryBinding.inflate(inflater, container, false).apply {
            initVm()
            initViews()
            initObservers()
            requestData()
        }
        return binding.root
    }

    private fun initVm() {
        viewModel = ViewModelProvider(this)[MovieHistoryViewModel::class.java]
    }

    private fun FragmentMovieHistoryBinding.initViews() {
        rvMovies.layoutManager = LinearLayoutManager(this.root.context, LinearLayoutManager.VERTICAL, false )
        rvMovies.adapter = adapter
        btnClearHistory.setOnClickListener {
            viewModel.clearHistory()
            adapter.clearData()
        }
    }

    private fun initObservers() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is LoadState.Loading -> {
                }
                is LoadState.Error -> {
                    showErrorLoadingDialog(requireContext(), it.error)
                }
                is LoadState.Success -> {
                    adapter.setData(it.data)
                }
            }
        }
    }

    private fun requestData() {
        viewModel.requestData()
    }

}