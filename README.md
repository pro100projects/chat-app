# Chat Application with WebSocket

This project is a simple multi-user chat application that allows users to exchange messages in real time. It demonstrates the use of Java (Spring Boot) for the backend and Angular for the frontend, along with WebSocket for real-time communication.

## Purpose

The goal of this project is to showcase your understanding of client-server architecture, JWT-based authentication, WebSocket communication, and the integration of Java and Angular. The app allows users to register, log in, send/receive chat messages, and maintain chat history.

## Technology Stack

- **Backend**: Java 17, Spring Boot, WebSocket, PostgreSQL
- **Frontend**: Angular
- **Database**: PostgreSQL
- **Authentication**: JWT Tokens
- **Real-Time Messaging**: WebSocket
- **Containerization**: Docker, Docker Compose

## Features

### User Authentication

- JWT-based authentication for user login.
- Simple login page created with Angular.

### Chat Functionality

- Users can send text messages in real-time to all connected clients.
- Messages contain:
    - Text
    - Sender’s name
    - Timestamp

### Chat History

- Messages are saved in a PostgreSQL database.
- On login, the last 50 messages are loaded for the user.

### Message Validation

- Message length is limited to 200 characters.
- Messages are displayed in chronological order (oldest to newest).

### Additional Features (Optional)

- Displays the "User is typing..." status.
- System messages, such as "User X has joined the chat".

## Requirements

Before running the application, make sure you have the following installed:

- **Docker** and **Docker Compose**
- **Node.js** (for Angular frontend)
- **Java 17** (for backend)
- **Gradle** (for building the backend)

## Setup Instructions

### Clone the Repository

To clone the project, use the following command:

```bash
git clone https://github.com/your-username/chat-app.git
cd chat-app
```

### Running the Application Using Docker Compose

To run the project using Docker Compose, follow these steps:

1. Make sure Docker and Docker Compose are installed.
2. Navigate to the project directory and run:

```bash
docker-compose up --build
```

This will build and start the containers for both the backend and frontend.

And you can access frontend by checking `http://localhost` in browser.

### Accessing the Application

- **Frontend**: Open your browser and navigate to `http://localhost:4200` to access the Angular frontend.
- **Backend**: The Spring Boot backend is available at `http://localhost:8080`.

### Stopping the Application

To stop the containers, run:

```bash
docker-compose down
```

This will stop and remove the containers.

## Code Structure

- **Backend (Spring Boot)**:
    - The backend is built using Java 17 and Spring Boot.
    - The chat messages are stored in PostgreSQL, and the server communicates with clients via WebSocket.

- **Frontend (Angular)**:
    - The frontend is built using Angular.
    - It communicates with the backend using WebSocket for real-time messaging.
