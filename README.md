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
5. Add a destination (name and location). An airline home base is considered a destination as
well.
6. An option to list the distance of a given airline from all the destinations in the system.
7. An option to list the available destinations for a given airline (according to the airline,
airplanes and their max distance).
8. Provide an option to an airline to buy an aircraft from another airline.
9. Locations should be represented by altitude and longitude. In order to calculate distance,
you should use the Haversine formula (it is highly recommended to use an existing library
which implement the formula).

Running the Project with IntelliJ IDEA Ultimate Edition for development purpose
---
- To run the project:

Clone the repository using the git integration of Intellij From the main menu, choose VCS | Checkout from Version Control | Git and then click Clone
IntelliJ will ask you to create an IntelliJ IDEA project from the sources you have checked out, just click Yes
Choose Import project from external model and select Maven
Modify the needed parameters for your configuration and click Finish
Using the Maven Projects tool window, run the maven build using package command
Click now the Run application button to start Spring Boot application ( IntelliJ already added Spring boot config )
Check http://localhost:8080
To view the generated Swagger UI documentation go to: http://localhost:8080/swagger-ui.html

