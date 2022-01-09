package com.example.moviedbapp.model.entities

import com.google.android.gms.maps.model.LatLng

data class GeofenceReminder(
    val id: String,
    val latLng: LatLng?,
    val radius: Double?,
    val message: String
)
