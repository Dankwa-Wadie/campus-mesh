package edu.gctu.campusmesh.config

import android.content.Context
import com.google.gson.Gson
import java.io.InputStreamReader

object ConfigLoader {
    private var cachedConfig: SchoolConfig? = null

    fun loadConfig(context: Context): SchoolConfig {
        cachedConfig?.let { return it }

        return try {
            val inputStream = context.assets.open("school_config.json")
            val reader = InputStreamReader(inputStream)
            val config = Gson().fromJson(reader, SchoolConfig::class.java)
            cachedConfig = config
            config
        } catch (e: Exception) {
            e.printStackTrace()
            val defaultConfig = SchoolConfig(
                campuses = listOf(
                    CampusPreset(
                        id = "main_campus",
                        name = "Main Campus (Tesano)",
                        latitude = 5.5961352,
                        longitude = -0.2234766,
                        geohash = "w4zb8",
                        channels = listOf("#gctu-announcements", "#computing-cis", "#engineering", "#business-school", "#src-general")
                    ),
                    CampusPreset(
                        id = "abeka_campus",
                        name = "Abeka Campus (School of IT Business)",
                        latitude = 5.5995349,
                        longitude = -0.2388291,
                        geohash = "w4zb9",
                        channels = listOf("#abeka-announcements", "#abeka-it-business", "#abeka-graduate-studies", "#abeka-library")
                    )
                )
            )
            cachedConfig = defaultConfig
            defaultConfig
        }
    }
}
