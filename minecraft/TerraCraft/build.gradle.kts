import com.google.protobuf.gradle.*
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar



buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.4.2")
    }
}

plugins {
    java
    id("com.google.protobuf") version "0.9.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.terracraft"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}






dependencies {
    implementation("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("io.grpc:grpc-netty-shaded:1.62.2")
    implementation("io.grpc:grpc-protobuf:1.62.2")
    implementation("io.grpc:grpc-protobuf-lite:1.62.2")
    implementation("io.grpc:grpc-stub:1.62.2")
    implementation("io.perfmark:perfmark-api:0.27.0")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53") // necessary for Java 9+


    // add proguard


//    implementation("com.google.protobuf:protobuf-java:3.25.3")

}

// create the source set for proto files
sourceSets {
    create("proto") {
        java.srcDir("build/generated/source/proto/main")
    }
}



protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.62.2"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without options.
                id("grpc") { }
            }
        }
    }
}


val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}


tasks.withType<JavaCompile>().configureEach {
    dependsOn("generateProto")
    options.encoding = "UTF-8"

    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

tasks.withType<Copy>().named("processResources") {
    val props = mapOf("version" to project.version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.withType<ShadowJar> {
//    configurations = listOf(project.configurations.getByName("compileClasspath"))
//    archiveClassifier.set(null)
    // set classifier to null to avoid creating a separate jar for the shadow jar
//    archiveClassifier.set("shadow")
//    minimize{
//        exclude(dependency("io.grpc.MethodDescriptor:.*:.*"))
//        exclude(dependency("com.google.protobuf:.*:.*"))
//    }
    // TODO: Try to add minimize()
}

tasks.register<proguard.gradle.ProGuardTask>("proguard") {
//    dependsOn("shadowJar")
    injars(tasks.named("shadowJar"))
    outjars("build/TerraCraft-Optimized.jar")

    val javaHome = System.getProperty("java.home")
    // Automatically handle the Java version of this build.
    if (System.getProperty("java.version").startsWith("1.")) {
        // Before Java 9, the runtime classes were packaged in a single jar file.
        libraryjars("$javaHome/lib/rt.jar")
    } else {
        // As of Java 9, the runtime classes are packaged in modular jmod files.
        libraryjars(
                // filters must be specified first, as a map
                mapOf("jarfilter" to "!**.jar",
                        "filter"    to "!module-info.class"),
                "$javaHome/jmods/java.base.jmod"
        )
    }
    allowaccessmodification()
    repackageclasses("")
    dontwarn()

    dontobfuscate()
//    dontshrink()
    dontoptimize()
    configuration("proguard-rules.pro")
}