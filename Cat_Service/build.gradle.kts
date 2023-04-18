plugins {
    id("java")
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.flywaydb.flyway") version "9.16.3"
}

group = "org.Cats_Service"
version = "1.0-SNAPSHOT"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
    implementation("org.springframework:spring-web")
    implementation("org.projectlombok:lombok")
    implementation("org.postgresql:postgresql:42.5.1")
    implementation("javax.persistence:javax.persistence-api:2.2")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testCompileOnly("org.projectlombok:lombok")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testAnnotationProcessor("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.flywaydb:flyway-core:9.16.3")
    implementation("org.springframework:spring-beans")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")
}

tasks.test {
    useJUnitPlatform()
}

flyway {
    url = "jdbc:postgresql://localhost:1433/tech_database"
    user = "postgres"
    password = "postgres"
}

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("gradle.plugin.org.flywaydb:gradle-plugin-publishing:9.16.3")
    }
}

apply(plugin = "org.flywaydb.flyway")