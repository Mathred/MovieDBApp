package com.example.moviedbapp.utils

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.moviedbapp.model.entities.GeofenceReminder
import com.example.moviedbapp.services.GeofenceBroadcastReceiver
import com.example.moviedbapp.services.GeofenceErrorMessages
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class GeofenceBuilder(private val context: Context) {

    private val geofencingClient = LocationServices.getGeofencingClient(context)
    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    fun add(
        geofenceReminder: GeofenceReminder,
        onSuccess: () -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {
        val geofence = buildGeofence(geofenceReminder)
        if (geofence != null && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            geofencingClient
                .addGeofences(buildGeofencingRequest(geofence), geofencePendingIntent)
                .addOnSuccessListener {
                    onSuccess.invoke()
                }
                .addOnFailureListener {
                    onFailure.invoke(GeofenceErrorMessages.getErrorString(context, it))
                }
        }
    }

    fun remove(
        geofenceReminder: GeofenceReminder,
        onSuccess: () -> Unit,
        onFailure: (errorMessage: String) -> Unit
    ) {
        geofencingClient
            .removeGeofences(listOf(geofenceReminder.id))
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(GeofenceErrorMessages.getErrorString(context, it))
            }
    }

    private fun buildGeofence(reminder: GeofenceReminder): Geofence? {
        val latitude = reminder.latLng?.latitude
        val longitude = reminder.latLng?.longitude
        val radius = reminder.radius

        return if (latitude != null && longitude != null && radius != null) {
            Geofence.Builder()
                .setRequestId(reminder.id)
                .setCircularRegion(
                    latitude,
                    longitude,
                    radius.toFloat()
                )
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build()
        } else {
            null
        }
    }

    private fun buildGeofencingRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
            .setInitialTrigger(0)
            .addGeofence(geofence)
            .build()
    }
}