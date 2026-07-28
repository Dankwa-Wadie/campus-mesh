package edu.gctu.campusmesh.security

import java.security.KeyPair
import java.security.Signature
import java.util.Base64

object Ed25519Signer {
    private const val SIGNATURE_ALGORITHM = "SHA256withECDSA"

    fun signData(data: String, keyPair: KeyPair): String {
        return try {
            val dsa = Signature.getInstance(SIGNATURE_ALGORITHM)
            dsa.initSign(keyPair.private)
            dsa.update(data.toByteArray(Charsets.UTF_8))
            val signature = dsa.sign()
            Base64.getEncoder().encodeToString(signature)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun verifySignature(data: String, signatureBase64: String, publicKey: java.security.PublicKey): Boolean {
        return try {
            val dsa = Signature.getInstance(SIGNATURE_ALGORITHM)
            dsa.initVerify(publicKey)
            dsa.update(data.toByteArray(Charsets.UTF_8))
            val signatureBytes = Base64.getDecoder().decode(signatureBase64)
            dsa.verify(signatureBytes)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
