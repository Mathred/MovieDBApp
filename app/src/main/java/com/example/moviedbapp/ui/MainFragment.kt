package com.example.moviedbapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moviedbapp.databinding.MainFragmentBinding
import com.example.moviedbapp.viewmodel.AppState
import com.example.moviedbapp.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        initVm()
        initObservers()
        requestData()
        return binding.root
    }

    private fun requestData() {
        viewModel.getData()
    }

    private fun initVm() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun initObservers() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is AppState.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                }
                is AppState.Error -> {
                    Toast.makeText(context, it.error.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                is AppState.Success -> {
                    Toast.makeText(context, it.data.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}