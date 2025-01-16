# Priya_ranjan_UI_Assignment

# Customer Rewards Application

## Overview
The **Customer Rewards Application** calculates reward points for customers based on their transaction history. Built with **Spring Boot**, the application exposes RESTful APIs for managing customers, transactions, and rewards. 

### Features
- **Customer Management**: Add, update, retrieve, and delete customer records.
- **Transaction Management**: Track and manage customer transactions.
- **Rewards Calculation**: Calculate and retrieve reward points dynamically based on transaction history.

## Architecture
The application is designed using a **layered architecture** to ensure maintainability and scalability:
- **Controller Layer**: Handles HTTP requests and responses.
- **Service Layer**: Contains business logic for processing data.
- **Repository Layer**: Interfaces with the database for CRUD operations.
- **Security Layer**: Provides authentication and authorization.
- **Exception Handling Layer**: Centralized error handling.
- **Validation Layer**: Validates incoming requests.

### Architecture Diagram
```plaintext
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
```
# Database Design

The application uses a **relational database** with the following schema:

## Tables

### Customer
- `id` (Primary Key): Unique identifier.
- `name`: Customer's name.
- `email`: Customer's email.

### CustomerTransaction
- `id` (Primary Key): Unique identifier.
- `customerId` (Foreign Key): Links to Customer.
- `amount`: Transaction amount.
- `date`: Transaction date.

### RewardPoints
- `id` (Primary Key): Unique identifier.
- `customerId` (Foreign Key): Links to Customer.
- `month`: Month for reward points.
- `year`: Year for reward points.
- `points`: Calculated reward points.

---

# Endpoints

## Customer Endpoints
- `POST /api/customers/register`: Register a new customer.
- `GET /api/customers/{customerId}`: Retrieve a customer by ID.
- `PUT /api/customers/{customerId}`: Update customer details.
- `DELETE /api/customers/{customerId}`: Delete a customer.

## Transaction Endpoints
- `GET /api/transactions/{customerId}`: Retrieve transactions for a customer.
- `POST /api/transactions`: Add a new transaction.
- `PUT /api/transactions/{transactionId}`: Update a transaction.
- `DELETE /api/transactions/{transactionId}`: Delete a transaction.

## Reward Endpoints
- `GET /api/rewards/{customerId}`: Calculate and retrieve reward points for a customer.
- `GET /api/rewards/all/{customerId}`: Retrieve all reward points for a customer.

---

# Getting Started

## Prerequisites
- **Java 17** or higher.
- **Maven 3.8+**.
- A relational database (e.g., **MySQL**, **PostgreSQL**).

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/customer-rewards-app.git
   cd customer-rewards-app

## Build the Project

1. Build the project using Maven:
   ```bash
   mvn clean install

## Run the Application

2. Start the application using Maven:
   ```bash
   mvn spring-boot:run

## Access the Application

Once the application is running, you can access it at:
http://localhost:8080


## API Documentation
The application uses Swagger for API documentation. Once the application is running, you can access the documentation at:

```bash
http://localhost:8080/swagger-ui.html
```
## Testing
Unit Tests: Test service and controller logic.
Integration Tests: Verify interactions between components.
API Tests: Use tools like Postman for testing endpoints.

## Security
Basic Authentication is implemented using Spring Security.
Define roles and permissions as needed in the SecurityConfig file.

## License
This project is licensed under the MIT License.

## Contact
For queries or issues, please contact:

Email: priyaranjanebi@gmai.com
GitHub: Priyaranjanch
