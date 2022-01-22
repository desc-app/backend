use crate::services::{jwt::JwtService, user::UserService};

pub struct AppContext {
    pub jwt_service: JwtService,
    pub user_service: UserService,
}
