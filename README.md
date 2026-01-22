# XYZ Movie Ticket Booking Platform

This is a **Spring Boot** application implementing a simple movie ticket booking platform that supports both **B2B (theatre partners)** and **B2C (end customers)** workflows.

The app uses an **H2 in-memory database** for data persistence and demonstrates key features like **browsing movies, onboarding theatres, and booking tickets** with discount rules.

---

## Key Features

### 1. Theatre Management (B2B)
- Onboard new theatres.
- Add shows for the theatres.

### 2. Browse Movies & Shows (B2C)
- Browse movies across **cities**.
- Optional filters: **movieId, language, genre, date**.
- Return all shows matching the filters.

### 3. Booking Tickets (B2C)
- Book tickets for a specific **showId**.
- Prevents **double booking** using in-memory seat locks.
- Applies **50% discount on the 3rd ticket** automatically.
- Stores booking info in **H2 DB**.

---

## Technologies Used

- **Java 17** / Spring Boot 3  
- **Spring Data JPA**  
- **H2 In-Memory Database**  
- **Lombok** (`@Data`, `@Builder`)  
- **ConcurrentHashMap** for seat locking  
- REST APIs using Spring Web

---

## Database Setup (H2)

**application.properties:**

```properties
spring.datasource.url=jdbc:h2:mem:movie-db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

spring.sql.init.mode=always

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Access H2 Console:
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:movie-db

Username: sa

Password: (leave empty)

## API Endpoints

### 1️⃣ Theatre APIs

#### Onboard Theatre

**POST /api/theatres**

**Request:**
```json
{
  "theatreId": "T1",
  "name": "PVR Bangalore Central",
  "city": "Bangalore"
}
```

**Response:**
```json
{
  "id": 1,
  "theatreId": "T1",
  "name": "PVR Bangalore Central",
  "city": "Bangalore"
}
```


#### Add Show to Theatre

**POST /api/theatres/shows**

**Request:**
```json
{
  "showId": "S1",
  "theatreId": "T1",
  "movieId": "M1",
  "language": "English",
  "genre": "Action",
  "showDate": "2026-01-21",
  "showTime": "14:00",
  "ticketPrice": 200
}
```

**Response:**
```
200 OK  
```

### 2️⃣ Browse API

**GET /api/browse/shows**
* city (mandatory)
* movieId (optional)
* language (optional)
* genre (optional)
* date (optional, format YYYY-MM-DD)

**Request:**
```json
GET /api/browse/shows?city=Bangalore&genre=Action&date=2026-01-21
```

**Response:**
```
[
  {
    "id": 1,
    "showId": "S1",
    "theatreId": "T1",
    "movieId": "M1",
    "language": "English",
    "genre": "Action",
    "showDate": "2026-01-21",
    "showTime": "14:00",
    "ticketPrice": 200.0
  },
  {
    "id": 2,
    "showId": "S2",
    "theatreId": "T1",
    "movieId": "M1",
    "language": "English",
    "genre": "Action",
    "showDate": "2026-01-21",
    "showTime": "19:00",
    "ticketPrice": 220.0
  }
]

```

### 3️⃣ Booking API

**POST /api/booking/book**
**Query Parameter**: showId (string, mandatory)

**Request:**
```json
["A1","A2","A3"]
```

**Response:**
```
{
  "id": 1,
  "bookingId": "7d3f5c3f-xxxx-xxxx-xxxx-abcdef123456",
  "showId": "S1",
  "theatreId": "T1",
  "movieId": "M1",
  "city": "Bangalore",
  "seatNumbers": ["A1","A2","A3"],
  "totalPrice": 500.0,
  "status": "CONFIRMED",
  "bookingTime": "2026-01-21T10:30:00"
}

```


**Notes:**

The **3rd seat (A3)** automatically gets **50% discount**.

Booking fails if **any seat is already locked.**

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

## High-Level Design (HLD)

The platform follows a layered, service-oriented architecture designed to support both B2B (theatre partners) and B2C (end customers).

The system is structured into the following layers:

- API Layer  
  Spring Boot REST controllers handle requests for theatre onboarding, browsing shows, and booking tickets.

- Service Layer  
  Contains core business logic such as browsing filters, booking validation, seat locking, and discount application.

- Persistence Layer  
  Uses Spring Data JPA with an in-memory H2 database for development and evaluation.  
  The design allows easy migration to a production-grade relational database.

- Database  
  Stores theatres, shows, and bookings with clear relationships and transactional boundaries.

The application is stateless, allowing horizontal scaling behind a load balancer.  
Future extensions include payment gateway integration, caching, and asynchronous workflows.

HLD Diagram Reference:  
docs/hdl/hld.png


## Low-Level Design (LLD)

### Entity Relationship Design (ERD)

The low-level design focuses on domain-driven modeling with clear entity boundaries.

Core entities:

- Theatre  
  Represents a theatre partner onboarded onto the platform.

- Show  
  Represents a movie show running in a theatre on a specific date, including language and genre.

- Booking  
  Represents a ticket booking made by an end customer for a specific show.

Relationships:

- One Theatre can have multiple Shows (1:N)
- One Show can have multiple Bookings (1:N)

Identifier strategy:

- Database-generated IDs are used internally for persistence.
- Business identifiers (theatreId, showId, bookingId) are exposed through APIs to ensure stability and future integrations.



LLD / ERD Diagram Reference:  
docs/lld/erd.png
docs/lld/lld.png

## Design Notes

- The design supports future enhancements such as seat-level persistence, payment workflows, and booking cancellations.
- H2 in-memory database is used for simplicity and fast iteration.
- The HLD and LLD are aligned with the implemented Spring Boot services.




