use actix_web::{HttpServer, App, Responder, get};

// Thought out stack
// Actix web
// PostgreSQL using SQLx
// InfluxDB for metrics and whatnot
// Redis/EQMX/RabbitMQ if we need network messaging
// Redis for caching & distributed locks

#[get("/")]
async fn index() -> impl Responder {
    "Hello World!"
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        App::new()
            .service(index)
    })
    .bind("127.0.0.1:8080")?
    .run()
    .await
}
