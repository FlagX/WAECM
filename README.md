# WAECM
183.223 Web Application Engineering &amp; Content Management (VU 2,0) 2017S Group 6


------------------------------------------------------------

## The Stack


*   PostgreSQL
*   Spring Boot
*   ReactJS

------------------------------------------------------------

## Docker

To build the Docker Image: (move into the directory where the Dockerfile is located, or pass its path as the last argument instead of ".") `docker build -t waecm-bsp1 .`

To create a container: `docker run -p 8080:8080 --name group6 waecm-bsp1 deploy|build`  
`build` - only create Artifacts and copy them to /jar  
`deploy` - build the project and start the application  
It is recommended to mount the host's src directory into the image, so it is not necessary to execute the build command again.


------------------------------------------------------------

## Usage

Once the container has started, you can go to localhost:8080 in your browser.

Authenticate with given usernames and passwords:  
Max Mustermann, Username: max, Password: maxmax, Bank Account Number: 1  
Gabi MusterFrau, Username: gabi, Password: gabigabi, Bank Account Number: 2  
Erika Test, Username: erika, Password: erikaerika, Bank Account Number: 3

The mocked Tan-Code for the transactions is: secret123.

You can also obtain the used resources using the backend's REST API.

### REST API

Account:  
GET http://localhost:8080/userinfo - get an object of the logged in account  

Transaction:  
GET http://localhost:8080/transactionsByAccountId - get all transactions where user is involved  
POST http://localhost:8080/transfer - get id of created transaction  
POST http://localhost:8080/commit - return true if commitment succeeded, false otherwise  

OAuth2 authentication is used.


------------------------------------------------------------

## Details

### Docker Image

Our Dockerfile extends the official ubuntu image.
A PostgreSQL instance, Java and Maven are installed.

The entypoint defined within the Dockerfile executes container_script.sh.
This shell script expects one argument, either deploy or build (see above for explanation).
`mvn clean package` is executed.
In deploy mode, our jar is executed, and our embedded tomcat will listen on port 8080.

### Application

The project basically is a Java project using Maven.

We decided to use Spring Boot as our backend framework.
A REST API is provided and used by the frontend to query data.
Our persistence layer uses Spring-Boot-Data repositories to communicate with our PostgreSQL instance.
Thymeleaf is used to create the resources that are then transfered as a ReactJS app to clients.
The user interface is rendered on the client side.

### Continuous Integration

The project uses Travis CI

GitHub/TravisCI Account for Testing: user: waecm-group6-lva, pw: bkKLBek5CE3ksJlhSwwb

### Continuous Delivery

On the master branch after a successful Travis CI build the application gets deployed to a AWS EC2 instance.

------------------------------------------------------------

## Possible issues

If you are on a Windows machine, make sure you correctly deal with line endings.
The file container_script.sh must have Unix style line endings (LF). If you checkout this file from git, it depends on your git client's configuration whether these line endings are converted to Windows style line endings (CRLF) or not.
