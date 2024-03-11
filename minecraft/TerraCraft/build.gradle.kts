import com.google.protobuf.gradle.*
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar;

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
    implementation("io.perfmark:perfmark-api:0.23.0")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53") // necessary for Java 9+
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
    configurations = listOf(project.configurations.getByName("compileClasspath"))
    // TODO: Try to add minimize()
}
