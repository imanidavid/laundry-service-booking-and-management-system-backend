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
GET    /api/customers/by-last-name?lastName=...
GET    /api/customers/of-attendant/{attendantId}
GET    /api/customers/{id}
PUT    /api/customers/update/{id}
DELETE /api/customers/delete/{id}

POST   /api/attendants/save
GET    /api/attendants/all
GET    /api/attendants/by-service-type?name=...
GET    /api/attendants/{id}
PUT    /api/attendants/update/{id}
PUT    /api/attendants/{attendantId}/service-types/{serviceTypeId}
PATCH  /api/attendants/{attendantId}/machine?machineNumber=...
DELETE /api/attendants/delete/{id}

POST   /api/service-types/save
GET    /api/service-types/all
GET    /api/service-types/unused
GET    /api/service-types/{id}
PUT    /api/service-types/update/{id}
DELETE /api/service-types/delete/{id}

POST   /api/machines/save
GET    /api/machines/all
GET    /api/machines/free
GET    /api/machines/{id}
PUT    /api/machines/update/{id}
DELETE /api/machines/delete/{id}

POST   /api/laundry-orders/save
GET    /api/laundry-orders/all
GET    /api/laundry-orders/{id}
PUT    /api/laundry-orders/update/{id}
DELETE /api/laundry-orders/delete/{id}
GET    /api/laundry-orders/by-status?status=...
GET    /api/laundry-orders/between?start=...&end=...
GET    /api/laundry-orders/stats/by-status
PATCH  /api/laundry-orders/{id}/status?status=...
PATCH  /api/laundry-orders/cancel-day?attendantId=...&date=...

POST   /api/delivery-notes/save?laundryOrderId=...
GET    /api/delivery-notes/customer/{phone}
DELETE /api/delivery-notes/delete/{id}
```

## Postman collection

Import `laundry-service-booking-and-management-system-midterm.postman_collection.json` into Postman. The requests are named A2, B1, B2, B3, C1, C2, C3, C4, D1, D2, D3, D4, E1, E2, E3, F1 and F2.
