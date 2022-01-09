package com.example.moviedbapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.moviedbapp.databinding.FragmentSettingsBinding
import com.example.moviedbapp.utils.Preferences

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false).apply {
            initViews()
        }
        return binding.root
    }

    private fun FragmentSettingsBinding.initViews() {
        val preferences = this@SettingsFragment.context?.let { Preferences(it) }
        if (preferences != null) {
            swAdultContent.isChecked = preferences.getAdultContentEnabledOption()
            swLongTitles.isChecked = preferences.getLongTitlesEnabledOption()
        }
        swAdultContent.setOnCheckedChangeListener { _, isChecked ->
            preferences?.setAdultContentEnabledOption(isChecked)
        }
        swLongTitles.setOnCheckedChangeListener { _, isChecked ->
            preferences?.setLongTitlesEnabledOption(isChecked)
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}