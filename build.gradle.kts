plugins {
    id("java")
    id("io.qameta.allure") version "2.11.2"
    id("org.gradle.test-retry") version "1.5.9"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.19.1")
    testImplementation("org.aspectj:aspectjweaver:1.9.22")
    testImplementation("org.slf4j:slf4j-simple:2.0.13")
    testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.17.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}


tasks.test {
    useJUnitPlatform()
    retry {
        maxRetries = 2
        maxFailures = 10
        failOnPassedAfterRetry = true
    }
}