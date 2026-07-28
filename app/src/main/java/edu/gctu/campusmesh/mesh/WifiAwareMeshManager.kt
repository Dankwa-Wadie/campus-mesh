package edu.gctu.campusmesh.mesh

import android.content.Context
import android.util.Log

class WifiAwareMeshManager(private val context: Context) {
    fun startWifiAwareSession() {
        Log.d("WifiAwareMeshManager", "Starting Wi-Fi Aware (NAN) silent P2P background session")
    }
}
