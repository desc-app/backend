package app.desc.backend.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authed")
class AuthenticatedTestController {
    @GetMapping
    fun index(): ResponseEntity<String> {
        return ResponseEntity.ok("Test")
    }
}