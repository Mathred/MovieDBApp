package com.example.moviedbapp.services

import android.content.Context
import android.content.Intent
import android.location.Location
import android.util.Log
import androidx.core.app.JobIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceTransitionsJobIntentService: JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceErrorMessages.getErrorMessage(this, geofencingEvent.errorCode)
            Log.e(TAG, errorMessage)
        } else {
            handleEvent(geofencingEvent)
        }
    }

    private fun handleEvent(geofencingEvent: GeofencingEvent) {
        if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            val firstGeofence = geofencingEvent.triggeringGeofences[0]
            val eventId = firstGeofence.requestId
            val eventLocation = geofencingEvent.triggeringLocation
            showNotification(eventId, eventLocation)
        }
    }

    private fun showNotification(eventId: String, eventLocation: Location) {
        TODO("Not yet implemented")
    }

    companion object {

        private const val TAG = "GeofenceTransitionsJobIntentService"
        private const val JOB_ID = 303

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(
                context,
                GeofenceTransitionsJobIntentService::class.java,
                JOB_ID,
                intent
            )
        }
    }
}