package edu.gctu.campusmesh.data

data class PeerLocationPacket(
    val peerId: String,
    val displayName: String,
    val roleBadge: String,
    val campusId: String,
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Float,
    val timestamp: Long = System.currentTimeMillis()
)
