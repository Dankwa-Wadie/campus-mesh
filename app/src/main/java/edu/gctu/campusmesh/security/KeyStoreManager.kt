package edu.gctu.campusmesh.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.util.Base64

object KeyStoreManager {
    private const val KEY_ALIAS = "CampusMeshIdentityKeyPair"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"

    fun getOrCreateHardwareKeyPair(): KeyPair {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }

        if (keyStore.containsAlias(KEY_ALIAS)) {
            val privateKey = keyStore.getKey(KEY_ALIAS, null) as PrivateKey
            val cert = keyStore.getCertificate(KEY_ALIAS)
            return KeyPair(cert.publicKey, privateKey)
        }

        val kpg = KeyPairGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_EC,
            ANDROID_KEYSTORE
        )
        val parameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
        ).run {
            setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
            build()
        }

        kpg.initialize(parameterSpec)
        return kpg.generateKeyPair()
    }

    fun getPublicKeyHex(keyPair: KeyPair): String {
        return Base64.getEncoder().encodeToString(keyPair.public.encoded)
    }
}
