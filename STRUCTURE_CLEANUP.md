# 📁 Projekt Struktúra - Átrendezés Státusz

## ✅ Átmozgatott Fájlok

### View Package (com.casino.view)
Átmozgatva a `src/main/java/com/casino/view/` mappába:
- ✅ CasinoLoginFrame.java
- ✅ MainMenuFrame.java
- ✅ BlackjackGame.java
- ✅ RouletteGame.java
- ✅ CrashGame.java
- ✅ LottoGame.java

### Model Package (com.casino.model)
- ✅ User.java (már benne van)

### Service Package (com.casino.service)
- ✅ UserService.java (már benne van)
- ✅ CrashGameService.java (már benne van)

### Controller Package (com.casino.controller)
- ✅ AuthController.java (már benne van)

## 🔧 Szükséges Módosítások

### 1. Package Deklarációk Hozzáadása
Minden view fájlhoz add hozzá:
```java
package com.casino.view;

import com.casino.model.User;
import com.casino.service.UserService;
```

### 2. UserManager → UserService Csere
A view fájlokban cseréld le:
```java
// Régi:
UserManager userManager = UserManager.getInstance();

// Új:
UserService userService = UserService.getInstance();
```

### 3. Importok Frissítése
```java
// Töröld:
import User;
import UserManager;

// Add hozzá:
import com.casino.model.User;
import com.casino.service.UserService;
```

## 📂 Végleges Struktúra

```
casino/
├── pom.xml
├── README.md
├── SPRING_BOOT_MVC.md
├── STRUCTURE_CLEANUP.md
│
├── src/main/
│   ├── java/com/casino/
│   │   ├── Main.java                          ✅ Új main
│   │   ├── CasinoApplication.java             ✅ Spring Boot main
│   │   │
│   │   ├── model/
│   │   │   └── User.java                      ✅
│   │   │
│   │   ├── service/
│   │   │   ├── UserService.java               ✅
│   │   │   └── CrashGameService.java          ✅
│   │   │
│   │   ├── controller/
│   │   │   └── AuthController.java            ✅
│   │   │
│   │   └── view/
│   │       ├── CasinoLoginFrame.java          ✅ (package kell)
│   │       ├── MainMenuFrame.java             ✅ (package kell)
│   │       ├── BlackjackGame.java             ✅ (package kell)
│   │       ├── RouletteGame.java              ✅ (package kell)
│   │       ├── CrashGame.java                 ✅ (package kell)
│   │       └── LottoGame.java                 ✅ (package kell)
│   │
│   └── resources/
│       └── application.properties             ✅
│
└── (gyökér - törölhető régi fájlok)
    ├── Main.java                              ❌ Régi
    ├── User.java                              ❌ Régi
    ├── UserManager.java                       ❌ Régi
    ├── LoginFrame.java                        ❌ Nem használt
    ├── ModernLoginFrame.java                  ❌ Nem használt
    ├── MainMVC.java                           ❌ Duplikált
    ├── *.class                                ❌ Compiled fájlok
    └── model/, controller/, view/             ❌ Régi üres mappák
```

## 🚀 Következő Lépések

### 1. Package Deklarációk (Manuális)
Minden view fájl elejére:
```java
package com.casino.view;
```

### 2. Importok Javítása
Cseréld le az összes view fájlban:
- `UserManager` → `UserService`
- Add hozzá: `import com.casino.model.User;`
- Add hozzá: `import com.casino.service.UserService;`

### 3. Fordítás
```bash
cd C:\Users\ASUS\Desktop\casino
javac -d bin -sourcepath src/main/java src/main/java/com/casino/**/*.java
```

### 4. Futtatás
```bash
java -cp bin com.casino.Main
```

### 5. Takarítás (Opcionális)
Töröld a gyökérből:
- Régi *.java fájlok
- *.class fájlok
- Üres model/, controller/, view/ mappák

## ⚠️ Fontos

A view fájlok **át vannak mozgatva**, de még **nem működnek** mert:
1. Hiányzik a `package com.casino.view;` deklaráció
2. Hiányoznak a megfelelő importok
3. UserManager helyett UserService-t kell használni

**Ezeket manuálisan kell javítani minden view fájlban!**
