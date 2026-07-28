package edu.gctu.campusmesh.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.gctu.campusmesh.data.AppMode
import edu.gctu.campusmesh.sync.AccountExportManager

@Composable
fun SettingsScreen(currentAppMode: AppMode, onAppModeChanged: (AppMode) -> Unit) {
    var showAccountExportDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Campus Mesh Settings", style = MaterialTheme.typography.headlineSmall, color = Color.White)

        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Campus & Mesh Mode (Soft Geofence Override)", color = Color(0xFF38BDF8))
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { onAppModeChanged(AppMode.MAIN_CAMPUS) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentAppMode == AppMode.MAIN_CAMPUS) Color(0xFF0284C7) else Color(0xFF334155)
                        )
                    ) {
                        Text("Main Campus")
                    }
                    Button(
                        onClick = { onAppModeChanged(AppMode.ABEKA_CAMPUS) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentAppMode == AppMode.ABEKA_CAMPUS) Color(0xFF0284C7) else Color(0xFF334155)
                        )
                    ) {
                        Text("Abeka Campus")
                    }
                    Button(
                        onClick = { onAppModeChanged(AppMode.GENERAL_MESH) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentAppMode == AppMode.GENERAL_MESH) Color(0xFF0284C7) else Color(0xFF334155)
                        )
                    ) {
                        Text("Off-Campus")
                    }
                }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Account Sync & Identity Export", color = Color(0xFF38BDF8))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Export your keypair and verified role certificate to a secondary device or new phone offline.", color = Color.LightGray)
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { showAccountExportDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                ) {
                    Text("Export Identity QR Code")
                }
            }
        }

        if (showAccountExportDialog) {
            AccountSyncDialog(onDismiss = { showAccountExportDialog = false })
        }
    }
}
