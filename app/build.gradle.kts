import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.jaewchoi.barcodescanner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jaewchoi.barcodescanner"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["appAuthRedirectScheme"] = "com.jaewchoi.barcodescanner"
        buildConfigField("String", "OAUTH_CLIENT_ID", getLocalProperty("client_id"))
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    val kotlinVersion: String by project
    val appCompatVersion: String by project
    val materialVersion: String by project
    val constraintLayoutVersion: String by project
    val junitVersion: String by project
    val testExtJunitVersion: String by project
    val espressoVersion: String by project
    val viewModelKtxVersion: String by project
    val activityKtxVersion: String by project
    val fragmentKtxVersion: String by project
    val coroutineVersion: String by project
    val retrofitVersion: String by project
    val hiltVersion: String by project
    val cameraxVersion: String by project
    val mlkitVersion: String by project
    val navVersion: String by project
    val appAuthVersion: String by project
    val datastoreVersion: String by project
    val roomVersion: String by project

    implementation("androidx.core:core-ktx:$kotlinVersion")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$testExtJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelKtxVersion")
    implementation("androidx.activity:activity-ktx:$activityKtxVersion")
    implementation("androidx.fragment:fragment-ktx:$fragmentKtxVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    implementation("androidx.camera:camera-core:$cameraxVersion")
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")
    implementation("androidx.camera:camera-mlkit-vision:$cameraxVersion")

    implementation("com.google.mlkit:barcode-scanning:$mlkitVersion")

    implementation("androidx.navigation:navigation-fragment:$navVersion")
    implementation("androidx.navigation:navigation-ui:$navVersion")

    implementation("net.openid:appauth:$appAuthVersion")

    implementation("androidx.datastore:datastore-preferences:$datastoreVersion")

    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
}

kapt {
    correctErrorTypes = true
}

fun getLocalProperty(key: String): String {
    val props = Properties()
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { props.load(it) }
    }
    return props.getProperty(key, "")
}
