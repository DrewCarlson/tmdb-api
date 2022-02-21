plugins {
    id("io.github.gradle-nexus.publish-plugin") version Versions.nexus
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", Versions.kotlin))
        classpath(kotlin("serialization", Versions.kotlin))
        classpath(Plugins.dokka)
    }
}

group = "org.drewcarlson"
version = Versions.versionName

System.getenv("GITHUB_REF")?.let { ref ->
    if (ref.startsWith("refs/tags/v")) {
        version = ref.substringAfterLast("refs/tags/v")
    }
}

nexusPublishing {
    val sonatypeUsername: String? by project
    val sonatypePassword: String? by project
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(sonatypeUsername)
            password.set(sonatypePassword)
        }
    }
}

allprojects {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}
