package edu.gctu.campusmesh.map

import android.location.Location
import edu.gctu.campusmesh.config.CampusPreset
import edu.gctu.campusmesh.data.AppMode

object SoftGeofenceManager {
    fun detectCampusMode(currentLocation: Location, presets: List<CampusPreset>): AppMode {
        for (preset in presets) {
            val campusLocation = Location("").apply {
                latitude = preset.latitude
                longitude = preset.longitude
            }
            val distance = currentLocation.distanceTo(campusLocation)
            if (distance <= preset.radiusMeters) {
                return when (preset.id) {
                    "abeka_campus" -> AppMode.ABEKA_CAMPUS
                    else -> AppMode.MAIN_CAMPUS
                }
            }
        }
        return AppMode.GENERAL_MESH
    }
}
