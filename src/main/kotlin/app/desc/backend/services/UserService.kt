package app.desc.backend.services

import app.desc.backend.models.UserDAO
import app.desc.backend.repositories.IUserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: IUserRepository) {
    fun create(username: String, email: String, password: String): CreateResponse {
        if (userRepository.findByUsername(username) != null) {
            return CreateResponse.USERNAME_TAKEN
        }

        if (userRepository.findByEmail(email) != null) {
            return CreateResponse.EMAIL_TAKEN
        }

        // TODO: Test the password with a password strength tester or something :shrug:
        if (password.isBlank() || password.length < 8) {
            return CreateResponse.INVALID_PASSWORD
        }

        // TODO: Actually hash password
        val user = UserDAO(username, email, password)
        userRepository.save(user)
        return CreateResponse.SUCCESS
    }

    // TODO: Support email login
    fun login(username: String, password: String): String? {
        val user = userRepository.findByUsername(username) ?: return null

        // TODO: Actually do password hashing
        return if (user.password_hash == password) {
            // TODO: JWT Tokens
            UUID.randomUUID().toString()
        } else {
            null
        }
    }

    enum class CreateResponse {
        SUCCESS, EMAIL_TAKEN, USERNAME_TAKEN, INVALID_PASSWORD
    }
}