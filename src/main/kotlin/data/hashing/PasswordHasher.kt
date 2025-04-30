package data.hashing

interface PasswordHasher {
    fun hashPassword(input:String):String
}
