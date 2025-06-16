// MaxMente/settings.gradle.kts
pluginManagement {
    repositories {
        google {
            content {
                // Corregido: Se escapa la barra invertida para Kotlin,
                // de modo que la expresión regular reciba "\."
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("org\\.jetbrains\\.kotlin.*") // AÑADIDO: Para incluir plugins de Kotlin como el de Compose
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MaxMente"
include(":app")
