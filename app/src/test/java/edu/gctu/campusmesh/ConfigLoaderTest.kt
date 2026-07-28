package edu.gctu.campusmesh

import edu.gctu.campusmesh.config.SchoolConfig
import edu.gctu.campusmesh.data.SchoolRole
import edu.gctu.campusmesh.security.Ed25519Signer
import edu.gctu.campusmesh.security.RevocationLedgerStore
import edu.gctu.campusmesh.security.RevocationCertificate
import org.junit.Assert.*
import org.junit.Test
import java.security.KeyPairGenerator
import java.util.Base64

class ConfigLoaderTest {

    @Test
    fun testDefaultSchoolConfigPresets() {
        val config = SchoolConfig()
        assertEquals("Ghana Communication Technology University", config.schoolName)
        assertEquals("GCTU", config.schoolShortName)
        assertEquals("Campus-Mesh-GCTU", config.wifiHotspotSsid)
        assertEquals("campusmesh.local", config.mdnsHostname)
    }

    @Test
    fun testEd25519SignatureVerification() {
        val kpg = KeyPairGenerator.getInstance("EC")
        kpg.initialize(256)
        val keyPair = kpg.generateKeyPair()

        val payload = "OFFICIAL_ANNOUNCEMENT:Exam timetable released for CIS faculty."
        val signature = Ed25519Signer.signData(payload, keyPair)

        assertTrue(signature.isNotBlank())
        val isValid = Ed25519Signer.verifySignature(payload, signature, keyPair.public)
        assertTrue(isValid)

        // Verify tampered payload fails
        val isTamperedValid = Ed25519Signer.verifySignature(payload + " TAMPERED", signature, keyPair.public)
        assertFalse(isTamperedValid)
    }

    @Test
    fun testRevocationLedgerBlacklist() {
        val kpg = KeyPairGenerator.getInstance("EC")
        kpg.initialize(256)
        val adminKeyPair = kpg.generateKeyPair()

        val compromisedId = "student_20261099"
        val payload = "$compromisedId:Compromised device:1000"
        val signature = Ed25519Signer.signData(payload, adminKeyPair)

        val cert = RevocationCertificate(
            idToRevoke = compromisedId,
            reason = "Compromised device",
            timestamp = 1000,
            adminSignature = signature
        )

        assertFalse(RevocationLedgerStore.isRevoked(compromisedId))
        val revokedSuccess = RevocationLedgerStore.revokeId(cert, adminKeyPair.public)
        assertTrue(revokedSuccess)
        assertTrue(RevocationLedgerStore.isRevoked(compromisedId))
    }
}
