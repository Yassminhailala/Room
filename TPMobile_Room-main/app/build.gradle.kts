plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.roomwordssample"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.roomwordssample"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Composant de base de données Room
    implementation(libs.room.runtime)
    implementation(libs.activity)
    annotationProcessor(libs.room.compiler)
    testImplementation(libs.room.testing)
    implementation ("com.google.android.material:material:1.12.0")
    // Composants de cycle de vie
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.common.java8)
    implementation ("com.google.android.material:material:1.9.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}