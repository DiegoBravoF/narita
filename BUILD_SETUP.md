# OpenSchool - Build Configuration Guide

## Overview

Este proyecto implementa un Monorepo Kotlin Multiplatform (KMP) llamado "OpenSchool" con Kotlin 2.0, Gradle DSL y Version Catalog.

## Estructura del Proyecto

### Módulos

#### 1. :shared
**Propósito**: Módulo compartido multiplataforma para DTOs (Data Transfer Objects)

**Targets**: 
- Android (minSdk 24, compileSdk 35)
- JVM (Java 17)

**Dependencias**:
- kotlinx-serialization-json: Para serialización/deserialización de DTOs
- kotlinx-coroutines-core: Para programación asíncrona
- koin-core: Para inyección de dependencias

**Configuración**: `shared/build.gradle.kts`

#### 2. :backend  
**Propósito**: Servidor API REST con Ktor

**Target**: JVM (Java 17)

**Tecnologías**:
- **Ktor 3.0.2**: Framework web asíncrono
  - Server Core & Netty
  - Content Negotiation
  - JSON Serialization
  - Call Logging
  - CORS Support
  - Status Pages
- **Exposed 0.57.0**: ORM para Kotlin
  - Core, DAO, JDBC
  - Java Time support
- **PostgreSQL 42.7.4**: Driver de base de datos
- **HikariCP 6.2.1**: Connection pooling
- **Koin**: Dependency injection para Ktor
- **Logback**: Logging

**Configuración**: `backend/build.gradle.kts`

#### 3. :androidApp
**Propósito**: Aplicación móvil Android con Jetpack Compose

**Configuración Android**:
- Application ID: com.openschool.android
- minSdk: 24
- targetSdk: 35  
- compileSdk: 35
- JVM Target: 17

**Tecnologías**:
- **Jetpack Compose**: UI moderno declarativo
  - Compose BOM 2024.12.01
  - Material 3
  - UI Tooling
  - Activity Compose
- **Koin**: Dependency injection para Android y Compose
- **Kotlin Coroutines**: Programación asíncrona
- **Kotlin Serialization**: Manejo de JSON

**Configuración**: `androidApp/build.gradle.kts`

## Archivos de Configuración

### gradle/libs.versions.toml
Version Catalog centralizado que define:
- **Versiones**: Todas las versiones de dependencias en un solo lugar
- **Librerías**: Referencias a bibliotecas con group:artifact:version
- **Plugins**: Plugins de Gradle con IDs y versiones
- **Bundles**: Grupos de dependencias relacionadas (ktor-server, exposed, compose)

Beneficios:
- Gestión centralizada de versiones
- Autocompletado en IDEs
- Type-safe accessors
- Fácil actualización de dependencias

### build.gradle.kts (Root)
Configuración raíz del proyecto:
- Declara plugins sin aplicarlos (`apply false`)
- Configura `allprojects` con group y version comunes
- Define tarea `clean` compartida

### settings.gradle.kts
Configuración del multi-módulo:
- Define nombre del proyecto: "OpenSchool"
- Habilita TYPESAFE_PROJECT_ACCESSORS
- Configura repositorios en `pluginManagement`:
  - Gradle Plugin Portal
  - Google Maven
  - Maven Central
- Configura `dependencyResolutionManagement`:
  - Mode: FAIL_ON_PROJECT_REPOS (todas las deps desde repos centrales)
  - Repositorios: Google y Maven Central
- Incluye los 3 módulos: :shared, :backend, :androidApp

### gradle.properties
Configuraciones de Gradle:
- **JVM Args**: -Xmx2048m para mejor performance
- **Flags de optimización**:
  - parallel=true: Builds paralelos
  - caching=true: Cache de builds
  - daemon=true: Gradle daemon
  - configureondemand=true: Configuración bajo demanda
- **Kotlin**: 
  - code.style=official
  - mpp.androidSourceSetLayoutVersion=2
- **Android**:
  - useAndroidX=true
  - nonTransitiveRClass=true

## Comandos de Build

### Listar proyectos
```bash
./gradlew projects
```

### Build completo
```bash
./gradlew build
```

### Build por módulo
```bash
./gradlew :shared:build
./gradlew :backend:build  
./gradlew :androidApp:build
```

### Ejecutar backend
```bash
./gradlew :backend:run
```

### Instalar app Android
```bash
./gradlew :androidApp:installDebug
```

### Limpiar builds
```bash
./gradlew clean
```

## Tecnologías y Versiones

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Kotlin | 2.0.21 | Lenguaje principal |
| Gradle | 8.11.1 | Sistema de build |
| AGP | 8.2.2 | Android Gradle Plugin |
| Ktor | 3.0.2 | Backend framework |
| Exposed | 0.57.0 | ORM |
| PostgreSQL Driver | 42.7.4 | Database |
| Compose BOM | 2024.12.01 | UI Android |
| Koin | 4.0.1 | Dependency Injection |
| Serialization | 1.7.3 | JSON handling |
| Coroutines | 1.9.0 | Async programming |

## Características Destacadas

### 1. Version Catalog
Todos los dependencies centralizados en `gradle/libs.versions.toml`:
```kotlin
// En build.gradle.kts
implementation(libs.ktor.server.core)
implementation(libs.bundles.ktor.server)  // Bundle de múltiples deps
```

### 2. Kotlin Multiplatform (:shared)
Código compartido entre Android y JVM:
```
shared/
  src/
    commonMain/    <- Código compartido
    androidMain/   <- Específico Android
    jvmMain/       <- Específico JVM
```

### 3. Inyección de Dependencias con Koin
Configurado en los 3 módulos:
- :shared -> koin-core
- :backend -> koin-ktor  
- :androidApp -> koin-android + koin-compose

### 4. Kotlin Serialization
Configurado en todos los módulos para manejo consistente de JSON

## Próximos Pasos

1. **Implementar DTOs en :shared**
   - Crear data classes con @Serializable
   - Definir modelos de dominio

2. **Configurar Backend**
   - Setup de base de datos con Exposed
   - Definir rutas de Ktor
   - Implementar repositorios

3. **Desarrollar Android App**
   - Crear composables de UI
   - Implementar ViewModels
   - Configurar navegación

## Requisitos

- **JDK**: 17 o superior
- **Android SDK**: Para módulo androidApp
  - Build Tools 35.x
  - Android SDK Platform 35
- **PostgreSQL**: Para backend (runtime)
- **IDE**: IntelliJ IDEA o Android Studio

## Notas

- El proyecto usa Gradle 8.11.1 con soporte completo para Kotlin 2.0
- Android minSdk 24 (Android 7.0+) para amplia compatibilidad
- Java 17 es el target para mejor performance y features modernos
- Version Catalog permite actualizaciones centralizadas de dependencias
