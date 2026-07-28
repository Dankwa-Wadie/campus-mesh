package edu.gctu.campusmesh.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.gctu.campusmesh.config.SchoolConfig
import edu.gctu.campusmesh.data.AppMode
import edu.gctu.campusmesh.data.BitchatMessage

@Composable
fun ChatScreen(config: SchoolConfig, currentAppMode: AppMode) {
    var activeChannel by remember { mutableStateOf("#gctu-announcements") }
    var messageText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<BitchatMessage>() }

    val isReadOnly = activeChannel.contains("announcements", ignoreCase = true)

    Column(modifier = Modifier.fillMaxSize()) {
        // Channel Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E293B))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Channel: $activeChannel", color = Color.White, style = MaterialTheme.typography.titleMedium)
            if (isReadOnly) {
                Text(text = "Read-Only Official", color = Color(0xFFF59E0B), style = MaterialTheme.typography.labelSmall)
            }
        }

        // Messages List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { msg ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(text = msg.senderRoleBadge, color = Color(0xFF38BDF8), style = MaterialTheme.typography.labelSmall)
                            Text(text = msg.senderName, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = msg.content, color = Color(0xFFE2E8F0), style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        // Chat Input Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E293B))
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f),
                enabled = !isReadOnly,
                placeholder = {
                    Text(
                        if (isReadOnly) "Read-Only Official Channel (Ed25519 Verified)" else "Message nearby mesh peers...",
                        color = Color.Gray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.Gray
                )
            )
            Button(
                onClick = {
                    if (messageText.isNotBlank()) {
                        messages.add(
                            BitchatMessage(
                                id = System.currentTimeMillis().toString(),
                                senderId = "self",
                                senderName = "You",
                                senderRoleBadge = "[Student]",
                                content = messageText,
                                channelId = activeChannel
                            )
                        )
                        messageText = ""
                    }
                },
                enabled = !isReadOnly && messageText.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
            ) {
                Text("Send")
            }
        }
    }
}
