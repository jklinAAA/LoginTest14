plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

}

android {
    namespace = "com.example.logintest14"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.logintest14"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments ["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    configurations.all{
        resolutionStrategy{
            exclude("com.google.guava",  "AbstractResolvableFuture")
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    //kotlinOptions{
        //jvmTarget = "11"
 //  }


buildFeatures{
    viewBinding= true ;
    dataBinding=true ;
}


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core:1.5.0-alpha04")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.room:room-runtime:2.6.0")
    annotationProcessor( "androidx.room:room-compiler:2.6.0:")

    //glide
    implementation ( "com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    //chat
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    //圆角        item_note      <de.hdodenhof.circleimageview.CircleImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")

}