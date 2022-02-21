apply(plugin = "maven-publish")
apply(plugin = "signing")
apply(plugin = "org.jetbrains.dokka")


val mavenUrl: String by extra
val mavenSnapshotUrl: String by extra
val signingKey: String? by project
val signingPassword: String? by project
val sonatypeUsername: String? by project
val sonatypePassword: String? by project
val pomProjectUrl: String by project
val pomProjectDescription: String by project
val pomScmUrl: String by project
val pomDeveloperId: String by project
val pomDeveloperName: String by project
val pomLicenseName: String by project
val pomLicenseUrl: String by project
val pomLicenseDistribution: String by project

task<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
}

configure<PublishingExtension> {
    components.findByName("java")?.also { javaComponent ->
        task<Jar>("sourcesJar") {
            archiveClassifier.set("sources")
            val sourceSets = project.extensions.getByName<SourceSetContainer>("sourceSets")
            from(sourceSets["main"].allSource)
        }
        publications.create<MavenPublication>("mavenJava") {
            from(javaComponent)
            artifact(tasks["sourcesJar"])
        }
    }
    publications.withType<MavenPublication>.configureEach {
        artifact(tasks.named("javadocJar"))
        pom {
            name.set("Kotlin Multiplatform TMDB API")
            description.set("A Kotlin Multiplatform library to access the TMDB API.")
            url.set("https://github.com/MoviebaseApp/tmdb-api")
            inceptionYear.set("2021")

            developers {
                developer {
                    id.set("chrisnkrueger")
                    name.set("Christian Krüger")
                    email.set("christian.krueger@moviebase.app")
                }
            }
            licenses {
                license {
                    name.set("The Apache Software License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            issueManagement {
                system.set("GitHub Issues")
                url.set("https://github.com/MoviebaseApp/tmdb-api/issues")
            }
            scm {
                connection.set("scm:git:https://github.com/MoviebaseApp/tmdb-api.git")
                developerConnection.set("scm:git:git@github.com:MoviebaseApp/tmdb-api.git")
                url.set("https://github.com/MoviebaseApp/tmdb-api")
            }
        }
    }
    repositories {
        maven {
            url = if (version.toString().endsWith("SNAPSHOT")) {
                uri(mavenSnapshotUrl)
            } else {
                uri(mavenUrl)
            }
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
    }
}

configure<SigningExtension> {
    isRequired = !version.toString().endsWith("SNAPSHOT")
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign((extensions["publishing"] as PublishingExtension).publications)
}