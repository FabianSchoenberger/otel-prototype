plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.10"
}

group = "com.infendro"
version = "1.0.0"

repositories {
    maven {
        url = uri("https://maven.pkg.github.com/dcxp/opentelemetry-kotlin")
        credentials {
            username = project.property("GITHUB_USERNAME") as String
            password = project.property("GITHUB_PASSWORD") as String
        }
    }
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("io.opentelemetry.kotlin.api:all:1.0.570")
    implementation("io.opentelemetry.kotlin.sdk:sdk-trace:1.0.570")
    implementation("com.infendro:otlp-exporter:1.0.0")
}

kotlin {
    jvmToolchain(23)
}
