package data.hashing

import java.security.MessageDigest

class MD5PasswordEncryptor : PasswordEncryptor {
    override fun hashPassword(password: String): String {
        if (password.isBlank()) throw IllegalStateException("Can't accept empty string")
        val md = MessageDigest.getInstance("MD5")
        val byte = md.digest(password.encodeToByteArray())
        return byte.joinToString(separator = "") { "%02x".format(it) }
    }
}