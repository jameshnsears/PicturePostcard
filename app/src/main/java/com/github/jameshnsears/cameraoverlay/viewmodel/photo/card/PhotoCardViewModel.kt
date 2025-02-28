package com.github.jameshnsears.cameraoverlay.viewmodel.photo.card

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class PhotoCardViewModel(private val locationManager: LocationManager) :
    ViewModel(), LocationListener {
    val defaultLocation = Location("")

    private val _locationState = MutableStateFlow(defaultLocation)
    val locationState = _locationState.asStateFlow()

    override fun onLocationChanged(location: Location) {
        Timber.d("%s", location)

        _locationState.update { myState ->
            location
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        // Handle the status change
    }

    override fun onProviderEnabled(provider: String) {
        // Handle the provider enablement
    }

    override fun onProviderDisabled(provider: String) {
        // Handle the provider disablement
    }

    fun requestLocation() {
        viewModelScope.launch {
            requestLocationUpdates()
        }
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000,
            1f,
            this
        )
    }

    suspend fun getLocation(location: Location = defaultLocation): Location {
        while (locationState.value == location) {
            Timber.d("%s", locationState.value)
            delay(1000)
        }
        return locationState.value
    }

    fun calculateDistance(locationFrom: Location, locationTo: Location): Float {
        return locationFrom.distanceTo(locationTo)
    }
}
