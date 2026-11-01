# Laundry Service Booking And Management System

Name: Imani Nzeyimana David
Student ID: 27896
Project: Laundry Service Booking And Management System

## Requirements

- Java 21
- Maven
- PostgreSQL

## Database setup

Create the database:

```sql
CREATE DATABASE laundry_db;
```

The default database settings are in `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/laundry_db
spring.datasource.username=postgres
spring.datasource.password=3002
server.port=8085
```

Update the username and password there if your PostgreSQL setup is different.

## Run the API

```bash
cd backend
mvn spring-boot:run
```

The API runs on:

```text
http://localhost:8085
```

## Main endpoints

```text
POST   /api/customers/save
GET    /api/customers/all
GET    /api/customers/{id}
PUT    /api/customers/update/{id}
DELETE /api/customers/delete/{id}

POST   /api/attendants/save
GET    /api/attendants/all
GET    /api/attendants/{id}
PUT    /api/attendants/update/{id}
DELETE /api/attendants/delete/{id}

POST   /api/service-types/save
GET    /api/service-types/all
GET    /api/service-types/{id}
PUT    /api/service-types/update/{id}
DELETE /api/service-types/delete/{id}

POST   /api/machines/save
GET    /api/machines/all
GET    /api/machines/{id}
PUT    /api/machines/update/{id}
DELETE /api/machines/delete/{id}

POST   /api/laundry-orders/save
GET    /api/laundry-orders/all
GET    /api/laundry-orders/{id}
PUT    /api/laundry-orders/update/{id}
DELETE /api/laundry-orders/delete/{id}
GET    /api/laundry-orders/by-status
GET    /api/laundry-orders/between
GET    /api/laundry-orders/stats/by-status
PATCH  /api/laundry-orders/{id}/status
PATCH  /api/laundry-orders/cancel-day

POST   /api/delivery-notes/save
GET    /api/delivery-notes/{id}
GET    /api/delivery-notes/history
DELETE /api/delivery-notes/delete/{id}

GET    /api/reports/orders-by-customer
GET    /api/reports/attendant-workload
GET    /api/reports/monthly-revenue
```
