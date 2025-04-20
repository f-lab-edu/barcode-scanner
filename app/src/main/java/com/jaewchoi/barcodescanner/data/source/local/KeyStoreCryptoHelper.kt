package com.jaewchoi.barcodescanner.data.source.local

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.ByteBuffer
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object KeyStoreCryptoHelper {
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val IV_SIZE = 12

    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
    }

    private val keyGenerator: KeyGenerator by lazy {
        KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
    }

    private val cipher: Cipher by lazy {
        Cipher.getInstance(TRANSFORMATION)
    }

    private val charset = Charsets.UTF_8

    @Synchronized
    private fun getOrCreateSecretKey(alias: String): SecretKey {
        (keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry)?.let {
            return it.secretKey
        }

        val spec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    /**
     * @param alias 키 식별자
     * @param plainText 암호화할 문자열
     * @return Base64로 인코딩된 "IV + CipherText"
     */
    fun encrypt(alias: String, plainText: String): String {
        val secretKey = getOrCreateSecretKey(alias)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val cipherText = cipher.doFinal(plainText.toByteArray(charset))
        val combined = ByteBuffer.allocate(iv.size + cipherText.size)
            .put(iv)
            .put(cipherText)
            .array()

        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    /**
     * @param alias 키 식별자
     * @param encryptedData Base64로 인코딩된 "IV + CipherText"
     * @return 복호화된 평문
     */
    fun decrypt(alias: String, encryptedData: String): String {
        val combined = Base64.decode(encryptedData, Base64.NO_WRAP)
        val byteBuffer = ByteBuffer.wrap(combined)
        val iv = ByteArray(IV_SIZE).also { byteBuffer.get(it) }
        val cipherText = ByteArray(byteBuffer.remaining()).also { byteBuffer.get(it) }

        val secretKey = getOrCreateSecretKey(alias)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        val plainBytes = cipher.doFinal(cipherText)
        return String(plainBytes, charset)
    }
}