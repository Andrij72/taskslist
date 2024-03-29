# TasksList

#### This springboot application use technology stack: SpringBoot, Hibernate JPA, JWT, Redis, Docker

#### and his all available endpoints are accessed by Swagger in address http://localhost:8090/swagger-ui/index.html

#### Application Taskslist uses Redis for caching, a Postgresql database and MinIO for storage.

## To start:

#### To run the application in the root directory, you need to create a .env file with environments variables:

- `SERVER_PORT`- host of main application server
- `HOST_DB`- host of Postgresql database
- `POSTGRES_USERNAME`- username of Postgres database
- `POSTGRES_PASSWORD`- password of Postgres database
- `POSTGRES_DATABASE`- name database
- `POSTGRES_SCHEMA`- name scheme database
- `JWT_SECRET`- JWT tokens secret string

- `REDIS_PASSWORD` - password for Redis
- `REDIS_HOST` - Redis instance host

- `MINIO_BUCKET` - bucket name for MinIO
- `MINIO_URL` - URL for MinIO
- `MINIO_SECRET_KEY` - secret key for MinIO
- `MINIO_ACCESS_KEY` - access key for MinIO

## Sequence diagram

![Sequence diagram](docs/uml_sequence.png)

## Classes diagram

![Classes diagram](docs/diagramme_classes.png)
