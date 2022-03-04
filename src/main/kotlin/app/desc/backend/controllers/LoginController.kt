package app.desc.backend.controllers

import app.desc.backend.services.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {
    private val failedLoginResponse by lazy { ResponseEntity.badRequest().body(LoginFailedResponse()) }

    @PostMapping
    fun index(@RequestBody request: LoginRequest): ResponseEntity<*> {
        try {
            // Maybe put this into UserService instead?
            val authentication: Authentication =
                authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        request.username,
                        request.password
                    )
                )
            SecurityContextHolder.getContext().authentication = authentication

            val token = jwtService.generateJwtToken(authentication)
            return ResponseEntity.ok(LoginSuccessResponse(token = token))
        } catch (e: Exception) {
            return failedLoginResponse
        }
    }

    // TODO: Support email login
    data class LoginRequest(val username: String, val password: String)

    enum class LoginResponseType {
        SUCCESS,
        ERROR
    }

    data class LoginSuccessResponse(val status: LoginResponseType = LoginResponseType.SUCCESS, val token: String)

    data class LoginFailedResponse(val status: LoginResponseType = LoginResponseType.ERROR)
}