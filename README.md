# VideoSNS
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Eclipse](https://img.shields.io/badge/Eclipse-FE7A16.svg?style=for-the-badge&logo=Eclipse&logoColor=white)

## Description
VideoSNS is a backend service designed for a video-based social networking platform. Leveraging Spring Boot, it facilitates video uploads, streaming, user authentication, and real-time interactions among users. Built with modern technologies and a focus on scalability, it aims to provide developers with a robust foundation for building a social video platform.

## Technology Stack
- **Language**: Java 17
- **Framework**: Spring Boot 3.3.0-SNAPSHOT
- **Database**: PostgreSQL
- **Security**: OAuth2, JSON Web Tokens (JWT)
- **Documentation**: SpringDoc OpenAPI
- **Build Tool**: Maven

## Features
- Video upload and streaming
- User registration and authentication via OAuth2 and JWT
- Real-time comments and likes on videos
- Scalable architecture
- API documentation generated with SpringDoc OpenAPI

## Getting Started

### Prerequisites
- Java 17 or later
- Maven
- PostgreSQL database

### Installation
1. Clone the repository:
```
git clone https://github.com/Sangkyun-Kim15/VideoSNS-Backend.git
git clone https://github.com/Sangkyun-Kim15/VideoSNS-Frontend.git
```
2. Unified Build Script:
Create a file named unified-build.bat with the following content.
```
@echo off
echo Starting unified build process...

echo Building Backend...
pushd "Your_root_directory_of_backend_project"
call build-backend.bat
popd

echo Building Frontend...
pushd "Your_root_directory_of_frontend_project"
call build-frontend.bat
popd

echo Unified build process completed.
```

2. Navigate to the project directory:
3. Build and Run the project:
Backend:
```
mvn clean install
mvn spring-boot:run
```

Frontend
```
npm install
npm start
```

## Configuration
Before running the application, configure the necessary environment variables or `application.properties` file with your database credentials and any other required settings.

Additionally, ensure that the `build-frontend.bat` and `build-backend.bat` scripts are correctly configured with the appropriate directory paths for your projects. These scripts are used in the unified build process and must be set up to point to the correct locations of your Backend and Frontend projects, respectively.


## Usage
After starting the application, you can access the endpoints defined in the project through tools like Postman or Swagger UI (if SpringDoc OpenAPI is configured correctly).

## API Reference
For a detailed description of available APIs, run the application and navigate to `/swagger-ui.html` (default path) to access the Swagger UI dashboard.

## Testing
Run the automated tests with Maven:
mvn test

## Deployment
To deploy this project, you may need to adjust `pom.xml` and `application.properties` for your production environment. Follow best practices for deploying Spring Boot applications, such as using an external Tomcat server or deploying as a standalone application.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Credits
- Your Name (and any other contributors)
