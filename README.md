# Backend System Design Lab

## 1. Overview

**Backend System Design Lab** is a collection of hands-on projects designed to explore backend engineering concepts, system design principles, and real-world business scenarios.

The repository focuses on practical implementation and experimentation with Java, Spring Boot, databases, distributed systems, and software architecture.

Each lab focuses on a specific technical concept or engineering problem, providing opportunities to understand underlying principles, compare different approaches, and evaluate their trade-offs.

## 2. Learning Objectives

The main objectives of this repository are:

- Understand core backend engineering concepts and system design principles.
- Explore concurrency, multithreading, and asynchronous processing.
- Design and implement scalable and reliable backend services.
- Understand database transactions, locking, and data consistency.
- Practice solving real-world business problems such as order processing, payment, and money transfer.
- Explore distributed systems, caching, messaging, and fault tolerance.
- Improve performance optimization, testing, and problem-solving skills.

## 3. Technology Stack

| Category | Technologies |
|---|---|
| Programming Language | Java 21+ |
| Framework | Spring Boot |
| Build Tool | Maven |
| Database | PostgreSQL |
| Cache | Redis |
| Message Broker | RabbitMQ, Apache Kafka |
| Testing | JUnit 5, Mockito, Testcontainers |
| Containerization | Docker, Docker Compose |
| Orchestration | Kubernetes |
| CI/CD | GitHub Actions |
| Monitoring | Prometheus, Grafana, OpenTelemetry |

## 4. Backend Engineering Labs

### 4.1. Core Backend Engineering

| No. | Lab | Topics |
|---|---|---|
| 01 | Concurrency & Multithreading | Virtual Threads, ExecutorService, Thread Pool, Synchronization, Race Condition, Deadlock |
| 02 | Rate Limiting & Throttling | Fixed Window, Sliding Window, Token Bucket, Leaky Bucket, Redis Rate Limiter |
| 03 | API Design & RESTful | REST API, HTTP Methods, Status Codes, Pagination, Filtering, API Versioning |
| 04 | Authentication & Authorization | JWT, OAuth2, OIDC, RBAC, ABAC, Refresh Token, Session Management |
| 05 | Caching Strategies | Cache-Aside, Write-Through, Write-Behind, Cache Invalidation, Cache Stampede |
| 06 | Database Engineering | Indexing, Query Optimization, Execution Plan, Connection Pool, Partitioning |

### 4.2. Database & Transaction Management

| No. | Lab | Topics |
|---|---|---|
| 07 | Database Transactions | ACID, Isolation Levels, Dirty Read, Non-repeatable Read, Phantom Read, MVCC |
| 08 | Database Locking | Optimistic Lock, Pessimistic Lock, Deadlock, Lock Timeout |
| 09 | Database Scaling | Replication, Sharding, Read Replica, Partitioning |
| 10 | Data Consistency | Strong Consistency, Eventual Consistency, Distributed Transaction |

### 4.3. Business Workflow Engineering

| No. | Lab | Topics |
|---|---|---|
| 11 | E-commerce Order Processing | Product Purchase, Inventory Management, Flash Sale, Order State Machine |
| 12 | Payment & Banking System | Deposit, Withdrawal, Money Transfer, Idempotency, Reconciliation |
| 13 | Distributed Systems | Message Queue, Kafka, RabbitMQ, Event-Driven Architecture |
| 14 | Distributed Transactions | Saga Pattern, Transactional Outbox, Compensation |
| 15 | Scheduling & Background Jobs | Cron, Job Scheduler, Retry, Job Deduplication, Distributed Scheduler |

### 4.4. System Design & Software Architecture

| No. | Lab | Topics |
|---|---|---|
| 16 | Software Architecture | Layered Architecture, Clean Architecture, Hexagonal Architecture |
| 17 | Design Patterns | Factory, Strategy, Observer, Adapter, Decorator |
| 18 | Microservices Architecture | Service Discovery, API Gateway, Service Communication |
| 19 | Domain-Driven Design | Bounded Context, Aggregate, Entity, Value Object, Domain Events |
| 20 | Scalability & High Availability | Horizontal Scaling, Load Balancing, Failover, Replication |

### 4.5. Performance & Reliability Engineering

| No. | Lab | Topics |
|---|---|---|
| 21 | Resilience & Fault Tolerance | Retry, Circuit Breaker, Timeout, Bulkhead, Fallback |
| 22 | Performance Engineering | Profiling, Benchmark, Throughput, Latency, Memory Optimization |
| 23 | Observability & Monitoring | Logging, Metrics, Distributed Tracing, OpenTelemetry |
| 24 | Message Processing Reliability | At-most-once, At-least-once, Exactly-once Semantics, Dead Letter Queue |
| 25 | Load Testing | JMeter, k6, Stress Testing, Spike Testing, Capacity Planning |

### 4.6. Infrastructure & DevOps

| No. | Lab | Topics |
|---|---|---|
| 26 | Docker & Containerization | Dockerfile, Multi-stage Build, Docker Compose |
| 27 | Kubernetes & Deployment | Pod, Deployment, Service, ConfigMap, Secret, HPA |
| 28 | CI/CD Pipeline | GitHub Actions, Automated Testing, Build, Deployment |
| 29 | Security Engineering | OWASP Top 10, SQL Injection, XSS, CSRF, Secrets Management |
| 30 | Networking Fundamentals | TCP, UDP, DNS, TLS, HTTP/1.1, HTTP/2, HTTP/3 |

## 5. End-to-End System Design Projects

In addition to individual technical labs, this section focuses on designing and implementing complete backend systems by combining multiple engineering concepts.

