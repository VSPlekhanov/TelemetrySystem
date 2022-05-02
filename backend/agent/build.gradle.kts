plugins {
    `java-library`
    id("org.springframework.boot") version "2.6.6"
    id("io.freefair.lombok") version "6.4.2"
}

group = "org.azul.telemetry"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(":data"))
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.6")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.6")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.6")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("io.rest-assured:json-schema-validator:3.0.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
