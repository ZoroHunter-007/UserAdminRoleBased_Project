# 🔐 Role-Based User Management System

A production-level Spring Boot REST API with Role-Based Access Control (RBAC) using Spring Security. Built with clean architecture, custom exception handling, and structured logging.

---

## 🛠️ Tech Stack

| Technology | Usage |
|---|---|
| Java 21 | Core Language |
| Spring Boot 3.x | Framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | Database ORM |
| MySQL | Database |
| Lombok | Boilerplate Reduction |
| Maven | Build Tool |

---

## 📁 Project Structure

```
src/main/java/com/springboot/useradminrolebased/
│
├── Controller/
│   ├── AuthController.java       # Register & Login endpoints
│   ├── UserController.java       # User-specific endpoints
│   └── AdminController.java      # Admin-specific endpoints
│
├── Service/
│   ├── AuthService.java          # Register & Login logic
│   ├── UserService.java          # User CRUD with identity check
│   └── AdminService.java         # Admin CRUD for all users
│
├── Entity/
│   ├── Users.java                # User entity
│   └── Role.java                 # Enum: USER, ADMIN
│
├── DTO/
│   ├── RegisterRequestDTO.java   # Register input
│   ├── RegisterResponseDTO.java  # Register output
│   ├── LoginRequestDTO.java      # Login input
│   ├── LoginResponseDTO.java     # Login output
│   ├── UpdateRequestDTO.java     # Update input (optional fields)
│   └── UpdateResponseDTO.java    # Update output
│
├── Repository/
│   └── UserRepository.java       # Single repository for Users entity
│
├── Security/
│   └── SecurityClass.java        # SecurityFilterChain + UserDetailsService
│
├── Mapper/
│   └── UserMapper.java           # Reusable mapper methods
│
├── Exception/
│   ├── EmailExistsException.java
│   ├── UserNotFoundException.java
│   ├── UnauthorizedException.java
│   └── InvalidCredentialsException.java
│
├── Handler/
│   └── GlobalExceptionHandler.java  # Centralized exception handling
│
└── Response/
    └── ApiResponse.java             # Generic API response wrapper
```

---

## 🔐 Security Flow

```
Request
   │
   ▼
SecurityFilterChain
   │
   ├── /api/auth/**  ──► permitAll() ──► AuthController
   │
   ├── /api/user/**  ──► hasRole("USER") ──► UserController
   │
   └── /api/admin/** ──► hasRole("ADMIN") ──► AdminController
         │
         ▼
   UserDetailsService
         │
         ▼
   Loads user from DB by email
         │
         ▼
   Spring Security validates credentials
         │
         ▼
   SecurityContextHolder stores Authentication
         │
         ▼
   @PreAuthorize checks role at method level
```

---

## 📡 API Endpoints

### 🔓 Auth Endpoints (Public)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login user |

### 👤 User Endpoints (Role: USER)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/user/{userId}` | Get own profile |
| PUT | `/api/user/{userId}` | Update own profile |
| DELETE | `/api/user/{userId}` | Delete own account |

### 👑 Admin Endpoints (Role: ADMIN)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/admin` | Get all users |
| GET | `/api/admin/{userId}` | Get user by ID |
| PUT | `/api/admin/{userId}` | Update any user |
| DELETE | `/api/admin/{userId}` | Delete any user |

---

## ⚙️ Exception Handling

| Exception | Status Code | When Triggered |
|---|---|---|
| `EmailExistsException` | 409 Conflict | Duplicate email on register |
| `UserNotFoundException` | 404 Not Found | User ID doesn't exist |
| `InvalidCredentialsException` | 401 Unauthorized | Wrong password |
| `UnauthorizedException` | 403 Forbidden | Accessing another user's data |
| `MethodArgumentNotValidException` | 400 Bad Request | Invalid request body |
| `Exception` | 500 Internal Server Error | Unexpected errors |

---

## 📦 API Response Format

All endpoints return a consistent response structure:

```json
{
    "statusCode": 200,
    "status": "Success",
    "message": "User fetched successfully",
    "data": { }
}
```

---

## 🚀 How to Run

### Prerequisites
- Java 21
- MySQL
- Maven

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/your-username/UserAdminRoleBased.git

# 2. Configure database in application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password

# 3. Build the project
mvn clean package -DskipTests

# 4. Run the project
mvn spring-boot:run
```

---

## 🧪 Testing via Postman

**Register User:**
```json
POST /api/auth/register
{
    "userName": "Dhruv",
    "userEmail": "dhruv@gmail.com",
    "password": "dhruv123",
    "role": "USER"
}
```

**Login (Basic Auth):**
```
Authorization: Basic Auth
Username: dhruv@gmail.com
Password: dhruv123
```

---

## 👨‍💻 Author

**Dhruv Malusare**  
Java Full Stack Developer  
Vadodara, Gujarat
