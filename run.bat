@echo off
echo Starting the application with Docker Compose...

echo Removing old containers...
docker-compose down

echo Building and starting the services...
docker-compose up --build -d

echo Application started successfully!
