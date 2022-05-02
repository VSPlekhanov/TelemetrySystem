plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.flywaydb.flyway") version "8.5.8"
    id("io.freefair.lombok") version "6.4.2"
}

group = "org.azul.telemetry"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(project(":data"))
    implementation("me.paulschwarz:spring-dotenv:2.5.2")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.6")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.6")
    implementation("org.flywaydb:flyway-gradle-plugin:3.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
tasks.getByName<JavaExec>("bootRun") {
    classpath("${project.rootDir}") // To fetch .env file
}