| No. | Project | Key Concepts |
|---|---|---|
| 01 | URL Shortener | Unique ID Generation, Redis Cache, Database Indexing, Read-heavy System, Analytics |
| 02 | Notification System | Email, SMS, Push Notification, Message Queue, Retry, DLQ, Delivery Tracking |
| 03 | Ride Booking System | Real-time Matching, Geospatial Search, Concurrent Booking, State Management |
| 04 | Digital Wallet | Wallet Management, Money Transfer, Ledger System, Idempotency, Transaction Consistency |
| 05 | Flash Sale Platform | High Concurrency, Distributed Lock, Inventory Reservation, Queue-based Processing, Rate Limiting |

### 5.1. System Design Documentation

Each system design project should include the following sections:

1. **Functional Requirements:** Define the main features and business requirements.
2. **Non-functional Requirements:** Define availability, scalability, reliability, and performance requirements.
3. **Capacity Estimation:** Estimate traffic, storage, throughput, and resource requirements.
4. **API Design:** Define API endpoints, request/response structures, and communication patterns.
5. **Database Design:** Design database schemas, relationships, indexes, and data access strategies.
6. **High-level Architecture:** Describe system components and their interactions.
7. **Bottlenecks & Scaling:** Identify potential bottlenecks and explore scaling strategies.
8. **Failure Scenarios:** Analyze system failures, recovery mechanisms, and fault tolerance.
9. **Trade-offs:** Compare alternative solutions and explain architectural decisions.

## 6. Learning Approach

Each lab follows a consistent learning and implementation process:

| Step | Activity | Description |
|---|---|---|
| 01 | Problem Definition | Identify the problem and understand its requirements. |
| 02 | Concept Exploration | Study underlying concepts and possible solutions. |
| 03 | System Design | Design architecture and define the implementation approach. |
| 04 | Implementation | Build a working solution. |
| 05 | Testing | Validate functionality, edge cases, and concurrent scenarios. |
| 06 | Performance Analysis | Evaluate performance and compare different approaches. |
| 07 | Lessons Learned | Document findings, trade-offs, and key takeaways. |

### 6.1. Implementation Types

Each lab may follow one or more implementation approaches:

- **Concept Lab:** Explore technical concepts using simple examples or Java standalone applications.
- **Implementation Lab:** Build practical solutions using Java, Spring Boot, databases, and other supporting technologies.
- **System Design Lab:** Focus on architecture, requirements, database design, diagrams, and engineering trade-offs.
- **End-to-End System:** Build a complete application integrating multiple backend concepts.

## 7. Learning Roadmap

The recommended learning order is divided into four phases.

| Phase | Focus | Main Topics |
|---|---|---|
| Phase 1 | Backend Foundation | Java Concurrency, REST API, Authentication, Database, Transactions, Locking |
| Phase 2 | Backend Advanced | Rate Limiting, Caching, Payment, Order Processing, Background Jobs |
| Phase 3 | Distributed Systems & System Design | Message Queue, Distributed Lock, Saga, Outbox, Microservices, Scalability |
| Phase 4 | Production Engineering | Observability, Performance Testing, Security, Docker, Kubernetes, CI/CD |

## 8. Repository Structure

```text
backend-system-design-lab/
│
├── README.md
│
├── labs/
│   ├── lab-01-concurrency/
│   ├── lab-02-rate-limiting/
│   ├── lab-03-api-design/
│   ├── lab-04-authentication/
│   ├── lab-05-caching/
│   ├── lab-06-database-engineering/
│   ├── lab-07-database-transactions/
│   ├── lab-08-database-locking/
│   ├── lab-09-database-scaling/
│   ├── lab-10-data-consistency/
│   ├── lab-11-order-processing/
│   ├── lab-12-payment/
│   ├── lab-13-distributed-systems/
│   ├── lab-14-distributed-transactions/
│   ├── lab-15-background-jobs/
│   ├── lab-16-software-architecture/
│   ├── lab-17-design-patterns/
│   ├── lab-18-microservices/
│   ├── lab-19-domain-driven-design/
│   ├── lab-20-scalability/
│   ├── lab-21-resilience/
│   ├── lab-22-performance/
│   ├── lab-23-observability/
│   ├── lab-24-message-reliability/
│   ├── lab-25-load-testing/
│   ├── lab-26-docker/
│   ├── lab-27-kubernetes/
│   ├── lab-28-cicd/
│   ├── lab-29-security/
│   └── lab-30-networking/
│
├── systems/
│   ├── system-01-url-shortener/
│   ├── system-02-notification/
│   ├── system-03-ride-booking/
│   ├── system-04-digital-wallet/
│   └── system-05-flash-sale/
│
└── docs/
    ├── system-design/
    ├── architecture/
    └── references/
```

## 9. Lab Documentation Convention

Each lab should maintain a consistent documentation structure:

```text
lab-xx-topic/
│
├── README.md
├── docs/
│   ├── architecture.md
│   ├── business-flow.md
│   └── database-design.md
│
├── src/
│   ├── main/
│   └── test/
│
├── docker-compose.yml
└── pom.xml
```

The README of each lab should cover:

- Overview
- Learning Objectives
- Business Requirements
- Technical Concepts
- Architecture
- Implementation
- Test Scenarios
- Performance Testing
- Lessons Learned

## 10. Project Goal

Build a strong foundation in backend engineering and system design through hands-on experimentation, practical implementation, and continuous learning.

The ultimate goal is to understand not only how to build backend applications, but also how to design, scale, optimize, and maintain reliable systems in production environments.

---

*Learning by building. Understanding by experimenting.*