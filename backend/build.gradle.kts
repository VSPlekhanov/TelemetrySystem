subprojects {
    apply(plugin="java")
    apply(plugin="checkstyle")

    repositories {
        mavenCentral()
    }

    configure<CheckstyleExtension> {
        toolVersion = "10.0"
    }
    task<Wrapper>("wrapper") {
        gradleVersion = "7.2"
    }
}
