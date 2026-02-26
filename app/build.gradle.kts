plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt") // Necessário para o compilador do Room
}

android {
    namespace = "com.example.portosmartguide"
    compileSdk = 36 // Mantém a compilação na API mais recente exigida pela loja

    defaultConfig {
        applicationId = "com.example.portosmartguide"
        minSdk = 26 // Ajustado para Android 8.0 para cumprir o RNF02 do relatório
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
}

dependencies {
    // Dependências nativas geradas
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // --- Dependências do Smart Porto Guide ---

    // ViewModel e LiveData (Arquitetura MVVM da Camada Lógica)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")

    // Room (Base de dados local SQLite - Camada de Dados)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Google Maps SDK e Geolocalização
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")

    // Firebase (Para autenticação - RF01/RF03)
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.android.gms:play-services-auth:21.1.1")
}