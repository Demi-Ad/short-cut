# Shortest

Make Short URL and Redirect Analyze

## Getting Started


### 1.IN-MEMORY LOCAL

```
Requrie JAVA11

Dependecy MaxMind GeoLite2 Download
Path: /src/main/resources/static/geo/GeoLite2-City.mmdb
```

Step 1.

```shell
gradle build --exculde-task test
```

Step 2.

```
java -Dspring.profiles.active=dev -jar /build/libs/short-cut-web-0.0.1-SNAPSHOT.jar
```
---
### 2. DOCKER

```shell
/docker $ docker-compose up -d
```



---
## Deploy

[Show](https://st-est.herokuapp.com)

## Dependencies

* [Spring Boot](https://spring.io/) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management
* [GeoLite2](https://www.maxmind.com/) - Used Ip Convert Country
* [UAP-JAVA](https://github.com/ua-parser/uap-java) - User Agent Parser
* [JSOUP](https://jsoup.org/) - HTML Parser

* [BootStrap](https://getbootstrap.kr) - The CSS framework
* [Jquery](https://jquery.com) - javascript easy
* [Typed.js](https://github.com/mattboldt/typed.js) - highlight input text
* [toastr.js](https://github.com/CodeSeven/toastr) - web toast message
* [Chart.js](https://www.chartjs.org) - web Chart library
* [Grid.js](https://gridjs.io/) - web Table library