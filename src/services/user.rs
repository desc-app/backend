use crate::models::user::User;

use super::hash::HashService;

pub struct UserService {
    hash_service: HashService,
}

impl UserService {
    pub fn new(hash_service: HashService) -> Self {
        UserService { hash_service }
    }

    // sentry would be pog
    // TODO: Some error logging and just correctly use Results and Errors instead of Optional types
    // they are so confusing AAAA
    pub fn create_user(&self, username: String, password_raw: String) -> Option<User> {
        let display_name = username.clone();

        let hash_option = self.hash_service.hash(password_raw);
        let hash = hash_option.as_ref()?;

        Some(User {
            id: 0,
            display_name,
            username,
            password_hash: hash.to_owned(),
        })
    }
}
