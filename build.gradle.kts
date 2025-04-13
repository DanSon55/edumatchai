plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "com.edumatch"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.4")
    implementation("io.ktor:ktor-server-netty:2.3.4")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-serialization-jackson:2.3.4")
    implementation("io.ktor:ktor-server-status-pages:2.3.4")
    implementation("io.ktor:ktor-server-static-content:2.3.4")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.ktor:ktor-server-test-host:2.3.4")
}

application {
    mainClass.set("com.edumatch.ApplicationKt")
}

tasks.test {
    useJUnitPlatform()
}