package edu.gctu.campusmesh.config

import com.google.gson.annotations.SerializedName

data class SchoolConfig(
    @SerializedName("school_name") val schoolName: String = "Ghana Communication Technology University",
    @SerializedName("school_short_name") val schoolShortName: String = "GCTU",
    @SerializedName("github_repo_owner") val githubRepoOwner: String = "your-username",
    @SerializedName("github_repo_name") val githubRepoName: String = "campus-mesh",
    @SerializedName("update_channel") val updateChannel: String = "stable",
    @SerializedName("wifi_hotspot_ssid") val wifiHotspotSsid: String = "Campus-Mesh-GCTU",
    @SerializedName("wifi_hotspot_pass") val wifiHotspotPass: String = "GCTUSecure2026",
    @SerializedName("web_server_port") val webServerPort: Int = 8080,
    @SerializedName("mdns_hostname") val mdnsHostname: String = "campusmesh.local",
    @SerializedName("campuses") val campuses: List<CampusPreset> = emptyList()
)

data class CampusPreset(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("geohash") val geohash: String,
    @SerializedName("radius_meters") val radiusMeters: Double = 800.0,
    @SerializedName("channels") val channels: List<String> = emptyList()
)
