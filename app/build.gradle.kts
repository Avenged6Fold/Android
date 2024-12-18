plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.project.jemberliburan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.jemberliburan"
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.google.material)
    implementation(libs.viewpager2)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.android.volley:volley:1.2.1")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation ("com.tbuonomo:dotsindicator:5.1.0")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("com.github.yalantis:ucrop:2.2.8")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

}