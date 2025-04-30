package data.hashing

import java.security.MessageDigest

class MD5HashingImpl : MD5Hashing {
    override fun hash(input: String): String {
        if (input.isBlank()) throw IllegalStateException("Can't accept empty string")
        val md = MessageDigest.getInstance("MD5")
        val byte = md.digest(input.encodeToByteArray())
        return byte.joinToString(separator = "") { "%02x".format(it) }
    }

}