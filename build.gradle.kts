plugins {
    kotlin("jvm") version "2.0.0"
    id("org.jetbrains.kotlinx.kover") version "0.9.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.insert-koin:koin-core:4.0.3")

    testImplementation(kotlin("test"))
    testImplementation("com.google.truth:truth:1.4.4")
    testImplementation("io.mockk:mockk:1.14.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

kover {
    reports {
        filters {
            includes {
                packages("ui", "logic.usecase")
            }
            excludes {
                classes("ui.MainKt")
            }
        }

        verify {
            rule {
                bound {
                    minValue = 80
                }
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
