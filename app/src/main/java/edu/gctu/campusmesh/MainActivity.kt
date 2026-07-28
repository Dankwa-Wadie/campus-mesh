package edu.gctu.campusmesh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.gctu.campusmesh.config.ConfigLoader
import edu.gctu.campusmesh.data.AppMode
import edu.gctu.campusmesh.data.BitchatMessage
import edu.gctu.campusmesh.ui.ChatScreen
import edu.gctu.campusmesh.ui.CampusMapScreen
import edu.gctu.campusmesh.ui.SettingsScreen
import edu.gctu.campusmesh.ui.HotspotQrDialog
import edu.gctu.campusmesh.ui.EmergencyBanner

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = ConfigLoader.loadConfig(this)

        setContent {
            var activeTab by remember { mutableStateOf("chat") }
            var currentAppMode by remember { mutableStateOf(AppMode.MAIN_CAMPUS) }
            var showQrModal by remember { mutableStateOf(false) }

            MaterialTheme {
                Scaffold(
                    topBar = {
                        Column {
                            TopAppBar(
                                title = {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text("Campus Mesh", color = Color.White)
                                        Text(
                                            text = when(currentAppMode) {
                                                AppMode.MAIN_CAMPUS -> "[Main Campus]"
                                                AppMode.ABEKA_CAMPUS -> "[Abeka Campus]"
                                                AppMode.GENERAL_MESH -> "[General Mesh]"
                                            },
                                            color = Color(0xFF38BDF8),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                },
                                actions = {
                                    Button(
                                        onClick = { showQrModal = true },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                                    ) {
                                        Text("Invite Nearby", color = Color.White)
                                    }
                                },
                                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1E293B))
                            )
                            EmergencyBanner(
                                activeAlertMessage = null
                            )
                        }
                    },
                    bottomBar = {
                        NavigationBar(containerColor = Color(0xFF1E293B)) {
                            NavigationBarItem(
                                selected = activeTab == "chat",
                                onClick = { activeTab = "chat" },
                                label = { Text("Chat", color = Color.White) },
                                icon = {}
                            )
                            NavigationBarItem(
                                selected = activeTab == "map",
                                onClick = { activeTab = "map" },
                                label = { Text("Campus Map", color = Color.White) },
                                icon = {}
                            )
                            NavigationBarItem(
                                selected = activeTab == "settings",
                                onClick = { activeTab = "settings" },
                                label = { Text("Settings", color = Color.White) },
                                icon = {}
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(Color(0xFF0F172A))
                    ) {
                        when (activeTab) {
                            "chat" -> ChatScreen(config = config, currentAppMode = currentAppMode)
                            "map" -> CampusMapScreen(currentAppMode = currentAppMode)
                            "settings" -> SettingsScreen(
                                currentAppMode = currentAppMode,
                                onAppModeChanged = { currentAppMode = it }
                            )
                        }

                        if (showQrModal) {
                            HotspotQrDialog(
                                ssid = config.wifiHotspotSsid,
                                pass = config.wifiHotspotPass,
                                webUrl = "http://${config.mdnsHostname}:${config.webServerPort}",
                                onDismiss = { showQrModal = false }
                            )
                        }
                    }
                }
            }
        }
    }
}
