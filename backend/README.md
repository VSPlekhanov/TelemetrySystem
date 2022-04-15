# Azul telemetry system backend

## Applications
- data -- Common JPA classes
- web -- REST API for frontend

## Startup
- Configure local environment
    ```sh
    $ cp .env.example.env
    $ vim .env
    ```
- Start DB and pgAdmin
    ```sh
    $ docker-compose up -d
    ```
- Move rights to data directory to pgAdmin
    ```sh
    $ chown -R 5050:5050 pgadmin-data
    ```
- Start application
    ```sh
    $ ./gradlew :web:bootRun
    ```

NOTE: Application does not create DB schema yet.
You should do it manually