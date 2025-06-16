plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

}

android {
    // CRÍTICO: Asegúrate que esto coincida con tu google-services.json y el package en tus archivos Kotlin
    namespace = "com.maxpro.maxmente" // CAMBIADO para coincidir con el resto del proyecto
    compileSdk = 35

    defaultConfig {
        // CRÍTICO: Asegúrate que esto coincida con tu google-services.json
        applicationId = "com.maxpro.maxmente" // CAMBIADO para coincidir con el resto del proyecto
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

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        // Verifica la compatibilidad de esta versión con tu versión de Kotlin y la BoM de Compose
        // https://developer.android.com/jetpack/androidx/releases/compose-kotlin
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation("androidx.activity:activity-compose:1.9.0")

    // Compose BoM - Gestiona las versiones de las dependencias de Compose
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3") // Para Material 3

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // ViewModel con Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // Considera actualizar a 1.8.x si es compatible

    // DataStore (si lo usas para persistencia de preferencias)
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Firebase BoM - RECOMENDADO para gestionar versiones de Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.0.0")) // Usa la última versión de la BoM de Firebase

    // Firebase Authentication (KTX para extensiones de Kotlin)
    // Ya no necesitas especificar la versión aquí si usas la BoM
    implementation("com.google.firebase:firebase-auth-ktx")
    // Si necesitas otras dependencias de Firebase (ej. Firestore, Analytics), añádelas aquí sin versión
    // implementation("com.google.firebase:firebase-firestore-ktx")
    // implementation("com.google.firebase:firebase-analytics-ktx")
    dependencies {
        // ... tus otras dependencias de Firebase (auth, etc.) y Compose ...
        implementation(platform("com.google.firebase:firebase-bom:33.1.1")) // Asegúrate de tener el BOM y que esté actualizado
        implementation("com.google.firebase:firebase-firestore-ktx") // Para Firestore con extensiones Kotlin
    }

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00")) // BoM también para tests de Compose
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Debugging
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
