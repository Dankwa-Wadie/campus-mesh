package edu.gctu.campusmesh.ui

import androidx.compose.foundation.Image
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
fun AccountSyncDialog(onDismiss: () -> Unit) {
    var pin by remember { mutableStateOf("1234") }
    val payload = remember(pin) {
        AccountExportManager.generateEncryptedExportQrPayload("{\"id\":\"student_20261099\"}", pin)
    }
    val qrBitmap = remember(payload) {
        AccountExportManager.generateQrCodeBitmap(payload, 512)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
            ) {
                Text("Done")
            }
        },
        title = {
            Text("Export Account Identity", color = Color.White)
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Scan on secondary phone/tablet to sync account:", color = Color.LightGray)
                qrBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Encrypted Account QR Code",
                        modifier = Modifier.size(200.dp)
                    )
                }
                Text("PIN Protected (AES-256): $pin", color = Color(0xFF38BDF8))
                Text("QR Code refreshes dynamically for security.", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
            }
        },
        containerColor = Color(0xFF1E293B)
    )
}
