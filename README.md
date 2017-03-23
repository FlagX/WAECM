# WAECM
183.223 Web Application Engineering &amp; Content Management (VU 2,0) 2017S Group 6


------------------------------------------------------------

## The Stack


*   MongoDB
*   Spring Boot
*   ReactJS

------------------------------------------------------------

## Docker

To build the Docker Image: (move into the directory where the Dockerfile is located, or pass its path as the last argument instead of ".") `docker build -t waecm-bsp1 .`

To create a container: `docker run waecm-bsp1 -p 8080:8080 --name group6 waecm-bsp1 deploy|build` 
`build` - only create Artifacts and copy them to /jar
`deploy` - build the project and start the application
It is recommended to mount the host's src directory into the image, so it is not necessary to execute the build command again.


------------------------------------------------------------

## Usage

Once the container has started, you can go to localhost:8080 in your browser.
Authenticate with "user" and "password".
You can also obtain the used resources using the backend's REST API.

### REST API

GET http://localhost:8080/counter - get the current counter value
POST http://localhost:8080/counter - increment the counter by one

Basic authentication is used ("username" "password")


------------------------------------------------------------

## Details

### Docker Image

Our Dockerfile extends the official ubuntu image.
A MongoDB instance, Java and Maven are installed.

The entypoint defined within the Dockerfile executes container_script.sh.
This shell script expects one argument, either deploy or build (see above for explanation).
`mvn clean package` is executed.
In deploy mode, our jar is executed, and our embedded tomcat will listen on port 8080.

### Application

The project basically is a Java project using Maven.

We decided to use Spring Boot as our backend framework.
A REST API is provided and used by the frontend to query data.
Our persistence layer uses Spring-Boot-Data repositories to communicate with our MongoDB instance.
Thymeleaf is used to create the resources that are then transfered as a ReactJS app to clients.
The user interface is rendered on the client side.

------------------------------------------------------------

## Possible issues

If you are on a Windows machine, make sure you correctly deal with line endings.
The file container_script.sh must have Unix style line endings (LF). If you checkout this file from git, it depends on your git client's configuration whether these line endings are converted to Windows style line endings (CRLF) or not.