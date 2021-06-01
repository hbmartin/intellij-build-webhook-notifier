rootProject.name = "intellij-build-webhook-notifier"

pluginManagement {
    val kotlinVersion: String by settings
    val detektVersion: String by settings
    plugins {
        id("org.jetbrains.kotlin.jvm") version "${kotlinVersion}"
        id("io.gitlab.arturbosch.detekt") version "${detektVersion}"
    }
}
