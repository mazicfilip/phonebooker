# Phone Booker Application

This is a Spring Boot application for managing phone reservations.

## Prerequisites
- Java 22
- Maven
- PostgreSQL

## Setup
1. Clone this repository.
2. Navigate to the project directory.
3. Make sure PostgreSQL is installed and running on your machine.
4. Configure the PostgreSQL credentials (username, password, and URL) in the `application.yml` file located in the `src/main/resources` directory.
5. Build the project using Maven: `mvn clean install`.
6. Run the application: `mvn spring-boot:run`. This will create the necessary tables in the database.
7. Execute the `initialize_data.sql` script located in the `src/main/resources` directory to populate the database.

## Usage
- Once the application is running, you can access the API documentation at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
- Use the provided endpoints to manage phone reservations.

## Important Note
- After running the application, ensure that the database is populated by executing the `initialize_data.sql` script. It is crucial to run this script after starting the application to populate tables with initial data.
