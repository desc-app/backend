package app.desc.backend.controllers

import app.desc.backend.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(private val userService: UserService) {
    private val failedLoginResponse by lazy { ResponseEntity.badRequest().body(LoginFailedResponse()) }

    @PostMapping
    fun index(@RequestBody request: LoginRequest): ResponseEntity<*> {
        val token = userService.login(request.username, request.password) ?: return failedLoginResponse
        return ResponseEntity.ok(LoginSuccessResponse(token = token))
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