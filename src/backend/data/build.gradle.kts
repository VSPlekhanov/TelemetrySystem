plugins {
    `java-library`
}

group = "org.azul.telemetry"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("org.postgresql:postgresql:42.3.3")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.6")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.6")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}