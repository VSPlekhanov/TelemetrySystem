plugins {
    application
}

group = "org.azul.telemetry"
version = "1.0-SNAPSHOT"

val main = "org.azul.telemetry.App"

application {
    mainClass.set(main)
}

tasks.getByName<Jar>("jar") {
    manifest.attributes["Main-Class"] = main
}
