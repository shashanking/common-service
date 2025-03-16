plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.bannrx"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	var env = project.findProperty("environment") as String? ?: System.getenv("ENVIRONMENT")
	if (env != "local"){
		val user: String? = project.findProperty("username") as String? ?: System.getenv("GITHUB_USERNAME")
		val token: String? = project.findProperty("token") as String? ?: System.getenv("GITHUB_TOKEN")
		val repo = "bannrx-common"
		val gitUrl = "https://maven.pkg.github.com/${user}/${repo}"
		maven {
			name = "bannrx-common-package"
			url = uri(gitUrl)
			credentials {
				username = user
				password = token
			}
		}
		val utilityUser: String? = project.findProperty("utility-username") as String? ?: System.getenv("GITHUB_USERNAME")
		val utilityToken: String? = project.findProperty("utility-token") as String? ?: System.getenv("GITHUB_TOKEN")
		val utilityRepo = "utility"
		val utilityGitUrl = "https://maven.pkg.github.com/${utilityUser}/${utilityRepo}"
		maven {
			name = "GitHubPackages"
			url = uri(utilityGitUrl)
			credentials {
				username = utilityUser
				password = utilityToken
			}
		}
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation ("org.modelmapper:modelmapper:3.2.0")
	implementation("jakarta.validation:jakarta.validation-api:3.1.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.0")
	implementation("org.apache.commons:commons-lang3:3.17.0")


	//added
	var env = project.findProperty("environment") as String? ?: System.getenv("ENVIRONMENT")
	if (env == "local"){
		implementation(project(":bannrx-common")) {
			exclude(group="com.rklab", module="utility")
		}
		implementation(project(":utility"))
	} else {
		implementation("com.bannrx:common:base-0.0.1-SNAPSHOT"){
			exclude(group="com.rklab", module="utility")
		}
		implementation("com.rklab:utility:0.0.1-SNAPSHOT")
	}
	implementation("org.springframework.boot:spring-boot-starter-security:3.3.4")
	implementation("org.springframework.security:spring-security-config:6.2.4")
	implementation("org.springframework.boot:spring-boot-starter-aop")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
