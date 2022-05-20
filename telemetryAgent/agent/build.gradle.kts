group = "org.azul.telemetry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2")
}

tasks.getByName<Jar>("jar") {
    manifest.from("src/main/resources/META-INF/MANIFEST.MF")

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory ) it else zipTree(it) })
}
