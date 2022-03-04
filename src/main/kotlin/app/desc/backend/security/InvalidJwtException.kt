package app.desc.backend.security

import org.springframework.security.core.AuthenticationException

class InvalidJwtException(msg: String) : AuthenticationException(msg)