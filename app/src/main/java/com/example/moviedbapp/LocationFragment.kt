package com.example.moviedbapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.example.moviedbapp.databinding.FragmentLocationBinding
import com.example.moviedbapp.utils.showAlertDialog

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    private val binding: FragmentLocationBinding get() = _binding!!

    private lateinit var location: String

    private var isPermissionGranted = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        location = arguments?.getString(LOCATION) ?: "St. Petersburg"
        _binding = FragmentLocationBinding.inflate(inflater, container, false).apply {
            initViews()
            checkPermission()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()

    }

    private fun FragmentLocationBinding.initViews() {
        tvLocation.text = location
    }

    private fun checkPermission() {
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                isPermissionGranted = true
            } else {
                context?.let {
                    showAlertDialog(
                        it,
                        title = getString(R.string.dialog_location_access_denied_title),
                        message = getString(R.string.dialog_location_access_denied_message)
                    )
                }
            }
        }
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                    isPermissionGranted = true
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showAlertDialog(
                        context = it,
                        title = getString(R.string.dialog_location_access_title),
                        message = getString(R.string.dialog_location_access_message),
                        negativeButtonText = getString(R.string.dialog_location_access_negative_button),
                        positiveButtonText = getString(R.string.dialog_location_access_positive_button),
                        positiveButtonAction = { requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS) }
                    )
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->

            googleMap.uiSettings.isZoomControlsEnabled = true

            val locationCoords = Geocoder(requireContext()).getFromLocationName(location, 1)
            if (locationCoords.size > 0) {
                val lat = locationCoords[0].latitude
                val lon = locationCoords[0].longitude
                val position = LatLng(lat,lon)
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
                googleMap.addMarker(MarkerOptions().position(position).title(location))
            }

            googleMap.uiSettings.isMyLocationButtonEnabled = isPermissionGranted
            googleMap.isMyLocationEnabled = isPermissionGranted
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            ContactsFragment.REQUEST_CODE_CONTACTS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    isPermissionGranted = true
                } else {
                    context?.let {
                        showAlertDialog(
                            it,
                            title = getString(R.string.dialog_contact_access_denied_title),
                            message = getString(R.string.dialog_contact_access_denied_message)
                        )
                    }
                }
            }
        }
        return
    }

    companion object {
        const val LOCATION = "LOCATION"
        const val REQUEST_CODE_ACCESS_FINE_LOCATION = "REQUEST_CODE_ACCESS_FINE_LOCATION"
        fun newInstance(location: String) = LocationFragment().apply {
            arguments = bundleOf(LOCATION to location)
        }
    }
}