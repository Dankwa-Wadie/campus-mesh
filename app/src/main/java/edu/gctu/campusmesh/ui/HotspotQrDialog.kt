package edu.gctu.campusmesh.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import edu.gctu.campusmesh.sync.AccountExportManager

@Composable
fun HotspotQrDialog(ssid: String, pass: String, webUrl: String, onDismiss: () -> Unit) {
    val qrPayload = "WIFI:S:$ssid;T:WPA;P:$pass;;"
    val qrBitmap = remember { AccountExportManager.generateQrCodeBitmap(qrPayload, 512) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
            ) {
                Text("Close")
            }
        },
        title = {
            Text("Invite Nearby Peers / Safari Web Gateway", color = Color.White, style = MaterialTheme.typography.titleMedium)
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Scan with iPhone or Android camera to join campus mesh network:", color = Color.LightGray)
                qrBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Wi-Fi Join QR Code",
                        modifier = Modifier.size(200.dp)
                    )
                }
                Text("Wi-Fi SSID: $ssid", color = Color(0xFF38BDF8))
                Text("Safari PWA Link: $webUrl", color = Color(0xFFF59E0B))
            }
        },
        containerColor = Color(0xFF1E293B)
    )
}
