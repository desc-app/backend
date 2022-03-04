package app.desc.backend.services

import app.desc.backend.models.UserDAO
import app.desc.backend.repositories.IUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(private val userRepository: IUserRepository) {
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

    enum class CreateResponse {
        SUCCESS, EMAIL_TAKEN, USERNAME_TAKEN, INVALID_PASSWORD
    }
}