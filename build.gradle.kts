import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
val build by tasks.build

plugins {
	id("org.springframework.boot")
	kotlin("jvm")
	kotlin("plugin.spring")
}

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	/**
	 * Common Infrastructure
	 * pull these to utility script
	 */
	//BOMs
	implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	//kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	//testing
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	/**
	 * Project specific dependencies
	 */
	//camunda
	implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter:7.13.0")
	implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-rest:7.13.0")
	implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-webapp:7.13.0")
	//data access
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql:9.4.1212")
	runtimeOnly("mysql:mysql-connector-java")
	//cf env - needed for an HSDP database hack
	implementation("io.pivotal.cfenv:java-cfenv-boot:2.1.1.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-cloudfoundry-connector:2.0.7.RELEASE")
	implementation("org.springframework.cloud:spring-cloud-spring-service-connector:2.0.7.RELEASE")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

val copyJarToSimpleName by tasks.register<Copy>("copyJarToSimpleName"){
	from(bootJar.archiveFile.get().asFile.absolutePath)
	into("${buildDir}/libs")
	rename{ filename: String ->
		filename.replace("-${project.version}", "")
	}
	dependsOn(bootJar)
}

build.dependsOn(copyJarToSimpleName)
