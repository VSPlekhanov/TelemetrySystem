plugins {
    java
}

subprojects {
    apply(plugin="java")

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}

tasks.register<Exec>("runAppWithAgent") {
    dependsOn(":agent:jar")
    dependsOn(":app:jar")

    val appJar = files(project.childProjects["app"]?.tasks?.jar).first().canonicalPath
    val agentJar = files(project.childProjects["agent"]?.tasks?.jar).first().canonicalPath

    commandLine = listOf("java", "-javaagent:$agentJar=clientId=1&authToken=auth&enabled=false&initialDelayMs=0&telemetryIntervalMs=1000", "-jar", appJar)
}
