plugins {
    kotlin("multiplatform") version "2.0.21"
    `maven-publish`
}

group = "com.bangbang93.file.tsk"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    
    js(IR) {
        nodejs()
        useCommonJs()
        binaries.executable()
        
        compilations["main"].packageJson {
            customField("name", "tsk-file")
            customField("version", version)
        }
        
        compilations["test"].packageJson {
            customField("name", "tsk-file-test-internal")
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            groupId = "com.bangbang93.file.tsk"
            artifactId = "tsk-file"
            version = project.version.toString()
        }
    }
}
