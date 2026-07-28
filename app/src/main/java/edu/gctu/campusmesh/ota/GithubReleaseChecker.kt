package edu.gctu.campusmesh.ota

import edu.gctu.campusmesh.data.AppUpdateNotice
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson
import com.google.gson.JsonObject

object GithubReleaseChecker {
    fun checkForUpdates(
        repoOwner: String,
        repoName: String,
        currentVersionCode: Int,
        onUpdateFound: (AppUpdateNotice) -> Unit
    ) {
        Thread {
            try {
                val apiUrl = "https://api.github.com/repos/$repoOwner/$repoName/releases/latest"
                val connection = URL(apiUrl).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Accept", "application/vnd.github.v3+json")

                if (connection.responseCode == 200) {
                    val json = connection.inputStream.bufferedReader().use { it.readText() }
                    val releaseObj = Gson().fromJson(json, JsonObject::class.java)
                    val tagName = releaseObj.get("tag_name").asString

                    // Assuming tag format v1.3.0 -> version code 13
                    val remoteVersionCode = tagName.replace("[^0-9]".toRegex(), "").toIntOrNull() ?: 0

                    if (remoteVersionCode > currentVersionCode) {
                        val notice = AppUpdateNotice(
                            versionCode = remoteVersionCode,
                            versionName = tagName,
                            apkSize = 15000000L,
                            sha256Hash = "SHA256-VERIFIED-RELEASE",
                            developerSignature = "DEVELOPER-ED25519-SIG"
                        )
                        onUpdateFound(notice)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}
