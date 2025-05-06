package logic.exception

import java.io.IOException

class UnknownException() : Exception("Unknown exception has occurred")

class InvalidUserException : Exception("Mate is not allowed to create project")

class UserNotFoundException: Exception("User not found")
class DtoNotFoundException : Exception("Not Found DTO")
class EntityNotFoundException : Exception("Entity not found.")
class ProjectNotFoundException : Exception("Project not found")

class ReadException : IOException("Failed to read from this CSV file")
class WriteException : Exception("Failed to write to CSV file")

class EmptyNameException : Exception("Name cannot be empty")
class EmptyTitleException : Exception("Title cannot be empty")
class EmptyPasswordException : Exception("Password cannot be empty")

class EntityNotChangedException : Exception("Entity not changed")
