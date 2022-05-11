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
    implementation("org.postgresql:postgresql:42.3.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.6")
    implementation("org.projectlombok:lombok:1.18.18")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.6")
    implementation("org.flywaydb:flyway-gradle-plugin:3.0")
    implementation("com.vladmihalcea:hibernate-types-52:2.16.1")
    implementation("javax.validation:validation-api:2.0.1.Final")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
