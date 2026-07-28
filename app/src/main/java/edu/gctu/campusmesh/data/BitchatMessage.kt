package edu.gctu.campusmesh.data

data class BitchatMessage(
    val id: String,
    val senderId: String,
    val senderName: String,
    val senderRoleBadge: String,
    val content: String,
    val channelId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isEmergencyAlert: Boolean = false,
    val isOfficialAnnouncement: Boolean = false,
    val digitalSignature: String? = null,
    val ttlHops: Int = 7
)
