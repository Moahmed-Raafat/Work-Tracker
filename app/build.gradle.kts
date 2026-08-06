plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.worktracker"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.worktracker"
        minSdk = 29
        targetSdk = 36
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    //hilt dagger
    implementation ("com.google.dagger:hilt-android:2.51.1")
    kapt  ("com.google.dagger:hilt-android-compiler:2.51.1")
    kapt ("androidx.hilt:hilt-compiler:1.2.0")
    implementation ("androidx.activity:activity-ktx:1.9.3")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    //viewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //navigation drawer
    implementation("androidx.navigation:navigation-compose:2.8.9")

    implementation ("androidx.core:core-splashscreen:1.0.1")

    implementation ("androidx.activity:activity-compose:1.8.0")
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    //implementation ("com.cloudinary:cloudinary-android:2.3.1")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.31.5-beta")

    //navigation
    implementation("androidx.navigation:navigation-compose:2.9.8")

    //room
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt( "androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")

    //splash screen
    implementation ("androidx.core:core-splashscreen:1.2.0")

}