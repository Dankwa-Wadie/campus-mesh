package edu.gctu.campusmesh.security

import edu.gctu.campusmesh.data.SchoolRole
import java.security.KeyPair
import java.util.Base64

data class RoleAttestationCertificate(
    val devicePublicKeyHex: String,
    val staffOrIndexId: String,
    val role: SchoolRole,
    val issuedTimestamp: Long,
    val expiryTimestamp: Long,
    val adminSignature: String
)

object RoleAttestationManager {
    fun createAttestation(
        devicePublicKeyHex: String,
        staffOrIndexId: String,
        role: SchoolRole,
        durationDays: Long = 365,
        adminKeyPair: KeyPair
    ): RoleAttestationCertificate {
        val now = System.currentTimeMillis()
        val expiry = now + (durationDays * 24 * 60 * 60 * 1000)
        val payload = "$devicePublicKeyHex:$staffOrIndexId:${role.name}:$now:$expiry"
        val signature = Ed25519Signer.signData(payload, adminKeyPair)

        return RoleAttestationCertificate(
            devicePublicKeyHex = devicePublicKeyHex,
            staffOrIndexId = staffOrIndexId,
            role = role,
            issuedTimestamp = now,
            expiryTimestamp = expiry,
            adminSignature = signature
        )
    }

    fun isValidAttestation(cert: RoleAttestationCertificate, adminPublicKey: java.security.PublicKey): Boolean {
        val now = System.currentTimeMillis()
        if (now > cert.expiryTimestamp) return false
        if (RevocationLedgerStore.isRevoked(cert.devicePublicKeyHex) || RevocationLedgerStore.isRevoked(cert.staffOrIndexId)) {
            return false
        }
        val payload = "${cert.devicePublicKeyHex}:${cert.staffOrIndexId}:${cert.role.name}:${cert.issuedTimestamp}:${cert.expiryTimestamp}"
        return Ed25519Signer.verifySignature(payload, cert.adminSignature, adminPublicKey)
    }
}
