plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.ecommerceapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ecommerceapp"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-firestore:24.10.1")
    implementation("com.google.firebase:firebase-database:20.3.0")
    val  nav_version = "2.7.6"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")



    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.0")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-analytics:21.5.0")
    implementation("com.google.firebase:firebase-analytics-ktx:21.5.0")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-firestore:24.10.1")
    implementation ("com.google.firebase:firebase-firestore-ktx:24.10.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")


    //slide show
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")

    //room db
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

}