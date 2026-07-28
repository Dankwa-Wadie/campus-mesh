package edu.gctu.campusmesh.mesh

import android.content.Context
import android.util.Log

class BluetoothMeshManager(private val context: Context) {
    private var isScanning = false

    fun startAdaptiveMeshScan(isStationary: Boolean = false) {
        val intervalMs = if (isStationary) 30000L else 5000L
        Log.d("BluetoothMeshManager", "Starting adaptive BLE scan with interval ${intervalMs}ms")
        isScanning = true
    }

    fun stopMeshScan() {
        Log.d("BluetoothMeshManager", "Stopping BLE scan")
        isScanning = false
    }
}
