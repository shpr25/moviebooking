# 🎬 Online Movie Ticket Booking Platform (B2B + B2C)

## Overview

This project is a **minimal backend implementation** of an online movie ticket booking platform that supports:

- **B2B**: Theatre partners onboarding their theatres
- **B2C**: End customers browsing shows and booking tickets

The objective of this assignment is to demonstrate **system design thinking, clean API design, transactional handling, and extensibility**, rather than building a complete production-ready system.

---

## Key Goals Covered

### Theatre Partner Enablement (B2B)
- Enable theatre partners to onboard their theatres on the platform
- Allow theatres to expose their shows digitally to customers

### Customer Enablement (B2C)
- Browse movies and shows by city
- Book tickets in advance by selecting seats
- Apply simple offers during booking

---

## Scope of Implementation

### Implemented (Code)
- Theatre onboarding API
- Browse shows API (mocked)
- Ticket booking API
- Seat locking to prevent double booking
- Discount logic using Strategy Pattern
- In-memory data storage

### Not Implemented (Covered in Design / Discussion)
- UI / Frontend
- Authentication & Authorization
- Payment gateway integration
- Database (JPA / SQL / NoSQL)
- Caching (Redis)
- Messaging (Kafka)
- CI/CD & deployment

> These components are intentionally excluded to keep the solution focused and aligned with the assignment requirements.

---

## Technology Stack

- **Language**: Java 17
- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Dependencies**:
  - Spring Web
  - Lombok
- **Storage**: In-memory collections

---

## Project Structure

com.xyz.moviebooking
├── controller
│ ├── TheatreController.java
│ ├── ShowController.java
│ └── BookingController.java
├── service
│ ├── TheatreService.java
│ └── BookingService.java
├── repository
│ ├── TheatreRepository.java
│ └── SeatRepository.java
├── model
│ ├── Theatre.java
│ ├── Show.java
│ ├── Booking.java
│ └── Seat.java
└── discount
├── DiscountStrategy.java
└── ThirdTicketDiscount.java


---

## API Endpoints

### 1. Theatre Onboarding (B2B)

**POST** `/theatres`

**Request**
```json
{
  "name": "PVR Cinemas",
  "city": "Bangalore"
}
```

**Response**
```json
{
  "id": "uuid",
  "name": "PVR Cinemas",
  "city": "Bangalore"
}
```

### 2. Browse Shows (B2C – Read Scenario)
**GET** `/movies/{movieId}/shows?city=Bangalore`
**Response**
```json
[
  {
    "id": "S1",
    "movieId": "M1",
    "city": "Bangalore",
    "showTime": "14:00"
  }
]
```

### 3. Book Tickets (B2C – Write Scenario)
**POST** `/bookings`

**Request**
```json
{
  "showId": "S1",
  "showTime": "14:00",
  "seatIds": ["A1", "A2", "A3"]
}
```

**Response**
```json
{
  "bookingId": "uuid",
  "showId": "S1",
  "seatIds": ["A1", "A2", "A3"],
  "amount": 500
}
```

## Seat Locking & Concurrency Handling

- Seat locking is implemented using `ConcurrentHashMap`
- Each seat is locked before confirming a booking
- Prevents double booking in concurrent requests

**In production, this can be replaced with:**
- Redis with TTL
- Database row-level locking

---

## Discount Logic

Discounts are implemented using the **Strategy Pattern** for extensibility.

### Implemented Rule
- **50% discount on the third ticket**

### This design allows:
- Adding new discount rules without modifying booking logic
- Supporting city, theatre, or time-based offers

---

## Transactional Considerations (Design)

- Booking is treated as a single logical transaction
- If any seat cannot be locked, the booking fails
- Payment flow is not implemented but designed to support a **Saga-based approach** in the future

---

## Scalability & Availability (Design)

- Stateless services for horizontal scaling
- City-based data partitioning
- Designed to achieve **99.99% availability** using:
  - Load balancers
  - Multi-AZ deployments
  - Read replicas

---

## Security Considerations (Design)

- Input validation
- API rate limiting via gateway (future)
- OAuth2 / JWT for authentication (future)
- OWASP Top 10 considerations discussed at design level

---

## Assumptions & Limitations

- Single service for simplicity
- No data persistence across restarts
- No real payment processing
- No frontend / UI

---

## How to Run

```bash
mvn spring-boot:run
```

Application runs on:
```
http://localhost:8080
```


