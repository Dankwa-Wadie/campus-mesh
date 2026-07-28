package edu.gctu.campusmesh.server

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import io.ktor.server.engine.embeddedServer
import io.ktor.server.cio.CIO
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.routing

class LocalWebGatewayServer {
    class ForegroundService : Service() {
        private var serverEngine: Any? = null

        override fun onCreate() {
            super.onCreate()
            createNotificationChannel()
            val notification = NotificationCompat.Builder(this, "campus_mesh_channel")
                .setContentTitle("Campus Mesh Active")
                .setContentText("Mesh router & Web Gateway running silently")
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()

            startForeground(1001, notification)

            startKtorServer()
        }

        private fun startKtorServer() {
            try {
                val server = embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
                    routing {
                        staticResources("/", "web_pwa")
                    }
                }
                server.start(wait = false)
                serverEngine = server
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "campus_mesh_channel",
                    "Campus Mesh Service",
                    NotificationManager.IMPORTANCE_LOW
                )
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
        }

        override fun onBind(intent: Intent?): IBinder? = null
    }
}
