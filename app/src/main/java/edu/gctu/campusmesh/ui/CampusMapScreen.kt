package edu.gctu.campusmesh.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.gctu.campusmesh.data.AppMode

@Composable
fun CampusMapScreen(currentAppMode: AppMode) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Offline OpenStreetMap",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF38BDF8)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = when(currentAppMode) {
                        AppMode.MAIN_CAMPUS -> "Rendering GCTU Main Campus (Tesano • 5.5961352, -0.2234766)"
                        AppMode.ABEKA_CAMPUS -> "Rendering GCTU Abeka Campus (SITB • 5.5995349, -0.2388291)"
                        AppMode.GENERAL_MESH -> "Rendering Dynamic Off-Campus Map"
                    },
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                    modifier = Modifier.padding(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Live Peer Markers Detected Over Mesh:", color = Color(0xFF94A3B8))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("📍 [Lecturer • Verified] Dr. Mensah (Faculty of Computing)", color = Color.White)
                        Text("📍 [Student] Kwesi (Library - Room 102)", color = Color.White)
                        Text("📍 [Student • Web] Ama #a7f9 (Hostel A)", color = Color.White)
                    }
                }
            }
        }
    }
}
