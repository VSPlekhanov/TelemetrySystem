group = "org.azul.telemetry"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Probably we should get rid of these dependencies
    // because they make agent very fat which is not good
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation ("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("org.jetbrains:annotations:23.0.0")
}

tasks.getByName<Jar>("jar") {
    manifest.from("src/main/resources/META-INF/MANIFEST.MF")

    // Create fat jar
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory ) it else zipTree(it) })
}
