import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.3.61"
  kotlin("plugin.spring") version "1.3.61"
  id("org.springframework.boot") version "2.2.2.RELEASE"
  id("io.spring.dependency-management") version "1.0.8.RELEASE"
  // uncomment to build a deployable artifact
  //war
}

version="1.0.0-SNAPSHOT"

repositories {
  jcenter()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

// uncomment to build a deployable artifact
//tasks.war {
//  isEnabled = true
//}

tasks.wrapper {
  gradleVersion = "6.1"
  distributionType = Wrapper.DistributionType.ALL
}
