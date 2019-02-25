plugins {
    id("com.android.application")
    kotlin("android.extensions")
    kotlin("android")
    kotlin("kapt")
}
android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.murtaza0xff.harbour"
        minSdkVersion(15)
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
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.kotlin_stdlib)

    implementation(Deps.Dagger.runtime)
    kapt(Deps.Dagger.compiler)

    implementation(Deps.Android.Androidx.annotations)
    implementation(Deps.Android.Androidx.appCompat)
    implementation(Deps.Android.Androidx.core)
    implementation(Deps.Android.Androidx.constraintLayout)
    implementation(Deps.Android.Androidx.design)
    implementation(Deps.Android.Androidx.fragment)
    implementation(Deps.Android.Androidx.fragmentKtx)
    implementation(Deps.Android.Androidx.palette)
    implementation(Deps.Android.Androidx.paletteKtx)
    implementation(Deps.Android.Androidx.preference)
    implementation(Deps.Android.Androidx.preferenceKtx)
    implementation(Deps.Android.Androidx.viewPager)
    implementation(Deps.Android.Androidx.swipeRefresh)

    implementation(Deps.Android.Androidx.Lifecycle.extensions)
    kapt(Deps.Android.Androidx.Lifecycle.apt)
    implementation(Deps.Android.Androidx.Room.runtime)
    implementation(Deps.Android.Androidx.Room.rxJava2)
    kapt(Deps.Android.Androidx.Room.apt)

    implementation(Deps.Android.Androidx.appCompat)
    implementation("com.android.support.constraint:constraint-layout:1.1.3")

    implementation(Deps.Okhttp.core)
    implementation(Deps.Misc.okio)
    implementation(Deps.Retrofit.core)
    implementation(Deps.Retrofit.moshi)
    implementation(Deps.Retrofit.rxJava2)
    implementation(Deps.Retrofit.coroutines)
    implementation(Deps.Rx.android)
    implementation(Deps.Rx.java)
    implementation(Deps.Rx.Binding.core)
    implementation(Deps.Rx.Binding.v4)
    implementation(Deps.Rx.Binding.design)
    implementation(Deps.Misc.timber)
    debugImplementation(Deps.Misc.Debug.telescope)
    debugImplementation(Deps.Okhttp.Debug.loggingInterceptor)
    debugImplementation(Deps.Retrofit.Debug.mock)
    implementation(Deps.Misc.timber)
    implementation(Deps.Misc.lottie)
    debugImplementation(Deps.Stetho.Debug.core)
    debugImplementation(Deps.Stetho.Debug.okhttp)
    debugImplementation(Deps.Stetho.Debug.timber)

    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Rx.relay)
    androidTestImplementation(Deps.Rx.java)
    testImplementation(Deps.Test.robolectric)
    testImplementation(Deps.Okhttp.Test.mockwebserver)
    testImplementation(Deps.Test.mockk)
    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

}