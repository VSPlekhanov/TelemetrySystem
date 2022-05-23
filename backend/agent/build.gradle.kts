plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("io.freefair.lombok") version "6.4.2"
}

group = "org.azul.telemetry"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(":data"))
    implementation("me.paulschwarz:spring-dotenv:2.5.3")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.7")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("io.rest-assured:json-schema-validator:5.0.1")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.6.7")
    implementation("org.springframework.boot:spring-boot-starter-security:2.6.7")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.7")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.getByName<JavaExec>("bootRun") {
    classpath("${project.rootDir}") // To fetch .env file
}