plugins {
    java
}

subprojects {
    apply(plugin="java")

    repositories {
        mavenCentral()
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

    commandLine = listOf("java", "-javaagent:$agentJar=telemetry.agent.id=1&telemetry.agent.authtoken=auth", "-jar", appJar)
}
