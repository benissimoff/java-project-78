plugins {
    java
    application
    checkstyle
    id("org.sonarqube") version "6.1.0.5360"
    jacoco
}

sonar {
    properties {
        property("sonar.projectKey", "benissimoff_java-project-71")
        property("sonar.organization", "benissimoff")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)

}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
    }
}
