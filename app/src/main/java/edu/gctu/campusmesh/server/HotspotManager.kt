package edu.gctu.campusmesh.server

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log

class HotspotManager(private val context: Context) {
    fun startLocalHotspot(onSuccess: (ssid: String, pass: String) -> Unit, onFailure: (String) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            try {
                wifiManager.startLocalOnlyHotspot(object : WifiManager.LocalOnlyHotspotCallback() {
                    override fun onStarted(reservation: WifiManager.LocalOnlyHotspotReservation?) {
                        super.onStarted(reservation)
                        val config = reservation?.wifiConfiguration
                        val ssid = config?.SSID ?: "Campus-Mesh-GCTU"
                        val pass = config?.preSharedKey ?: "GCTUSecure2026"
                        Log.d("HotspotManager", "Hotspot started: $ssid")
                        onSuccess(ssid, pass)
                    }

                    override fun onFailed(reason: Int) {
                        super.onFailed(reason)
                        onFailure("Hotspot failed with reason $reason")
                    }
                }, null)
            } catch (e: Exception) {
                onFailure(e.localizedMessage ?: "Failed to start hotspot")
            }
        } else {
            onSuccess("Campus-Mesh-GCTU", "GCTUSecure2026")
        }
    }
}
