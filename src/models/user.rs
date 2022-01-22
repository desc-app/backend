#[derive(Debug)]
pub struct User {
    pub id: i32,
    pub display_name: String,
    pub username: String,
    pub password_hash: String,
}