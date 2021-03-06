package com.example.moviedbapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedbapp.MovieDbApp
import com.example.moviedbapp.R
import com.example.moviedbapp.databinding.MainFragmentBinding
import com.example.moviedbapp.ui.rvadapter.MovieCategoryAdapter
import com.example.moviedbapp.utils.*
import com.example.moviedbapp.viewmodel.LoadState
import com.example.moviedbapp.viewmodel.MainViewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val onMovieClick: (Int) -> Unit = {
        openMovie(it)
    }

    private val onShowMoreClick: (Navigator.Companion.CategoryType) -> Unit = {
        openCategoryFullList(it)
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
            requestData(
                this@MainFragment.context?.let { Preferences(it) }?.getAdultContentEnabledOption() ?: false,
                this@MainFragment.context?.let { Preferences(it) }?.getLongTitlesEnabledOption() ?: true
            )
        }
        return binding.root
    }

    private fun initVm() {
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
                is LoadState.Loading -> {
                    progress.isVisible = true
                }
                is LoadState.Error -> {
                    progress.isVisible = false
                    showErrorLoadingDialog(requireContext(), it.error)
                }
                is LoadState.Success -> {
                    progress.isVisible = false
                    adapter.setData(it.data)
                }
            }
        }
    }

    private fun requestData(showAdultContent: Boolean, showLongTitles: Boolean) {
        viewModel.getData((activity?.application as? MovieDbApp)?.movieDbApi, showAdultContent, showLongTitles)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}