package edu.gctu.campusmesh.data

data class AppUpdateNotice(
    val versionCode: Int,
    val versionName: String,
    val apkSize: Long,
    val sha256Hash: String,
    val developerSignature: String,
    val downloadUrl: String? = null
)
