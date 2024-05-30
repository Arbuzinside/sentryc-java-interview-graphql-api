# Sentryc Java Interview GraphQL API

## Overview

This project is a Java-based application using Spring Boot, PostgreSQL, and GraphQL to manage and query aggregated seller information.

### Prerequisites

- Java Development Kit (JDK) 21
- Gradle 7.5 or later
- PostgreSQL 13 or later
- Docker (for running PostgreSQL in a container)

## Getting Started

1. **Clone the repository:**
    ```sh
    git clone https://github.com/Arbuzinside/sentryc-java-interview-graphql-api.git
    cd sentryc-java-interview-graphql-api
    ```

2. **Run PostgreSQL using Docker Compose:**
    ```sh
    docker-compose up
    ```
   This will start the PostgreSQL container with the initial schema and random test data setup as defined in the `init-db.sql` and `populate-db.sh` scripts.


3. **Run the application:**
    ```sh
    ./gradlew bootRun
    ```

## Usage

Access the GraphQL endpoint at `https://localhost:8080/graphiql` by using basic auth

### Default Credentials

- **Username**: `user`
- **Password**: `password`

### Sample GraphQL Query

```graphql
{
  sellers(filter: {}, page: {page: 0, size: 10}, sortBy: NAME_ASC) {
    meta {
      currentPage
      pageSize
      totalPages
      totalElements
    }
    data {
      sellerName
      externalId
      producerSellerStates {
        producerId
        producerName
        sellerState
        sellerId
      }
      marketplaceId
    }
  }
}
```

## Future work

### Enhance Error Handling
1. Adding custom exception handlers to provide meaningful error messages to the client. 
2. Implementing validation logic to ensure that all inputs meet the required criteria. 
3. Ensuring that all potential failure points in the application, such as database connection issues or invalid query parameters, are properly managed and reported.

### Improve Test Coverage
1. E2E API tests
2. More unit tests

### Externalize secrets and other configs 
Now they are hardcoded in the `application.yml` for the sake of simplicity

### Add proper Authentication and Authorization
Now it's just basic auth

### Test and optimize performance
1. Analyzing and optimizing slow-running queries using tools like EXPLAIN and query profiling.
2. Adding indexes to frequently queried columns to speed up data retrieval.
3. Caching frequently accessed data to reduce the load on the database.

