# OpenSchool - KMP Monorepo

School Management System built with Kotlin Multiplatform (KMP).

## Architecture

This is a Kotlin Multiplatform (KMP) monorepo using Kotlin 2.0, Gradle DSL, and Version Catalog.

### Modules

1. **:shared** - Shared Kotlin Multiplatform module
   - Targets: Android & JVM
   - Purpose: Data Transfer Objects (DTOs) and shared business logic
   - Tech: Kotlin Serialization, Koin DI

2. **:backend** - Server application  
   - Target: JVM
   - Tech: Ktor, Exposed ORM, PostgreSQL, Koin DI
   - Purpose: REST API server

3. **:androidApp** - Android application
   - Target: Android
   - Tech: Jetpack Compose, Koin DI
   - Purpose: Mobile client

## Tech Stack

- **Kotlin**: 2.0.21
- **Gradle**: 8.11.1
- **Android Gradle Plugin**: 8.2.2
- **Ktor**: 3.0.2  
- **Exposed**: 0.57.0
- **Jetpack Compose**: BOM 2024.12.01
- **Koin**: 4.0.1
- **Kotlin Serialization**: 1.7.3
- **PostgreSQL**: 42.7.4

## Build Configuration

The project uses Gradle Version Catalog (`gradle/libs.versions.toml`) for centralized dependency management.

### Building

```bash
# Build all modules
./gradlew build

# Build specific module
./gradlew :backend:build
./gradlew :androidApp:build
./gradlew :shared:build

# Run backend server
./gradlew :backend:run

# Install Android app
./gradlew :androidApp:installDebug
```

### Requirements

- JDK 17 or higher
- Android SDK (for Android module)
- PostgreSQL (for backend)

## Project Structure

```
OpenSchool/
├── gradle/
│   └── libs.versions.toml      # Version catalog
├── shared/                      # Shared KMP module
│   ├── build.gradle.kts
│   └── src/
│       ├── commonMain/          # Shared Kotlin code
│       ├── androidMain/         # Android-specific code
│       └── jvmMain/             # JVM-specific code
├── backend/                     # Ktor backend
│   ├── build.gradle.kts
│   └── src/main/
│       ├── kotlin/
│       └── resources/
├── androidApp/                  # Android app
│   ├── build.gradle.kts
│   └── src/main/
│       ├── kotlin/
│       └── res/
├── build.gradle.kts             # Root build configuration
├── settings.gradle.kts          # Multi-module settings
└── gradle.properties            # Gradle properties
```

## Development

This project uses:
- Kotlin Serialization for JSON handling
- Koin for dependency injection across all modules
- Exposed ORM for database access in the backend
- Jetpack Compose for the Android UI
- Ktor for the backend API

---

Built with ❤️ using Kotlin Multiplatform

