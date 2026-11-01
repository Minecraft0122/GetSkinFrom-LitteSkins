plugins {
    id("java")
}

allprojects {
    group = "in.littlesk.plugin"
    version = System.getenv("LSP_VERSION") ?: "1.2.1"
    extra.set("pluginId", "getskinfrom-littleskins")
    description = "Plugin for loading player textures from LittleSkins."

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
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

subprojects {
    apply(plugin = "java")
    version = rootProject.version
    description = rootProject.description

    afterEvaluate {
        val targetJavaVersion: Int = if (extra.has("targetJava")) {
            extra.get("targetJava").toString().toInt()
        } else {
            8
        }

        tasks.withType<JavaCompile>().configureEach {
            options.encoding = "UTF-8"
            options.release.set(targetJavaVersion)
        }
    }

    tasks.withType<ProcessResources> {
        val props = mapOf(
            "group" to rootProject.group,
            "pluginId" to rootProject.extra["pluginId"],
            "version" to rootProject.version,
            "name" to rootProject.name,
            "description" to rootProject.description
        )
        inputs.properties(props)
        filteringCharset = "UTF-8"

        filesMatching(listOf(
            "plugin.yml",
            "bungee.yml",
            "velocity-plugin.json"
        )) {
            expand(props)
        }
    }
}

tasks.jar {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}
