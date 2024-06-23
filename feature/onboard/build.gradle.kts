import kr.co.wdtt.convention.Local

plugins {
    alias(libs.plugins.nbdream.android.feature)
    alias(libs.plugins.nbdream.android.library.compose)
    alias(libs.plugins.sgp)
}

android {
    namespace = "kr.co.onboard"

    defaultConfig {
        buildConfigField("String", "KAKAO_API_KEY", "\"kakaoApiKey\"")
        buildConfigField("String", "DATASTORE_NAME", "\"data_store\"")
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(libs.kakao.maps)

    implementation(projects.core.data)
    implementation(libs.kotlinx.serialization.json)
    Local()
}
