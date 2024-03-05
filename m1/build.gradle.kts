import java.nio.charset.StandardCharsets

plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example.app3"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}
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
	compileOnly("org.projectlombok:lombok")
	//developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	testImplementation("org.springframework.amqp:spring-rabbit-test")
	// https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-hibernate6
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate6:2.17.0-rc1")

	implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")

	// https://mvnrepository.com/artifact/io.projectreactor/reactor-core
	implementation("io.projectreactor:reactor-core:3.6.3")


}

tasks.withType<Test> {
	useJUnitPlatform()
	jvmArgs("--enable-preview")
	defaultCharacterEncoding = StandardCharsets.UTF_8.name()
}
tasks.withType<JavaExec>().configureEach {
	jvmArgs("--enable-preview")
}

tasks.withType<JavaCompile>().configureEach {
	options.compilerArgs.add("--enable-preview")
	options.encoding = StandardCharsets.UTF_8.name()
}