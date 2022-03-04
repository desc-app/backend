package app.desc.backend.controllers

import app.desc.backend.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/register")
class RegisterController(private val userService: UserService) {
    @PostMapping
    fun index(@RequestBody request: RegisterRequest): ResponseEntity<RegisterResponse> {
        return userService.create(request.username, request.email, request.password).let {
            ResponseEntity.ok(RegisterResponse(it))
        }
    }

    data class RegisterRequest(val username: String, val email: String, val password: String)

    data class RegisterResponse(val status: UserService.CreateResponse)
}