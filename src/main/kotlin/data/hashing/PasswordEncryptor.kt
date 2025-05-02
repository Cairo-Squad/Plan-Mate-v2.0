package data.hashing

interface PasswordEncryptor {
    fun hashPassword(password: String): String
}
