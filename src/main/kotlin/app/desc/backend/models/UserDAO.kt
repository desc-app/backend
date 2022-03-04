package app.desc.backend.models

import javax.persistence.*

@Entity
@Table(name = "users")
class UserDAO(
    @Column(name = "username") var username: String,
    @Column(name = "email") var email: String,
    @Column(name = "password_hash") var password_hash: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}