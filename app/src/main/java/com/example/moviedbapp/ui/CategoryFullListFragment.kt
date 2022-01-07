package com.example.moviedbapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedbapp.MovieDbApp
import com.example.moviedbapp.databinding.FragmentCategoryFullListBinding
import com.example.moviedbapp.ui.rvadapter.CategoryFullListAdapter
import com.example.moviedbapp.utils.Navigator
import com.example.moviedbapp.utils.openMovie
import com.example.moviedbapp.viewmodel.CategoryFullListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class CategoryFullListFragment : Fragment() {

    private var _binding: FragmentCategoryFullListBinding? = null
    private val binding: FragmentCategoryFullListBinding get() = _binding!!

    private lateinit var viewModel: CategoryFullListViewModel

    private var categoryType: Int? = null

    private var onMovieClick: (Int) -> Unit = {
        openMovie(it)
    }

    private val adapter by lazy {
        CategoryFullListAdapter(onMovieClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        categoryType = arguments?.getInt(CATEGORY_TYPE) ?: -1
        _binding = FragmentCategoryFullListBinding.inflate(inflater, container, false).apply {
            initVm()
            initViews()
            initFlow()
        }
        return binding.root
    }

    private fun initVm() {
        viewModel = ViewModelProvider(this)[CategoryFullListViewModel::class.java]
    }

    private fun FragmentCategoryFullListBinding.initViews() {
        categoryType?.let {
            tvCategory.text = Navigator.Companion.CategoryType.values()[it].displayedName
        }
        rvMovies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvMovies.adapter = adapter
    }

    private fun initFlow() {
        lifecycleScope.launch(Dispatchers.IO) {
            categoryType?.let { categoryType ->
                viewModel.fetchMovies((activity?.application as MovieDbApp).movieDbApi, categoryType).distinctUntilChanged().collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    companion object {

        const val CATEGORY_TYPE = "CATEGORY_TYPE"

        fun newInstance(categoryTypeInt: Int) = CategoryFullListFragment().apply {
            arguments = bundleOf(CATEGORY_TYPE to categoryTypeInt)
        }

    }
}