#[derive(Debug)]
pub struct User<'s> {
    pub id: i32,
    pub display_name: &'s mut String,
    pub username: &'s mut String,
    pub password_hash: &'s mut String,
}