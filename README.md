# Priya_ranjan_UI_Assignment

Overview
The Customer Rewards Application calculates reward points for customers based on their transaction history. This Spring Boot application provides a RESTful API for managing customers, transactions, and rewards. It supports customer registration, transaction tracking, and dynamic reward point calculation based on the amount of each transaction.

Features
Register, update, and delete customers.
Add, update, and delete customer transactions.
Calculate and retrieve customer reward points.
Exposes a comprehensive set of RESTful endpoints for managing customers, transactions, and rewards.
Architecture
The application follows a layered architecture for better maintainability, scalability, and separation of concerns. The key layers include:

Controller Layer: Exposes REST endpoints to handle HTTP requests.
Service Layer: Contains business logic for customer, transaction, and reward point operations.
Repository Layer: Interfaces with the database to manage customer, transaction, and reward data.
Security Layer: Manages user authentication and authorization.
Exception Handling Layer: Centralizes the handling of application exceptions.
Validation Layer: Validates incoming requests.
Database Design
The application uses a relational database with the following entities:

Customer: Stores customer information.

id: Unique identifier (Primary Key).
name: Customer's name.
email: Customer's email.
CustomerTransaction: Stores transaction information.

id: Unique identifier (Primary Key).
customerId: Foreign Key linking to the Customer.
amount: Amount of the transaction.
date: Date of the transaction.
RewardPoints: Stores calculated reward points for each customer per month.

id: Unique identifier (Primary Key).
customerId: Foreign Key linking to the Customer.
month: Month when points were calculated.
year: Year of the points calculation.
points: The reward points earned.
Endpoints
CustomerController
POST /api/customers/register: Register a new customer.
GET /api/customers/{customerId}: Retrieve a customer by ID.
PUT /api/customers/{customerId}: Update customer details.
DELETE /api/customers/{customerId}: Delete a customer by ID.
CustomerTransactionController
GET /api/transactions/{customerId}: Retrieve transactions for a customer.
POST /api/transactions: Add a new transaction.
PUT /api/transactions/{transactionId}: Update a transaction.
DELETE /api/transactions/{transactionId}: Delete a transaction by ID.
RewardController
GET /api/rewards/{customerId}: Calculate and retrieve reward points for a customer.
GET /api/rewards/all/{customerId}: Retrieve all reward points for a customer.
Architecture Diagram
lua
Copy code
                   +--------------------+
                   |   REST Controller  |
                   +--------------------+
                            |
                            v
                   +--------------------+
                   |   Service Layer    |
                   +--------------------+
                            |
                            v
                   +--------------------+
                   |   Repository Layer |
                   +--------------------+
                            |
                            v
                   +--------------------+
                   |      Database      |
                   +--------------------+

                   +---------------------+
                   | Security Layer      |
                   +---------------------+
                            |
                            v
                   +---------------------+
                   | Exception Handling  |
                   +---------------------+
                            |
                            v
                   +---------------------+
                   | Validation Layer    |
                   +---------------------+
Testing
Unit Tests: Ensures the correctness of each individual component (service and controller logic).
Integration Tests: Validates the interaction between different components.
API Tests: Validates the behavior of RESTful API endpoints using tools like Postman.
Documentation
API documentation is automatically generated using Swagger and can be accessed at:

bash
Copy code
http://localhost:8080/swagger-ui.html
Running the Application
To run the application locally, follow these steps:

Clone the repository:

bash
Copy code
git clone https://github.com/yourusername/customer-rewards-app.git
cd customer-rewards-app
Build the project using Maven:

bash
Copy code
mvn clean install
Run the application:

bash
Copy code
mvn spring-boot:run
The application will be accessible at http://localhost:8080.

Security
The application uses Spring Security to provide basic authentication. Access to specific endpoints may require proper authorization.

Conclusion
This Customer Rewards Application is designed to manage customer data, transactions, and reward points efficiently. It provides a RESTful API that can be easily integrated with other systems and supports basic user authentication.

Feel free to adjust the information such as the GitHub repository URL or any specific details about your setup if needed!
