package com.alanvan.buildsrc

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:4.2.0-alpha08"

    const val junit = "junit:junit:4.13"

    const val material = "com.google.android.material:material:1.2.1"

    object Kotlin {
        private const val version = "1.4.0"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Google {
        object Services {
            private const val version = "4.3.3"
            const val std = "com.google.gms:google-services:$version"
        }

        object Analytics {
            private const val version = "17.5.0"
            const val analytics = "com.google.firebase:firebase-analytics:17.5.0"
        }

        object Auth {
            private const val version = "19.3.2"
            const val auth = "com.google.firebase:firebase-auth-ktx:$version"
        }

        object FireStore {
            private const val version = "21.6.0"
            const val firestore = "com.google.firebase:firebase-firestore-ktx:$version"
        }

        object Storage {
            private const val version = "19.2.0"
            const val storage = "com.google.firebase:firebase-storage-ktx:$version"
        }
    }

    object Coroutines {
        private const val version = "1.3.9"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object KtLint {
        private const val version = "0.39.0"
        const val ktlint = "com.pinterest:ktlint:$version"
    }

    object Koin {
        private const val version = "2.2.0-alpha-1"

        const val std = "org.koin:koin-android:$version"
        const val scope = "org.koin:koin-android-scope:$version"
        const val viewmodel = "org.koin:koin-android-viewmodel:$version"
    }

    object Glide {
        private const val version = "4.11.0"

        const val glide = "com.github.bumptech.glide:glide:$version"
        const val kapt = "com.github.bumptech.glide:compiler:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val coreKtx = "androidx.core:core-ktx:1.5.0-alpha01"

        object ConstraintLayout {
            private const val version = "2.0.1"

            const val constraintlayout = "androidx.constraintlayout:constraintlayout:$version"
        }

        object ViewPager2 {
            private const val version = "1.0.0"
            const val viewpager2 = "androidx.viewpager2:viewpager2:$version"
        }

        object Compose {
            const val snapshot = ""
            private const val version = "1.0.0-alpha02"

            const val core = "androidx.compose.ui:ui:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.ui:ui-tooling:$version"
            const val test = "androidx.compose.test:test-core:$version"
            const val uiTest = "androidx.ui:ui-test:$version"
        }

        object Navigation {
            private const val version = "2.3.0"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Lifecycle {
            private const val version = "2.2.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2-rc01"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }
    }
}