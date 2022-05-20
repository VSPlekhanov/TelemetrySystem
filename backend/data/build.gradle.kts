plugins {
    `java-library`
    id("io.freefair.lombok") version "6.4.2"
    id("org.flywaydb.flyway") version "8.5.8"
}

group = "org.azul.telemetry"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa:2.6.7")

    implementation("org.postgresql:postgresql:42.3.4")
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("org.springframework.boot:spring-boot-starter-security:2.6.7")
    implementation("org.projectlombok:lombok:1.18.24")
    implementation("com.vladmihalcea:hibernate-types-52:2.16.2")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.flywaydb:flyway-core:8.5.11")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.7")
    testRuntimeOnly("com.h2database:h2:2.1.212")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
