# OpenSchool - Build Configuration Guide

## Overview

This project implements a Kotlin Multiplatform (KMP) Monorepo called "OpenSchool" with Kotlin 2.0, Gradle DSL, and Version Catalog.

## Project Structure

### Modules

#### 1. :shared
**Purpose**: Shared multiplatform module for DTOs (Data Transfer Objects)

**Targets**: 
- Android (minSdk 24, compileSdk 35)
- JVM (Java 17)

**Dependencies**:
- kotlinx-serialization-json: For DTO serialization/deserialization
- kotlinx-coroutines-core: For asynchronous programming
- koin-core: For dependency injection

**Configuration**: `shared/build.gradle.kts`

#### 2. :backend  
**Purpose**: REST API server with Ktor

**Target**: JVM (Java 17)

**Technologies**:
- **Ktor 3.0.2**: Asynchronous web framework
  - Server Core & Netty
  - Content Negotiation
  - JSON Serialization
  - Call Logging
  - CORS Support
  - Status Pages
- **Exposed 1.0.0**: ORM for Kotlin
  - Core, DAO, JDBC
  - Java Time support
- **PostgreSQL 42.7.7**: Database driver
- **HikariCP 6.2.1**: Connection pooling
- **Koin**: Dependency injection for Ktor
- **Logback**: Logging

**Configuration**: `backend/build.gradle.kts`

#### 3. :androidApp
**Purpose**: Android mobile application with Jetpack Compose

**Android Configuration**:
- Application ID: com.openschool.android
- minSdk: 24
- targetSdk: 35  
- compileSdk: 35
- JVM Target: 17

**Technologies**:
- **Jetpack Compose**: Modern declarative UI
  - Compose BOM 2024.12.01
  - Material 3
  - UI Tooling
  - Activity Compose
- **Koin**: Dependency injection for Android and Compose
- **Kotlin Coroutines**: Asynchronous programming
- **Kotlin Serialization**: JSON handling

**Configuration**: `androidApp/build.gradle.kts`

## Configuration Files

### gradle/libs.versions.toml
Centralized Version Catalog that defines:
- **Versions**: All dependency versions in one place
- **Libraries**: References to libraries with group:artifact:version
- **Plugins**: Gradle plugins with IDs and versions
- **Bundles**: Groups of related dependencies (ktor-server, exposed, compose)

Benefits:
- Centralized version management
- IDE autocompletion
- Type-safe accessors
- Easy dependency updates

### build.gradle.kts (Root)
Root project configuration:
- Declares plugins without applying them (`apply false`)
- Configures `allprojects` with common group and version
- Defines shared `clean` task

### settings.gradle.kts
Multi-module configuration:
- Defines project name: "OpenSchool"
- Enables TYPESAFE_PROJECT_ACCESSORS
- Configures repositories in `pluginManagement`:
  - Gradle Plugin Portal
  - Google Maven
  - Maven Central
- Configures `dependencyResolutionManagement`:
  - Mode: FAIL_ON_PROJECT_REPOS (all deps from central repos)
  - Repositories: Google and Maven Central
- Includes the 3 modules: :shared, :backend, :androidApp

### gradle.properties
Gradle configurations:
- **JVM Args**: -Xmx2048m for better performance
- **Optimization flags**:
  - parallel=true: Parallel builds
  - caching=true: Build caching
  - daemon=true: Gradle daemon
  - configureondemand=true: Configuration on demand
- **Kotlin**: 
  - code.style=official
  - mpp.androidSourceSetLayoutVersion=2
- **Android**:
  - useAndroidX=true
  - nonTransitiveRClass=true

## Build Commands

### List projects
```bash
./gradlew projects
```

### Complete build
```bash
./gradlew build
```

### Build by module
```bash
./gradlew :shared:build
./gradlew :backend:build  
./gradlew :androidApp:build
```

### Run backend
```bash
./gradlew :backend:run
```

### Install Android app
```bash
./gradlew :androidApp:installDebug
```

### Clean builds
```bash
./gradlew clean
```

## Technologies and Versions

| Technology | Version | Purpose |
|------------|---------|---------|
| Kotlin | 2.0.21 | Main language |
| Gradle | 8.11.1 | Build system |
| AGP | 8.2.2 | Android Gradle Plugin |
| Ktor | 3.0.2 | Backend framework |
| Exposed | 1.0.0 | ORM |
| PostgreSQL Driver | 42.7.7 | Database |
| Compose BOM | 2024.12.01 | Android UI |
| Koin | 4.0.1 | Dependency Injection |
| Serialization | 1.7.3 | JSON handling |
| Coroutines | 1.9.0 | Async programming |

## Key Features

### 1. Version Catalog
All dependencies centralized in `gradle/libs.versions.toml`:
```kotlin
// In build.gradle.kts
implementation(libs.ktor.server.core)
implementation(libs.bundles.ktor.server)  // Bundle of multiple deps
```

### 2. Kotlin Multiplatform (:shared)
Shared code between Android and JVM:
```
shared/
  src/
    commonMain/    <- Shared code
    androidMain/   <- Android-specific
    jvmMain/       <- JVM-specific
```

### 3. Dependency Injection with Koin
Configured in all 3 modules:
- :shared -> koin-core
- :backend -> koin-ktor  
- :androidApp -> koin-android + koin-compose

### 4. Kotlin Serialization
Configured in all modules for consistent JSON handling

## Next Steps

1. **Implement DTOs in :shared**
   - Create data classes with @Serializable
   - Define domain models

2. **Configure Backend**
   - Database setup with Exposed
   - Define Ktor routes
   - Implement repositories

3. **Develop Android App**
   - Create UI composables
   - Implement ViewModels
   - Configure navigation

## Requirements

- **JDK**: 17 or higher
- **Android SDK**: For androidApp module
  - Build Tools 35.x
  - Android SDK Platform 35
- **PostgreSQL**: For backend (runtime)
- **IDE**: IntelliJ IDEA or Android Studio

## Notes

- The project uses Gradle 8.11.1 with full support for Kotlin 2.0
- Android minSdk 24 (Android 7.0+) for wide compatibility
- Java 17 is the target for better performance and modern features
- Version Catalog allows centralized dependency updates
