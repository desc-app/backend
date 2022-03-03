use argon2::{
    password_hash::{rand_core::OsRng, PasswordHash, PasswordHasher, PasswordVerifier, SaltString},
    Argon2,
};

pub struct HashService {
    salt: SaltString,
}

impl HashService {
    pub fn new() -> Self {
        // TODO: Generate salt once and store it or pull from env
        HashService {
            salt: SaltString::generate(&mut OsRng),
        }
    }

    pub fn hash(&self, input: String) -> Option<String> {
        match Argon2::default().hash_password(input.as_bytes(), &self.salt) {
            Ok(it) => Some(it.to_string()),
            Err(_) => None,
        }
    }

    pub fn verify(&self, hash: String, input: String) -> bool {
        let hash_result = PasswordHash::new(&hash);
        if hash_result.is_err() {
            return false;
        }

        let hash = hash_result.unwrap();
        return Argon2::default()
            .verify_password(input.as_bytes(), &hash)
            .is_ok();
    }
}
