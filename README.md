Leaseplan Spring Boot Coding Assignment
---

## The origins

I loaded the project into my IDE and tried to build the application without success.
I then tried to fix the pom.xml by adding some patch, then changing some characteristics and annotation in the classes without success.

The original structure is completely broken.


## Generation of a new structure

Instead of trying to fix the original application, I decided to restart completely the project by generating a skeleton from https://start.spring.io/

- https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.4.2.RELEASE&packaging=war&jvmVersion=1.8&groupId=com.assignment.spring&artifactId=brunasti&name=brunasti&description=Leaseplan%20Spring%20Boot%20Coding%20Assignment&packageName=com.assignment.spring.brunasti&dependencies=devtools,lombok,configuration-processor,data-jpa,h2,postgresql,restdocs,web

I started the new project with the material generated in the above start.spring.io setting

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
Then I changed the version of Java from 11 to 8

    <properties>
		<java.version>1.8</java.version>
	</properties>

### Multiple main methods
At this point the merge of the two projects (the original and the one generated from start.spring.io) cause a conflict between two different main class.
Of course I opt to remove the original one.

The build is now successful.

## First empty Execution

The application starts correctly, but it doesn't respond to the expected URL

    http://localhost:8080/weather

We still have to link the application to the expected URLs.

I remove completely the com.assignment.spring.Application class.

## Standard Spring MVC project directories structure

I create under com.assignment.spring.brunasti a standard directories structure for the Spring MVN projects:

    - controller
    - model
    - repository
    - validation

I even create an extra directory for the REST API resources

    - rest.resources

I move the original classes into the new directories according to their roles.

I check that all is still building correctly with a 

    - mvn clean install

## New Execution

Executing the application and calling the URL

    - http://localhost:8080/weather

results in a NullPointerException:

    java.lang.NullPointerException: null
	at java.base/java.lang.String.replace(String.java:2143) ~[na:na]
	at com.assignment.spring.brunasti.controller.WeatherController.weather(WeatherController.java:27) ~[classes/:na]
    ....

We can now start investigating on the business and application logic and make it works.

## APIs Documentation

The above mentioned exception is due to:
- unhandled error conditions
- missing documentation

Let's start to fix the exception handling by transforming the signature of the function and it's annotation.

After this first change the response is now:

    There was an unexpected error (type=Bad Request, status=400).
    Required String parameter 'city' is not present
    org.springframework.web.bind.MissingServletRequestParameterException: Required String parameter 'city' is not present

At least is no more a null point error, and the city parameter is said to be required

If we provide the missing city parameter the result is:

    There was an unexpected error (type=Internal Server Error, status=500).
    401 Unauthorized: [{"cod":401, "message": "Invalid API key. Please see http://openweathermap.org/faq#error401 for more info."}]
    org.springframework.web.client.HttpClientErrorException$Unauthorized: 401 Unauthorized: [{"cod":401, "message": "Invalid API key. Please see http://openweathermap.org/faq#error401 for more info."}]

This error is still to be handled but much better

## Registering for the OpenWeather API 

In the Constants class I insert the API key that I obtained registering on the OpenWeather web site:

    public static final String APP_ID = "25dd162561e22decc4a8578857b7c018";

This is not a very good way to store the key because, when it will expire and there will be the need of updating, a new version of the software must be redeployed.

Even the name of the constant is not good because it refers to an ID instead of a key.

I change it to WEATHER_API_KEY

## Adding logging capability

By annotating the desired classes with
    
    @Slf4j

we can start logging what the app is doing, for example showing which URL the application is calling:

    log.info("enter weather url [{}]",url);

This results in

    2021-01-30 16:49:58.162  INFO 13451 --- [nio-8080-exec-1] c.a.s.b.controller.WeatherController     : enter weather url [http://api.openweathermap.org/data/2.5/weather?q=milan&APPID=25dd162561e22decc4a8578857b7c018]


## Test coverage

The result of a first test coverage run provides a very low value:

- 30% of classes touched by tests
- 4% of the code lines covered

This is far from enough, not to say optimal.

But before improving the coverage I need to fix some more structural aspects of the application.

- removing @Autowired from WeatherController
- moving the transformation of the WeatherResponse into a WeatherEntity in an ad hoc class and package


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
