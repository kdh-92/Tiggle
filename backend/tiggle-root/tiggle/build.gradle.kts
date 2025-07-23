import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.jetbrains.kotlin.jvm") version "1.9.25"
	id("org.jetbrains.kotlin.plugin.spring") version "1.9.25"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.9.25"
	id("org.jetbrains.kotlin.kapt") version "1.9.25"
	id("jacoco")
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://repo.spring.io/milestone")
	}
	maven {
		url = uri("https://repo.spring.io/snapshot")
	}
}


dependencies {
	implementation ("org.jetbrains.kotlin:kotlin-reflect:1.9.25")
	implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.1")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa:3.5.0")
	implementation ("org.springframework.boot:spring-boot-starter-oauth2-client:3.5.0")
	implementation ("org.springframework.boot:spring-boot-starter-security:3.5.0")
	implementation ("org.springframework.boot:spring-boot-starter-web:3.5.0")
	implementation ("org.springframework.boot:spring-boot-starter-validation:3.5.0")
    implementation ("org.springframework.boot:spring-boot-devtools:3.5.0")
	implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")
//	testImplementation 'org.springframework.security:spring-security-test'

	// configurationProperties를 사용하기 위해 추가
//	kapt "org.springframework.boot:spring-boot-configuration-processor"

//	// h2
//	runtimeOnly 'com.h2database:h2'

	// jwt
	implementation ("io.jsonwebtoken:jjwt-api:0.12.6")
	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.12.6")

	// DB 셋업이 없는 동안 임시 설정
	runtimeOnly ("com.h2database:h2:2.2.224")

	// test
	testImplementation ("org.springframework.boot:spring-boot-starter-test:3.5.0")
	testImplementation ("io.kotest:kotest-runner-junit5-jvm:5.3.2")
	testImplementation ("io.kotest:kotest-assertions-core:5.3.2")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
	testImplementation ("io.mockk:mockk:1.12.4")

	implementation ("org.springframework.kafka:spring-kafka:3.1.2")
	implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.1")
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.Embeddable")
	annotation("jakarta.persistence.MappedSuperclass")
}

tasks.named<Test>("test") {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.withType<KotlinCompile>().configureEach {
	kotlinOptions {
		freeCompilerArgs = freeCompilerArgs + listOf("-Xjsr305=strict")
		jvmTarget = "21"
	}
}

tasks.jar {
	enabled = false
}

jacoco {
	toolVersion = "0.8.12"
	reportsDirectory.set(layout.buildDirectory.dir("reports/jacoco"))
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		html.required.set(true)
		xml.required.set(false)
		csv.required.set(false)
	}
	// 필요하면 경로 지정 가능
	// html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			element = "CLASS"
			enabled = true

			limit {
				counter = "LINE"
				value = "COVEREDRATIO"
				minimum = BigDecimal(0.0)
			}

			limit {
				counter = "BRANCH"
				value = "COVEREDRATIO"
				minimum = BigDecimal(0.0)
			}

			excludes = listOf(
				"com.side.tiggle.TiggleApplication", // 메인 클래스 제외
				"**.dto.**",                         // DTO 제외 예시
				"**.config.**"                       // Config 제외 예시
			)
		}
	}
}

tasks.check {
	dependsOn(tasks.jacocoTestCoverageVerification)
}
