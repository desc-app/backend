package app.desc.backend.services

import app.desc.backend.models.CustomUserDetails
import app.desc.backend.repositories.IUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: IUserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Couldn't find a matching user for name: $username")
        return CustomUserDetails(user)
    }

    fun loadUserById(id: Long): UserDetails {
        val user = userRepository.findById(id).orElse(null)
            ?: throw UsernameNotFoundException("Couldn't find a matching user for name: $id")
        return CustomUserDetails(user)
    }
}