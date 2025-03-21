plugins {
    java
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation(platform(libs.projectreactor.bom))
    implementation("io.projectreactor:reactor-core")
    implementation("io.projectreactor.netty:reactor-netty-core")
    implementation("io.projectreactor.netty:reactor-netty-http")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}