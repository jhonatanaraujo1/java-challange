# Order Management API

An API to manage orders, allowing you to create, list, retrieve, update, and delete orders.

## Requirements

- Java 17+
- Maven
- PostgreSQL (or another configured database)

## Database Setup


### Docker
Install docker and execute the command in root path and database will be created.
```commandline
docker-compose up 
```
Configure the application.properties file:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/order_management
spring.datasource.username=user
spring.datasource.password=12345
```

Running the Project
Build and run the project:

```commandline
mvn clean install
```

when the project run, all the entities will be created.
```commandline
mvn spring-boot:run

Access the application at: http://localhost:8080
```
## Endpoints

### POST /orders
Create a new order.

Request Example:
```JSON
{
"quantity": 10,
"skuCode": "ABC123",
"itemName": "Sample Item",
"userId": 1,
"userName": "John Doe",
"userEmail": "johndoe@example.com"
}
```

Response Example (201 Created):

```JSON
{
  "id": 1,
  "item": {
    "id": 1,
    "name": "Sample Item",
    "skuCode": "ABC123"
  },
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "johndoe@example.com"
  },
  "quantity": 10,
  "status": "PENDING",
  "createdAt": "2024-12-09T14:00:00",
  "stockMovements": []
}
```


### GET /orders/{id}
Retrieve an order by its ID.

Request Example:
GET /orders/1
Response Example (200 OK):
```JSON
{
  "id": 1,
  "item": {
    "id": 1,
    "name": "Sample Item",
    "skuCode": "ABC123"
  },
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "johndoe@example.com"
  },
  "quantity": 10,
  "status": "PENDING",
  "createdAt": "2024-12-09T14:00:00",
  "stockMovements": []
}

```
Response Example (404 Not Found):
```JSON
{}
```
### PUT /orders
Update an existing order.

Request Example:

```JSON
{
"id": 1,
"quantity": 15,
"skuCode": "ABC123",
"itemName": "Updated Item",
"userId": 1,
"userName": "John Doe",
"userEmail": "johndoe@example.com"
}
```
Response Example (200 OK):

```JSON
{
"id": 1,
"item": {
"id": 1,
"name": "Updated Item",
"skuCode": "ABC123"
},
"user": {
"id": 1,
"name": "John Doe",
"email": "johndoe@example.com"
},
"quantity": 15,
"status": "PENDING",
"createdAt": "2024-12-09T14:00:00",
"stockMovements": []
}
```

### GET /orders
List all orders.

Response Example (200 OK):
```JSON
[
{
"id": 1,
"item": {
"id": 1,
"name": "Sample Item",
"skuCode": "ABC123"
},
"user": {
"id": 1,
"name": "John Doe",
"email": "johndoe@example.com"
},
"quantity": 10,
"status": "PENDING",
"createdAt": "2024-12-09T14:00:00",
"stockMovements": []
},
{
"id": 2,
"item": {
"id": 2,
"name": "Another Item",
"skuCode": "DEF456"
},
"user": {
"id": 2,
"name": "Jane Smith",
"email": "janesmith@example.com"
},
"quantity": 5,
"status": "PENDING",
"createdAt": "2024-12-09T15:00:00",
"stockMovements": []
}
]
```

Response Example (204 No Content):
```JSON
[]
```
### DELETE /orders/{id}
Delete an order by its ID.

Request Example:

Response Example (204 No Content):
```JSON
{}
```

### Testing
Run unit tests:
```commandline
mvn test
```
# JAVA CHALLENGE

This is a simple exercise, a simple order manager. You should develop an API where users can create and manage orders. Items can be ordered and orders are automatically fulfilled as soon as the item stock allows it.

Specification

The system should be able to provide the following features:

- create, read, update and delete and list all entities;

- when an order is created, it should try to satisfy it with the current stock.;

- when a stock movement is created, the system should try to attribute it to an order that isn't complete;

- when an order is complete, send a notification by email to the user that created it;

- trace the list of stock movements that were used to complete the order, and vice-versa;

- show current completion of each order;

- Write a log file with: orders completed, stock movements, email sent and errors.


Entities
```SQL
- Item

            > name

- StockMovement

            > creationDate

            > Item

            > quantity

- Order

            > creationDate

            > Item

            > quantity

            > User (who created the order)

- User

            > name

            > email
```