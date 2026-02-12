plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
    application
}

application {
    mainClass.set("com.openschool.backend.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    // Shared module for DTOs
    implementation(project(":shared"))
    
    // Ktor Server
    implementation(libs.bundles.ktor.server)
    
    // Exposed ORM
    implementation(libs.bundles.exposed)
    
    // Database
    implementation(libs.postgres.driver)
    implementation(libs.hikari.cp)
    
    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    
    // Logging
    implementation(libs.logback.classic)
    
    // Kotlin
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
}

tasks {
    create("stage") {
        dependsOn("installDist")
    }
}

kotlin {
    jvmToolchain(17)
}
