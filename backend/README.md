# Azul telemetry system backend

**IMPORTANT**: For now tests in projects are disabled because of problems
with Spring + Flyway context setup in test environment. It will be fixed soon

## Applications
- data -- Common JPA classes
- web -- REST API for frontend
- agent -- backend side for java agents

## Startup
### Common
- Configure local environment (first time only)
    ```sh
    $ cp .env.example .env
    $ vim .env
    ```
- Start DB and pgAdmin
    ```sh
    $ docker-compose up -d
    ```
- Move rights to data directory to pgAdmin (first time only)
    ```sh
    $ chown -R 5050:5050 pgadmin-data
    ```

### `web` project

- Create signature for JWT signing (first time only). See details below
    ```sh
    $ openssl rand -out <file from .env> -base64 256
    ```
- Start application
    ```sh
    $ ./gradlew :web:bootRun
    ```
- Create admin user (first time only). If you run application in `DEVELOPMENT` mode you can do this via HTTP request
    ```sh
     $ curl \
         -d '{"username":"admin", "password":"admin"}' \
         -H "Content-Type: application/json" \
         -X POST http://localhost:8090/api/admin/auth/register
    ```

## Configuration
Application is configured via environment variables from `.env` file,
which are injected into `appication.properties` file.
You can set these environment variables any other way if you prefix them with `env.`
Configuration example can be found in `.env.example`

- `AZUL_TELEMETRY_DB_HOST` -- address of database host (without port or protocol)
- `AZUL_TELEMETRY_DB_PORT` -- database port
- `AZUL_TELEMETRY_DB_DBNAME` -- database name
- `AZUL_TELEMETRY_DB_SCHEMA` -- database schema
- `AZUL_TELEMETRY_DB_USERNAME` -- username for database
- `AZUL_TELEMETRY_DB_PASSWORD` -- database password
- `AZUL_TELEMETRY_WEB_JWT_SECRET` -- File with digital signature for JWT signing.
   Generation process described above.
   If you want it just for development purposes you can replace signature
   with any base64 encoded string with length of original string at least 64 bytes.
   For example in test resources it is encoded "Lorem ipsum..."
- `AZUL_TELEMETRY_ENVIRONMENT` -- string representing environment.
    - `DEVELOPMENT` -- Auth checks are disabled.
