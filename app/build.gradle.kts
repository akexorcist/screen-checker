import java.util.Properties

plugins {
    id("com.android.application")
}

val versionCodeOffset = 217
val releaseVersionName: String? = System.getenv("RELEASE_VERSION_NAME")
val releaseRunNumber: String? = System.getenv("GITHUB_RUN_NUMBER")

if (releaseVersionName != null) {
    require(Regex("""^\d+\.\d+\.\d+$""").matches(releaseVersionName)) {
        "RELEASE_VERSION_NAME must be in X.Y.Z format, got: $releaseVersionName"
    }
}

android {
    namespace = "app.akexorcist.checkscreen"
    compileSdk = 37

    defaultConfig {
        applicationId = "app.akexorcist.checkscreen"
        minSdk = 21
        targetSdk = 37
        versionCode = releaseRunNumber?.let { it.toInt() + versionCodeOffset } ?: 217
        versionName = releaseVersionName ?: "2.4.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val properties = Properties()
    if (File("local.properties").exists()) {
        properties.load(File("local.properties").inputStream())
    }

    signingConfigs {
        create("release") {
            storeFile = properties.getProperty("keystore_path")?.let { file(it) }
            storePassword = properties.getProperty("keystore_password")
            keyAlias = properties.getProperty("keystore_key_alias")
            keyPassword = properties.getProperty("keystore_key_password")

            enableV3Signing = true
            enableV4Signing = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    implementation(project(path = ":screenChecker"))
    implementation("com.google.android.gms:play-services-instantapps:18.2.0")
}
