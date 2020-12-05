# spring-flights-management
REST API to manage flight with Swagger-Springboot

Overview
---
management system for Airlines



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
- To run the project:

1. Compile the project with the following command:

mvn clean install

2. The project is a Spring Boot Application, so you can run inside of your ide or from terminal with the following command:

mvn spring-boot:run


Running the Project with IntelliJ IDEA Ultimate Edition for development purpose
---
- To run the project:

1. Clone the repository using the git integration of Intellij From the main menu, choose VCS | Checkout from Version Control | Git and then click Clone.
2. IntelliJ will ask you to create an IntelliJ IDEA project from the sources you have checked out, just click Yes.
3. Choose Import project from external model and select Maven.
4. Modify the needed parameters for your configuration and click Finish.
5. Using the Maven Projects tool window, run the maven build using package command.
6. Click now the Run application button to start Spring Boot application. ( IntelliJ already added Spring boot config )
7. Port http://localhost:8080
> Note: that there is no UI for this application; it only exposes REST endpoints

Swagger
---
To view the generated Swagger UI documentation go to: http://localhost:8080/swagger-ui.html

H2 Database
---
To view the H2 Database http://localhost:8080/h2/

- Driver Class org.h2.Driver
- JDBC Url jdbc:h2:mem:test
- User Name = sa, No password need to required then login into the H2 console.

API Endpoints
---

