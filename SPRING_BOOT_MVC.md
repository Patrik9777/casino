# 🍃 Spring Boot MVC Struktúra - Gypsy Feeling Casino

## 📁 Projekt Struktúra

```
casino/
├── pom.xml                                    # Maven konfiguráció
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── casino/
│       │           ├── CasinoApplication.java      # Main class
│       │           ├── model/                      # Entitások
│       │           │   └── User.java
│       │           ├── service/                    # Business Logic
│       │           │   ├── UserService.java
│       │           │   └── CrashGameService.java
│       │           ├── controller/                 # REST/MVC Controllers
│       │           │   └── AuthController.java
│       │           └── view/                       # Frontend (Swing UI)
│       │               ├── LoginView.java
│       │               ├── MainMenuView.java
│       │               └── games/
│       │                   ├── CrashGameView.java
│       │                   ├── BlackjackView.java
│       │                   ├── RouletteView.java
│       │                   └── LottoView.java
│       └── resources/
│           └── application.properties              # Spring konfiguráció
└── README.md
```

## 🎯 MVC Rétegek

### 1. MODEL (com.casino.model)
**Felelősség**: Adatok és entitások
- `User.java` - Felhasználó entitás
- JPA annotációkkal (@Entity, @Id, @GeneratedValue)
- Getters/Setters
- Business methods (addBalance, deductBalance)

**Spring Boot annotációk**:
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // ...
}
```

### 2. SERVICE (com.casino.service)
**Felelősség**: Üzleti logika
- `UserService.java` - User műveletek (register, login, balance)
- `CrashGameService.java` - Crash játék logika
- `BlackjackService.java` - Blackjack logika
- `RouletteService.java` - Rulett logika

**Spring Boot annotációk**:
```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public Optional<User> authenticateUser(String username, String password) {
        // Business logic
    }
}
```

### 3. CONTROLLER (com.casino.controller)
**Felelősség**: HTTP kérések kezelése (REST API)
- `AuthController.java` - Login/Register endpoints
- `GameController.java` - Játék endpoints
- `UserController.java` - User műveletek

**Spring Boot annotációk**:
```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Handle login
    }
}
```

### 4. VIEW (com.casino.view)
**Felelősség**: Frontend (Swing UI vagy Web)
- Swing: JFrame, JDialog komponensek
- Web: HTML/CSS/JavaScript (Thymeleaf, React, Angular)
- Csak megjelenítés, nincs üzleti logika

## 🚀 Spring Boot Futtatás

### Maven-nel:
```bash
cd C:\Users\ASUS\Desktop\casino
mvn clean install
mvn spring-boot:run
```

### JAR-ból:
```bash
mvn package
java -jar target/gypsy-feeling-casino-1.0.0.jar
```

### IDE-ből:
- Futtasd a `CasinoApplication.java` main metódusát

## 🗄️ Adatbázis

### H2 In-Memory Database
- **URL**: `jdbc:h2:mem:casinodb`
- **Console**: http://localhost:8080/h2-console
- **Username**: sa
- **Password**: (üres)

### JPA Repository (Opcionális):
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
```

## 📦 Dependencies (pom.xml)

- **Spring Boot Starter** - Core
- **Spring Boot Starter Web** - REST API
- **Spring Boot Starter Data JPA** - Adatbázis
- **H2 Database** - In-memory DB
- **Lombok** - Boilerplate csökkentés
- **Spring Boot DevTools** - Hot reload

## 🔧 Konfiguráció

### application.properties
```properties
spring.application.name=gypsy-feeling-casino
server.port=8080

# Database
spring.datasource.url=jdbc:h2:mem:casinodb
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
```

## 🎮 REST API Endpoints (Példa)

### Authentication
```
POST /api/auth/register
POST /api/auth/login
POST /api/auth/logout
```

### User
```
GET  /api/user/profile
PUT  /api/user/profile
GET  /api/user/balance
POST /api/user/deposit
POST /api/user/withdraw
```

### Games
```
POST /api/game/crash/start
POST /api/game/crash/cashout
GET  /api/game/crash/history

POST /api/game/blackjack/start
POST /api/game/blackjack/hit
POST /api/game/blackjack/stand
```

## 🧪 Tesztelés

### Unit Test (Service)
```java
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    
    @Test
    void testRegisterUser() {
        boolean result = userService.registerUser("test", "pass", "test@test.com");
        assertTrue(result);
    }
}
```

### Integration Test (Controller)
```java
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testLogin() throws Exception {
        mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"user1\",\"password\":\"pass\"}"))
            .andExpect(status().isOk());
    }
}
```

## 📊 Előnyök

### Spring Boot MVC:
✅ **Auto-configuration** - Automatikus beállítások
✅ **Dependency Injection** - Loose coupling
✅ **REST API** - Könnyen készíthető API
✅ **Database Integration** - JPA/Hibernate
✅ **Testing Support** - Beépített teszt eszközök
✅ **Production Ready** - Metrics, Health checks
✅ **Scalable** - Microservices-re bővíthető

### Jelenlegi Swing UI vs Web:
| Jelenlegi (Swing) | Spring Boot Web |
|-------------------|-----------------|
| Desktop app | Web app |
| JFrame/JDialog | HTML/CSS/JS |
| Egy gépen fut | Böngészőből elérhető |
| Nehéz frissíteni | Könnyű deploy |

## 🔄 Migráció Lépések

### 1. Backend (Kész)
- [x] Model réteg
- [x] Service réteg
- [x] Controller réteg
- [x] pom.xml
- [x] application.properties

### 2. Frontend (Választható)
- [ ] Swing UI megtartása
- [ ] Web UI készítése (Thymeleaf/React)

### 3. Database
- [ ] H2 → MySQL/PostgreSQL
- [ ] JPA Repository implementáció

### 4. Security
- [ ] Spring Security
- [ ] JWT Authentication
- [ ] Password encryption (BCrypt)

## 📝 Használat

### Jelenlegi Swing verzió:
```bash
cd C:\Users\ASUS\Desktop\casino
javac *.java
java Main
```

### Spring Boot verzió (ha elkészül):
```bash
cd C:\Users\ASUS\Desktop\casino
mvn spring-boot:run
```
Böngésző: http://localhost:8080

## 🎯 Következő Lépések

1. **View réteg** - LoginView, MainMenuView átírása
2. **Repository réteg** - JPA repository-k
3. **DTO osztályok** - Request/Response objektumok
4. **Exception Handling** - @ControllerAdvice
5. **Security** - Spring Security integráció
6. **Testing** - Unit és Integration tesztek
7. **Documentation** - Swagger/OpenAPI

## 📚 Források

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring MVC Guide](https://spring.io/guides/gs/serving-web-content/)
- [JPA Documentation](https://spring.io/projects/spring-data-jpa)
