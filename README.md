**Deployment Instructions**

Follow the steps below to set up and run the application using Docker:

**1\. Prerequisites**

- Install **Docker** on your system.
- Make sure Git is installed

**2\. Clone the Repository**

git clone https://github.com/LasithaRanathunga/course-management-platform.git

cd <repository-folder>

**3\. Build the Docker Images**

Run the following command to build the backend and frontend images:

docker compose build

**4\. Start the Application**

Run the containers with:

docker compose up

This will:

- Start the **backend service** (Spring Boot application).
- Start the **frontend service** (React application served via Nginx).
- Start the **database service** (H2/MySQL depending on configuration).

**5\. Access the Application**

- Frontend: [http://localhost:3000](http://localhost:3000/)
- Backend API: [http://localhost:8080](http://localhost:8080/)
- H2 Database Console (if enabled): [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Use jdbc:h2:file:./data/course_mgmt as the **JDBC URL**

**6\. Stopping the Application**

To stop all running containers:

docker compose down
