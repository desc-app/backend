package app.desc.backend.repositories

import app.desc.backend.models.UserDAO
import org.springframework.data.jpa.repository.JpaRepository

interface IUserRepository : JpaRepository<UserDAO, Long> {
    fun findByUsername(username: String): UserDAO?

    fun findByEmail(email: String): UserDAO?
}