package app.desc.backend.services

import app.desc.backend.models.UserDAO
import app.desc.backend.repositories.IUserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: IUserRepository, private val passwordEncoder: PasswordEncoder) {
    fun create(username: String, email: String, password: String): CreateResponse {
        if (userRepository.findByUsername(username) != null) {
            return CreateResponse.USERNAME_TAKEN
        }

        if (userRepository.findByEmail(email) != null) {
            return CreateResponse.EMAIL_TAKEN
        }

        if (password.isBlank() || password.length < 8) {
            return CreateResponse.INVALID_PASSWORD
        }

        val user = UserDAO(username, email, passwordEncoder.encode(password))
        userRepository.save(user)
        return CreateResponse.SUCCESS
    }

    enum class CreateResponse {
        SUCCESS, EMAIL_TAKEN, USERNAME_TAKEN, INVALID_PASSWORD
    }
}