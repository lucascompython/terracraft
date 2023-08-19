plugins {
    // Apply the java plugin to add support for Java
    java
    // Apply the application plugin to add support for building a CLI application.
    application
}


application {
    mainClass.set("terracraft.TerraCraft")
    applicationDefaultJvmArgs = listOf("-Djava.library.path=../TerraCraft/target/debug")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "terracraft.TerraCraft"
    }
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java")
        }
        resources {
            srcDirs("src/main/resources")
        }
    }
}

val main by sourceSets
tasks {
    val generateJniHeaders by creating(JavaCompile::class) {
        doFirst {
            exec {
                commandLine("javac", "-h", "src/main/jniLibs", "src/main/java/terracraft/TerraCraft.java")
            }
        }
    }

    named("compileJava") {
        dependsOn(generateJniHeaders)
    }
}