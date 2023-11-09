
# Booking Application based on Microservices
- [Svenska](README_SE.md)
## Overview
The Booking Application showcases my expertise in Java development, the Spring framework, and microservices architecture. It enables businesses to offer their services, allowing customers to effortlessly book these services. The application includes features for service providers to upload portfolio images and for users to communicate through messages.

## Key Features
- **User Registration and Authentication:** Secure user registration and authentication to protect user data and regulate access. The application utilizes JWT (JSON Web Tokens) for authentication.

- **Service and Portfolio Image Publication:** Service providers can easily publish their services and enhance their portfolios by adding images for each service.

- **Booking:** Users can smoothly browse available services, search for specific services, and book their favorites.

- **Schedule Overview:** Users can easily check available times for specific services on any day. They can also filter to see available times for different employees.

- **Message Handling with RabbitMQ:** The application uses RabbitMQ for efficient message handling between microservices.

- **Event-Driven Architecture:** The application employs RabbitMQ for event-driven architecture, enabling efficient data handling between services.

- **Messaging Functionality:** Users can send text and image messages to each other.

- **Unit Testing with JUnit and Mockito:** Each microservice undergoes thorough testing with JUnit and Mockito to ensure proper functionality and to detect and address potential issues.

## Technologies Used
- **Java:** The primary programming language.

- **Spring Framework:** The project leverages the Spring ecosystem, including Spring Cloud and Spring Data JPA, to build scalable microservices.

- **Microservices Architecture:** The application effectively utilizes microservices architecture to break down complex features into manageable components and ensure scalability.

- **JUnit and Mockito:** Unit testing is conducted using JUnit and Mockito to ensure the proper functioning of microservices and to detect and address potential issues.

- **PostgreSQL:** Data management, including image handling, is achieved using PostgreSQL as the database management system.

- **RabbitMQ:** Message services are facilitated by RabbitMQ, supporting real-time communication between microservices and enabling event-driven data handling.

- **Resilience4J:** The application uses Resilience4J as a circuit breaker to ensure robustness and error handling in microservices communication.

## Microservices

### Api Gateway
Responsible for routing and guidance.

### Security-Service
Handles authentication and authorization for incoming requests.
Responds with a JWT upon login for subsequent authentication and authorization.
Performs JWT verification.
Uses SHA-256 to encrypt passwords.
Unit testing with JUnit and Mockito: Authentication and authorization are thoroughly tested with JUnit and Mockito to ensure security and reliability.

### User-Service
Handles all user data, including contact information, names, and passwords.
Uses Event-Driven Architecture with RabbitMQ to notify other services of events such as user deletion, thereby removing all associated data from other databases used by other services.

### Booking-Service
Returns available times for services and generates Timeslot objects for employees available on a specific day to perform the service.
Returns companies/services based on name and popularity in their service category.
Sends messages with information about bookings through RabbitMQ.

### Image-Service
Compresses images and stores them in the PostgreSQL database.
When retrieving images from the database, they are decompressed.
Saves images for messages and portfolio pictures and responds with unique image IDs to other services for later retrieval.

### Messaging-Service
Enables users to send messages, including text and images, to each other.
Handles system messages about upcoming bookings.
