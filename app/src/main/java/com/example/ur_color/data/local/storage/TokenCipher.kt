package com.example.ur_color.data.local.storage

import android.util.Base64
import com.example.ur_color.utils.logError
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object TokenCipher {
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val IV_SIZE = 12

    private var secretKey: SecretKey? = null

    fun init(key: SecretKey) {
        secretKey = key
    }

    fun encrypt(plaintext: String): String {
        val key = secretKey ?: throw IllegalStateException("TokenCipher not initialized")
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val iv = cipher.iv
        val ciphertext = cipher.doFinal(plaintext.toByteArray())

        return Base64.encodeToString(iv + ciphertext, Base64.DEFAULT)
    }

    fun decrypt(encrypted: String): String? {
        val key = secretKey ?: throw IllegalStateException("TokenCipher not initialized")
        return try {
            val combined = Base64.decode(encrypted, Base64.DEFAULT)
            val iv = combined.copyOfRange(0, IV_SIZE)
            val ciphertext = combined.copyOfRange(IV_SIZE, combined.size)

            val cipher = Cipher.getInstance(TRANSFORMATION)
            val spec = GCMParameterSpec(128, iv)
            cipher.init(Cipher.DECRYPT_MODE, key, spec)

            String(cipher.doFinal(ciphertext))
        } catch (e: Exception) {
            logError(e, "Decryption failed")
            null
        }
    }
}