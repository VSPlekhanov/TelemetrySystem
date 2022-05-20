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
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation(project(":data"))
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("org.springframework.boot:spring-boot-starter-security:2.6.7")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.7")
    implementation("me.paulschwarz:spring-dotenv:2.5.3")
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.7")
    testRuntimeOnly("com.h2database:h2:2.1.212")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.getByName<JavaExec>("bootRun") {
    classpath("${project.rootDir}") // To fetch .env file
}

tasks.getByName<JavaCompile>("compileJava") {
    inputs.files(tasks.named("processResources"))
}
