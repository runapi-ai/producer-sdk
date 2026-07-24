plugins {
  `java-library`
  `maven-publish`
}

extra["runapiSlug"] = "producer"

description = "RunAPI Producer Java SDK for Producer workflows."

java {
  withSourcesJar()
  withJavadocJar()
}

dependencies {
  api("ai.runapi:runapi-core:0.2.6")

  testImplementation(platform("org.junit:junit-bom:5.10.3"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      artifactId = "runapi-producer"
      pom {
        name = "RunAPI Producer Java SDK"
        description = "RunAPI Producer Java SDK for Producer workflows."
        url = "https://runapi.ai/models/producer"
        licenses {
          license {
            name = "Apache License, Version 2.0"
            url = "https://www.apache.org/licenses/LICENSE-2.0"
          }
        }
        developers {
          developer {
            id = "runapi"
            name = "RunAPI"
            email = "contact@runapi.ai"
          }
        }
        scm {
          url = "https://github.com/runapi-ai/producer-sdk"
          connection = "scm:git:https://github.com/runapi-ai/producer-sdk.git"
          developerConnection = "scm:git:ssh://git@github.com/runapi-ai/producer-sdk.git"
        }
      }
    }
  }
}
