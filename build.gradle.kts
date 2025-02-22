plugins {
    // Java support
    id("java")
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "1.7.20"
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.12.0"
}

val pluginVersion = "1.5.3-chizganov"

group = "com.darkyen"
version = pluginVersion

// Configure project's dependencies
repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    type.set("IC")
    version.set("2022.2.3")
    //version.set("IC-212.4746.92")
    updateSinceUntilBuild.set(false)
    instrumentCode.set(false)
}

tasks {
    // Set the JVM compatibility versions
    // Java language level used to compile sources and to generate the files for - Java 11 is required since 2020.3
    val javaVersion = "11"
    withType<JavaCompile> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = javaVersion
    }

    patchPluginXml {
        version.set(pluginVersion)
    }

    // Because it is broken and crashes the build
    buildSearchableOptions.get().enabled = false

    runIde.configure {
        jvmArgs!!.add("-Didea.ProcessCanceledException=disabled")
    }
}