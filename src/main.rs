mod models;
mod services;

use actix_web::{get, web, App, HttpServer, Responder};
use models::ctx::AppContext;
use services::jwt::JwtService;

// Thought out stack
// Actix web
// PostgreSQL using SQLx
// InfluxDB for metrics and whatnot
// Redis/EQMX/RabbitMQ if we need network messaging
// Redis for caching & distributed locks

#[get("/")]
async fn index(data: web::Data<AppContext>) -> impl Responder {
    match data.jwt_service.create_token(None) {
        Ok(res) => res,
        Err(err) => err.to_string(),
    }
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        let jwt_service = JwtService::new();
        let ctx = AppContext { jwt_service };

        App::new().data(ctx).service(index)
    })
    .bind("127.0.0.1:8080")?
    .run()
    .await
}
