# My E-commerce Application

## Description

My E-commerce Application is a Java-based web application for managing electronic products, user accounts, shopping carts, and processing payments using PayPal integration.

## Features

- Create, update, and delete user accounts.
- Create, update, and delete electronic products.
- Create, retrieve, and manage shopping carts.
- Place orders and process payments via PayPal.

## Prerequisites

Before running the application, make sure you have the following prerequisites installed:

- Java JDK 11 or higher
- PostgreSQL database
- Apache Maven
- PayPal Sandbox Account (for PayPal integration)

## Installation

1. Clone this repository to your local machine:
git clone https://github.com/GalinaPylyptiy/Online-shop.git
Navigate to the project directory:cd Online-shop
Configure the database connection by editing the application.properties file with your database and paypal credentials;
Running the Application
To run the application, execute the following commands:
mvn clean install
mvn spring-boot:run
The application will start on http://localhost:8080

### Usage
Use API endpoints to interact with the application, e.g., /api/users, /api/products, /api/carts, /api/orders.
Visit /api/paypal/create-payment/{orderId} to initiate a PayPal payment.
Testing
To run unit tests, use the following command: mvn test

#### User Endpoints
Create User: Create a new user account.
Method: POST
Endpoint: /api/users
Request Body: User information (e.g., username, password)
Response: Created user details
Example Request:
{
  "username": "john_doe",
  "password": "secure_password"
}
Example Response:
{
  "id": 1,
  "username": "john_doe"
}
Get User by ID: Retrieve user information by user ID.
Method: GET
Endpoint: /api/users/{id}
Response: User details
Example Response:
{
  "id": 1,
  "username": "john_doe"
}
#### Product Endpoints
Create Product: Create a new electronic product.
Method: POST
Endpoint: /api/products
Request Body: Product information (e.g., name, price, quantity)
Response: Created product details
Example Request:
{
  "name": "Smartphone",
  "price": 399.99,
  "quantity": 50
}
Example Response:
{
  "id": 1,
  "name": "Smartphone",
  "price": 399.99,
  "quantity": 50
}
Get Product by ID: Retrieve product information by product ID.
Method: GET
Endpoint: /api/products/{id}
Response: Product details
Example Response:
{
  "id": 1,
  "name": "Smartphone",
  "price": 399.99,
  "quantity": 50
}
### PayPal Controller Endpoints:
- Create Payment: Create a new PayPal payment for an order.
Method: POST
Endpoint: /api/paypal/create-payment/{orderId}
Request: Order ID
Response: Redirect URL for PayPal approval
Description: This endpoint initiates the PayPal payment process for the specified order. It redirects the user to PayPal for payment approval.
Example Request: 
POST /api/paypal/create-payment/1
Example Response: 
Redirect URL: https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-2VX482914H2979136
- Cancel Payment: Handle PayPal payment cancellation.
Method: GET
Endpoint: /api/paypal/cancel
Response: "Canceled"
Description: This endpoint is called when the user cancels the PayPal payment process. It can be used to display a confirmation message to the user.
Example Response:
"Canceled"
- Success Payment: Handle successful PayPal payment execution.
Method: GET
Endpoint: /api/paypal/success
Request: paymentId (as query parameter)
Response: "Success" or "Failed"
Description: This endpoint is called when the PayPal payment is successfully executed. It verifies the payment and updates the order status accordingly. If successful, it returns "Success"; otherwise, it returns "Failed."
Example Request:
GET /api/paypal/success?paymentId=PAYID-MT5K2WY07D37666GE277600Y&PayerID=1234567890
Example Response: Success
These endpoints allow you to interact with the PayPal payment process and handle payment approval, cancellation, and execution. Make sure to integrate these endpoints into your application to enable PayPal payments for orders.


