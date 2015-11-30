This `spring-resource-demo` project is a Spring Boot application to test the [cas-oauth](https://mcm.max.gov/git/cas-oauth) server with OAuth authentication mechanisms.

## Start & test

Build the project and launch the web app with jetty on [http://localhost:8080](http://localhost:8080):

    cd spring-resource-demo
    mvn clean compile exec:java

To test, you can call a protected url by clicking on the "Protected url by **xxx**" link, which will start the authentication process with the **xxx** provider.  
