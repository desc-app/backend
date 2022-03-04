package app.desc.backend.services

import app.desc.backend.models.CustomUserDetails
import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {
    private val logger = LoggerFactory.getLogger(JwtService::class.java)

    @Value("\${app.jwt.secret}")
    var jwtSecret: String = ""

    @Value("\${app.jwt.expiry}")
    var jwtExpiry: Long = 0

    fun generateJwtToken(authentication: Authentication): String {
        val principal = authentication.principal as CustomUserDetails
        return Jwts.builder()
            .setSubject(principal.user.id.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpiry))
            .signWith(SignatureAlgorithm.HS512, jwtSecret.toByteArray())
            .compact()
    }

    fun getUserIdFromToken(token: String): Long {
        return Jwts.parser().setSigningKey(jwtSecret.toByteArray()).parseClaimsJws(token).body.subject.toLong()
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret.toByteArray()).parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }

        return false
    }
}