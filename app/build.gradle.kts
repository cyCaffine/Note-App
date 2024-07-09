plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.menkashah.notesapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.menkashah.notesapp"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation ("com.google.android.gms:play-services-auth:19.2.0")

    implementation("androidx.recyclerview:recyclerview:1.2.1") // For RecyclerView
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.3.1") // For LiveData and ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata:2.3.1")
    implementation("androidx.room:room-runtime:2.3.0") // For Room database
//    kapt("androidx.room:room-compiler:2.3.0") // For Room annotation processing


    implementation ("com.google.android.gms:play-services-auth:20.3.0")
    implementation("com.google.android.gms:play-services-auth:19.0.0") // For Google Sign-In (optional)
    implementation("com.google.android.gms:play-services-auth-api-phone:18.0.1") // For Phone Authentication (optional)
}

