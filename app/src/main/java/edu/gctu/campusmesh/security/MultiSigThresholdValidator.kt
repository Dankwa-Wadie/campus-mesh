package edu.gctu.campusmesh.security

import java.security.PublicKey

data class SignedPayload(
    val data: String,
    val signatures: Map<String, String> // AdminPubKeyHex -> SignatureBase64
)

object MultiSigThresholdValidator {
    fun verifyMultiSig(
        payload: SignedPayload,
        requiredThresholdM: Int = 2,
        authorizedAdminKeys: Map<String, PublicKey>
    ): Boolean {
        var validSignaturesCount = 0

        for ((adminPubHex, signature) in payload.signatures) {
            val pubKey = authorizedAdminKeys[adminPubHex] ?: continue
            if (RevocationLedgerStore.isRevoked(adminPubHex)) continue

            if (Ed25519Signer.verifySignature(payload.data, signature, pubKey)) {
                validSignaturesCount++
            }
        }

        return validSignaturesCount >= requiredThresholdM
    }
}
