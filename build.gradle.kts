plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
//    alias(libs.plugins.sqlDelight)
}

group = "io.github.rokuosan"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

kotlin {
    mingwX64("native").apply {
        binaries {
            executable {
                entryPoint = "io.github.rokuosan.scoop_search.main"
                runTask?.standardInput = System.`in`
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime)
                implementation(libs.ktor.core)
                implementation(libs.ktor.client.winhttp)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.multiplatformSettings)
                implementation(libs.clikt)
            }
        }

//        val jvmMain by getting {
//            dependencies {
//                implementation(libs.kotlinx.coroutines.core)
//                implementation(libs.kotlinx.datetime)
//                implementation(libs.ktor.core)
//                implementation(libs.ktor.client.content.negotiation)
//                implementation(libs.ktor.serialization.kotlinx.json)
//                implementation(libs.multiplatformSettings)
//                implementation(libs.ktor.client.winhttp)
//            }
//        }
    }

}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

//sqldelight {
//    databases {
//        create("MyDatabase") {
//            // Database configuration here.
//            // https://cashapp.github.io/sqldelight
//            packageName.set("org.company.app.db")
//        }
//    }
//}
