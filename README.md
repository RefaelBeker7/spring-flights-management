# spring-flights-management
REST API to manage flight with Swagger.

Overview
---
Management system for Airlines the basics of enabling Swagger definitions on a Spring Boot project with RESTful endpoints. Springfox automatically detects the use of Spring Boot Web MVC and infers from the endpoints defined in the ```MainController``` and generates the corresponding API definitions for Swagger. New Swagger-specific endpoints are injected into the Spring Boot application to allow for browsing the documentation using Swagger UI as well as for viewing the resulting JSON Swagger Definitions file.


Technology
---
The following are the key technologies used in the project:

- Spring Boot
- Java 8
- Apache Tomcat ( http server for the Spring boot app)
- Springfox
- Spring Data JPA
- H2 Database
- Spring boot framework
- Swagger UI
- Build tool – maven


System Requirements
----
1. Option to add an airline to the system (name, initial budget, home base location).
2. Option to retrieve a list of airlines and their current balance.
3. An option to add an aircraft to a specific airline (each aircraft has price and max distance
[km]).
4. An option to sell an aircraft. Upon selling an aircraft the sell price is determined as following
→ original price * (1 - num of months aircraft in use*0.02).
5. Option to Add a destination (name and location). An airline home base is considered a destination as
well.
6. An option to list the distance of a given airline from all the destinations in the system.
7. An option to list the available destinations for a given airline (according to the airline,
airplanes and their max distance).
8. Provide an option to an airline to buy an aircraft from another airline.
9. Locations should be represented by altitude and longitude. In order to calculate distance,
you should use the Haversine formula (it is highly recommended to use an existing library
which implement the formula).

Running the Project
---

1. Compile the project with the following command:
```
mvn clean install
```
2. The project is a Spring Boot Application, so you can run inside of your ide or from terminal with the following command:
```
mvn spring-boot:run
```

Running the Project with IntelliJ IDEA Ultimate Edition for development purpose
---

1. Clone the repository using the git integration of Intellij From the main menu, choose **VCS | Checkout from Version Control | Git** and then click **Clone**.
2. IntelliJ will ask you to create an IntelliJ IDEA project from the sources you have checked out, just click **Yes**.
3. Choose **Import project from external model** and select **Maven**.
4. Modify the needed parameters for your configuration and click **Finish**.
5. Using the **Maven Projects** tool window, run the maven build using **package** command.
6. Click now the **Run application** button to start Spring Boot application. ( IntelliJ already added Spring boot config )
7. LISTENING PORT **8080**
> Note: that there is no UI for this application; it only exposes REST endpoints

Swagger
---
To view the generated Swagger UI documentation go to: http://localhost:8080/swagger-ui.html

![alt text](https://github.com/RefaelBeker7/spring-flights-management/blob/master/Swagger.png)


H2 Database
---
If you prefer you can use any database client, else, you can access from the following URL:
1. Go to: http://localhost:8080/contacts/h2-console
2. Setting the following parameters:
```
 Driver Class :  org.h2.Driver
 JDBC URL     :  jdbc:h2:file:./data/fileDb
 User Name    :  sa
 Password     : 
```
API Endpoints
---
![alt text](https://github.com/RefaelBeker7/spring-flights-management/blob/master/controller.png)
![alt text](https://github.com/RefaelBeker7/spring-flights-management/blob/master/models.png)
