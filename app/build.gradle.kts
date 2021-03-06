plugins {
    id("com.android.application")
    kotlin("android.extensions")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}
android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.murtaza0xff.harbour"
        minSdkVersion(23)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":firebaseapi"))
    implementation(project(":user"))
    implementation(project(":algolia"))

    implementation(Libs.navigation_runtime_ktx)
    implementation(Libs.navigation_ui_ktx)
    implementation(Libs.appcompat)
    implementation(Libs.androidx_core_core)
    implementation(Libs.core_ktx)
    implementation(Libs.constraintlayout)
    implementation(Libs.material)
    implementation(Libs.fragment)
    implementation(Libs.fragment_ktx)
    implementation(Libs.preference)
    implementation(Libs.preference_ktx)
    implementation(Libs.timber)
    implementation(Libs.viewpager2)

    implementation(Libs.adapter_rxjava2)
    implementation(Libs.logging_interceptor)
    implementation(Libs.converter_moshi)
    implementation(Libs.retrofit)
    implementation(Libs.okhttp)
    implementation(Libs.kotlin_stdlib_jdk7)
    implementation(Libs.dagger)
    implementation(Libs.firebase_database)
    implementation(Libs.rxandroid)
    implementation(Libs.rxjava)
    implementation(Libs.moshi_kotlin)
    kapt(Libs.dagger_compiler)
    testImplementation(Libs.junit)
    testImplementation(Libs.junit_jupiter_api)
    testRuntimeOnly(Libs.junit_jupiter_engine)
    testImplementation(Libs.junit_jupiter_params)
    testImplementation(Libs.mockk)
    implementation(Libs.rxbinding)
    implementation(Libs.rxbinding_appcompat)
    implementation(Libs.rxbinding_core)
    implementation(Libs.mobius_core)
    testImplementation(Libs.mobius_test)
    implementation(Libs.mobius_rx2)
    implementation(Libs.mobius_extras)
    implementation(Libs.mobius_android)
}

apply(plugin = "com.google.gms.google-services")
