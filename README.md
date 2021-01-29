Leaseplan Spring Boot Coding Assignment
---

## The origins
The original structure is completely broken.


## Generation of a new structure

Instead of trying to fix it, I decided to restart completely the project by generating a skeleton from https://start.spring.io/

- https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.4.2.RELEASE&packaging=war&jvmVersion=1.8&groupId=com.assignment.spring&artifactId=brunasti&name=brunasti&description=Leaseplan%20Spring%20Boot%20Coding%20Assignment&packageName=com.assignment.spring.brunasti&dependencies=devtools,lombok,configuration-processor,data-jpa,h2,postgresql,restdocs,web

I replaced all the project with the material generated in the above start.spring.io setting

## Projects merging

I then merged the original source code into the new project and discovered that still some classes were not compiling.
So I removed the non compiling test classes, in detail:
 - com.assignment.spring.ApplicationTests

## First builds

I tried to build the project with the command:
 
- mvn clean install

At this point the project is still not building because of the mvn profile 'dev'

### Profile
I added in the pom.xml the following section:

    <profiles>
        <profile>
            <id>dev</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <war.name>dev</war.name>
            </properties>
        </profile>
    </profiles>

### Java version
And changed the version of Java from 11 to 8

    <properties>
		<java.version>1.8</java.version>
	</properties>

### Multiple main methods
At this point the merge of the two projects (the original and the one generated from start.spring.io) cause a conflict between two different main class.
Of course I opt to remove the original one.

The build is now successful.

## First empty Execution

The application starts correctly.
But it doesn't respond to the expected URL

    http://localhost:8080/weather

## Standard Spring MVC project directories structure

I create under com.assignment.spring.brunasti a standard directories structure for the Spring MVN projects:

    - controller
    - model
    - repository
    - validation



---
# Original README.md

Welcome to the Spring Boot Coding Dojo!

### Introduction

This is a simple application that requests its data from [OpenWeather](https://openweathermap.org/) and stores the result in a database. The current implementation has quite a few problems making it a non-production ready product.

### The task

As the new engineer leading this project, your first task is to make it production-grade, feel free to refactor any piece
necessary to achieve the goal.

### How to deliver the code

Please send an email containing your solution with a link to a public repository.

>**DO NOT create a Pull Request with your solution** 

### Footnote
It's possible to generate the API key going to the [OpenWeather Sign up](https://openweathermap.org/appid) page.
