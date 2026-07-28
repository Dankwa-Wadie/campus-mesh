package edu.gctu.campusmesh.security

import java.util.concurrent.ConcurrentHashMap

data class RevocationCertificate(
    val idToRevoke: String, // pubkey hex or student/staff index ID
    val reason: String,
    val timestamp: Long = System.currentTimeMillis(),
    val adminSignature: String
)

object RevocationLedgerStore {
    private val blacklistedIds = ConcurrentHashMap.newKeySet<String>()

    fun revokeId(revocationCert: RevocationCertificate, adminPublicKey: java.security.PublicKey): Boolean {
        val payload = "${revocationCert.idToRevoke}:${revocationCert.reason}:${revocationCert.timestamp}"
        val isValid = Ed25519Signer.verifySignature(payload, revocationCert.adminSignature, adminPublicKey)
        if (isValid) {
            blacklistedIds.add(revocationCert.idToRevoke)
            return true
        }
        return false
    }

    fun isRevoked(id: String): Boolean {
        return blacklistedIds.contains(id)
    }
}
