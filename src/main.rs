mod models;
mod services;

use actix_web::{get, web::{self, Data}, App, HttpServer, Responder};
use models::ctx::AppContext;
use services::{hash::HashService, jwt::JwtService, user::UserService};

#[get("/")]
async fn index(data: web::Data<AppContext>) -> impl Responder {
    let opt = data.user_service.create_user("lily".to_string(), "meow".to_string());
    format!("{:?}", opt.unwrap())
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        let jwt_service = JwtService::new();
        let hash_service = HashService::new();
        let user_service = UserService::new(hash_service);

        App::new()
            .app_data(Data::new(AppContext {
                jwt_service,
                user_service,
            }))
            .service(index)
    })
    .bind("127.0.0.1:8080")?
    .run()
    .await
}
