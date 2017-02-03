# Spring Boot version of the Spring PetClinic Sample Application [![Build Status](https://travis-ci.org/spring-petclinic/spring-petclinic-angular1.svg?branch=master)](https://travis-ci.org/spring-petclinic/spring-petclinic-angular1/)

## Understanding the Spring Petclinic application with a few diagrams
<a href="https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application">See the presentation here</a>

## Running petclinic locally
```
	git clone https://github.com/spring-petclinic/spring-petclinic-angular1.git
	cd spring-petclinic
	./mvnw clean install
	cd spring-petclinic-server
	../mvnw spring-boot:run
```

You can then access petclinic here: http://localhost:8080/

<img width="782" alt="spring-petclinic" src="https://cloud.githubusercontent.com/assets/838318/19653851/61c1986a-9a16-11e6-8b94-03fd7f775bb3.png">

## In case you find a bug/suggested improvement for Spring Petclinic
Our issue tracker is available here: https://github.com/spring-petclinic/spring-petclinic-angular1/issues

## Database configuration

In its default configuration, Petclinic uses an in-memory database (HSQLDB) which
gets populated at startup with data. A similar setup is provided for MySql in case a persistent database configuration is needed.
Note that whenever the database type is changed, the data-access.properties file needs to be updated and the mysql-connector-java artifact from the pom.xml needs to be uncommented.

You may start a MySql database with docker:

```
docker run -e MYSQL_ROOT_PASSWORD=petclinic -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:5.7.8
```

## Working with Petclinic in Eclipse/STS

### prerequisites
The following items should be installed in your system:
* Maven 3 (http://www.sonatype.com/books/mvnref-book/reference/installation.html)
* git command line tool (https://help.github.com/articles/set-up-git)
* Eclipse with the m2e plugin (m2e is installed by default when using the STS (http://www.springsource.org/sts) distribution of Eclipse)

Note: when m2e is available, there is an m2 icon in Help -> About dialog.
If m2e is not there, just follow the install process here: http://eclipse.org/m2e/download/


### Steps:

1) In the command line
```
git clone https://github.com/spring-projects/spring-petclinic.git
```
2) Inside Eclipse
```
File -> Import -> Maven -> Existing Maven project
```

### Active the dev Spring profile

In development mode, we recommand you yo use the ```dev``` Spring profile.
Just add the following VM option:
```
-Dspring.profiles.active=dev
```
All static resources changes will be monitored by the embedded LiveReload server of Spring Boot Devtools.
See [application-dev.properties](spring-petclinic-server/src/main/resources/application-dev.properties) for details.

## Client-side Architecture

Compared to the [standard Petclinic based on JSP pages](https://github.com/spring-projects/spring-petclinic), 
this SpringBoot AngularJS Petclinic is splitted in 2 modules - a client module and a server module:
* spring-petclinic-client : static resources (images, fonts, style, angular JS code) packaged as a webjar.
* spring-petclinic-server : Spring MVC REST API and an index.html template


## Looking for something in particular?

| Spring Boot Configuration     | Files |
|-------------------------------|-------|
| The Main Class                | [PetClinicApplication.java](spring-petclinic-server/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java)  |
| Common properties file        | [application.properties](spring-petclinic-server/src/main/resources/application.properties)  |
| Development properties file   | [application-dev.properties](spring-petclinic-server/src/main/resources/application-dev.properties)  |
| Production properties file    | [application-prod.properties](spring-petclinic-server/src/main/resources/application-prod.properties)  |
| Caching: Cache with EhCache   | [CacheConfig.java](spring-petclinic-server/src/main/java/org/springframework/samples/petclinic/config/CacheConfig.java) |
| Homepage                      | Map root context to the index.html template: [WebConfig.java](spring-petclinic-server/src/main/java/org/springframework/samples/petclinic/config/WebConfig.java) |


| Front-end module  | Files |
|-------------------|-------|
| Node and NPM      | [The frontend-maven-plugin plugin downloads/installs Node and NPM locally then runs Bower and Gulp](spring-petclinic-client/pom.xml)  |
| Bower             | [JavaScript libraries are defined by the manifest file bower.json](spring-petclinic-client/bower.json)  |
| Gulp              | [Tasks automated by Gulp: minify CSS and JS, generate CSS from LESS, copy other static resources](spring-petclinic-client/gulpfile.js)  |
| Angular JS        | [app.js, controllers and templates](spring-petclinic-client/src/scripts/)  |


# Contributing

The [issue tracker](https://github.com/spring-petclinic/spring-petclinic-angular1/issues) is the preferred channel for bug reports, features requests and submitting pull requests.

For pull requests, editor preferences are available in the [editor config](https://github.com/spring-projects/spring-petclinic/blob/master/.editorconfig) for easy use in common text editors. Read more and download plugins at <http://editorconfig.org>.

