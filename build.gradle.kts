plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.hiltCompiler) apply false
}

buildscript {
    dependencies {
        classpath(libs.safeargs)
        classpath(libs.okhttpprofiler)
        classpath(libs.okHttpClient)
    }
}