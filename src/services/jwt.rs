use anyhow::Error;
use jwt_simple::prelude::*;

use crate::models::user::User;

pub struct JwtService {
    key: HS256Key,
}

impl JwtService {
    pub fn new() -> Self {
        // TODO: Save key to disk or something, maybe load from env?
        JwtService {
            key: HS256Key::generate(),
        }
    }

    pub fn create_token(self, user: User) -> Result<String, Error> {
        // TODO: Use user data or some shit to add some fields and stuff
        let claims = Claims::create(Duration::from_hours(2));
        self.key.authenticate(claims)
    }

    pub fn verify(self, user: User, token: String) -> bool {
        self.key
            .verify_token::<NoCustomClaims>(&token, None)
            // TODO: Verify claims with user data
            .map_or(false, |_| true)
    }
}
