package edu.gctu.campusmesh.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EmergencyBanner(activeAlertMessage: String?) {
    if (activeAlertMessage != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFDC2626))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("🚨 [CRITICAL CAMPUS ALERT • Verified Ed25519]", color = Color.White, style = MaterialTheme.typography.labelSmall)
            Text(activeAlertMessage, color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
