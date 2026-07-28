package edu.gctu.campusmesh.mesh

import edu.gctu.campusmesh.data.BitchatMessage
import edu.gctu.campusmesh.security.RevocationLedgerStore
import java.util.concurrent.ConcurrentHashMap

object PacketRouter {
    private val seenMessageIds = ConcurrentHashMap.newKeySet<String>()

    fun processIncomingMessage(message: BitchatMessage, onValidMessageReceived: (BitchatMessage) -> Unit) {
        if (RevocationLedgerStore.isRevoked(message.senderId)) {
            // Drop blacklisted packets instantly
            return
        }

        if (seenMessageIds.contains(message.id)) {
            // Drop duplicate packets
            return
        }

        seenMessageIds.add(message.id)
        onValidMessageReceived(message)
    }
}
