import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("io.qameta.allure") version "2.11.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Kotest
    testImplementation("io.kotest:kotest-assertions-core:5.5.4")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.5.4")
    testImplementation("io.kotest:kotest-property:5.5.4")

    // RestAssured
    testImplementation("io.rest-assured:json-schema-validator:5.3.0")
    testImplementation("io.rest-assured:kotlin-extensions:5.3.0")

    // Allure для Kotest
    testImplementation("io.kotest:kotest-extensions-allure:4.4.3")
}

tasks.test {
    useJUnitPlatform()
}

allure {
    version.set("2.20.0")

    adapter {
        autoconfigure.set(true)
        aspectjWeaver.set(true)


        frameworks {
            junit5 {
                adapterVersion.set("2.20.0")
                enabled.set(true)
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"  // или "1.8", "17" и т. д.
    }
}