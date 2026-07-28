package edu.gctu.campusmesh.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.gctu.campusmesh.data.AppUpdateNotice

@Composable
fun UpdatePromptDialog(notice: AppUpdateNotice, onDownloadClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B4B)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "New Campus Mesh Update Available (${notice.versionName})",
                    color = Color(0xFF38BDF8),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Received offline from a nearby campus peer. Tap to download and install.",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(
                onClick = onDownloadClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
            ) {
                Text("Download")
            }
        }
    }
}
