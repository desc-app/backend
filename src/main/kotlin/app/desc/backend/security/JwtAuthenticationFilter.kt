package app.desc.backend.security

import app.desc.backend.services.CustomUserDetailsService
import app.desc.backend.services.JwtService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = request.getHeader("Authorization")?.substring(7)
                ?: throw InvalidJwtException("Authorization header missing!")

            val userId = jwtService.getUserIdFromToken(token)
            val user = userDetailsService.loadUserById(userId)

            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(user, null, user.authorities).apply {
                    details = WebAuthenticationDetailsSource().buildDetails(request)
                }
        } catch (_: Exception) {
        }

        filterChain.doFilter(request, response)
    }
}