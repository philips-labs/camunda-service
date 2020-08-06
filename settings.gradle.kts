rootProject.name = "camunda-service"
pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("org.jetbrains.kotlin")) {
                gradle.rootProject.extra["kotlinVersion"]?.let { useVersion(it as String) }
            }
            if (requested.id.id.startsWith("org.springframework.boot")) {
                gradle.rootProject.extra["springBootVersion"]?.let { useVersion(it as String) }
            }
        }
    }
}
