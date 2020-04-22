object AppMetaData {
    const val compileSdkVersion = 29
    const val targetSdkVersion = 29
    const val minSdkVersion = 21

}

object Versions {
    // Application
    const val kotlin = "1.3.61"
    const val navigation = "2.1.0"
    const val safeArgs = "1.0.0-rc01"
    const val gradle = "3.6.0-rc01"
    const val sonar = "2.8"
    const val jacoco = "0.1.4"

    // Core
    const val lifecycle = "2.2.0-alpha03"
    const val koin = "2.0.1"
    const val coroutines = "1.3.2"
    const val okHttp = "3.12.0"
    const val retrofit = "2.6.0"
    const val retrofitCoroutines = "0.9.2"
    const val paging = "2.1.0-rc01"

    // UI
    const val constraintLayout = "1.1.3"
    const val coil = "0.10.0"
    const val material = "1.1.0-alpha08"
    const val recyclerView = "1.1.0"

    // Test
    const val jUnit4 = "4.12"
    const val testRunner = "1.2.0"
    const val spMock = "1.0"
    const val coreTesting = "1.0.0-alpha3"
    const val mockk = "1.9.3"
    const val hamCrest = "2.2"

}

object Libraries {

    const val coil = "io.coil-kt:coil:${Versions.coil}"

    //region CORE
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    val lifecycle =
        listOf(lifecycleExtensions, lifecycleViewModel, lifecycleLiveData, lifecycleRuntime)
    const val koinXcore = "org.koin:koin-core:${Versions.koin}"
    const val koinXscope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koinXviewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
        val koin = listOf(koinXcore, koinXscope, koinXviewModel)
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val coroutines = listOf(coroutinesCore, coroutinesAndroid)
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    val navigation = listOf(navigationFragment, navigationUi)
    const val paging = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
    //endregion

    //region HTTP
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okhttp3Interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    val okhttp = listOf(okhttp3, okhttp3Interceptor)
    const val retrofitCore = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitCoroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutines}"
    val retrofit = listOf(retrofitCore, retrofitGsonConverter, retrofitCoroutines)
    //endregion

    //region UI
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    val ui = listOf(constraintLayout, material, recyclerView)
    //endregion

    //region TEST
    const val jUnit4 = "junit:junit:${Versions.jUnit4}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    val jUnit = listOf(jUnit4, testRunner)
    const val hamCrest = "org.hamcrest:hamcrest:${Versions.hamCrest}"
    const val testCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val spMock = "com.github.IvanShafran:shared-preferences-mock:${Versions.spMock}"
    const val coreTesting = "android.arch.core:core-testing:${Versions.coreTesting}"
    //endregion

}

object Modules {
    const val app = ":app"
    const val common = ":common"
    const val data = ":data"
    const val heroes = ":heroes"
    const val comic = ":comics"
}

object GradleTemplates {
    const val feature = "template-feature.gradle"
}