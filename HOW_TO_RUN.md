# 🚀 Futtatási Útmutató - Gypsy Feeling Casino

## 📁 Projekt Struktúra

```
casino/
├── pom.xml                           # Maven konfiguráció
├── README.md                         # Projekt leírás
├── SPRING_BOOT_MVC.md               # MVC dokumentáció
├── HOW_TO_RUN.md                    # Ez a fájl
├── bin/                             # Compiled fájlok
└── src/main/java/com/casino/
    ├── Main.java                    # Entry point
    ├── model/                       # Adatok
    ├── service/                     # Üzleti logika
    ├── controller/                  # API
    └── view/                        # UI (Swing)
```

## ✅ Fordítás és Futtatás

### 1. Fordítás
```bash
cd C:\Users\ASUS\Desktop\casino
javac -d bin -sourcepath src\main\java src\main\java\com\casino\Main.java
```

### 2. Futtatás
```bash
java -cp bin com.casino.Main
```

### 3. Egy lépésben (Windows)
```bash
cd C:\Users\ASUS\Desktop\casino && javac -d bin -sourcepath src\main\java src\main\java\com\casino\Main.java && java -cp bin com.casino.Main
```

## 🎮 Használat

### Bejelentkezés
- **Felhasználónév**: user1
- **Jelszó**: jelszo123
- **Kezdő egyenleg**: $1000

### Játékok
- 🎴 **Blackjack** - 21-es játék
- 🎲 **Rulett** - Európai rulett (0-36)
- 💥 **Crash** - Provably Fair (97% RTP)
- 🎟️ **Lotto** - 5/45 lottó

## 🔧 Fejlesztői Mód

### Teljes újrafordítás
```bash
cd C:\Users\ASUS\Desktop\casino
rmdir /s /q bin
mkdir bin
javac -d bin -sourcepath src\main\java src\main\java\com\casino\**\*.java
```

### Csak egy fájl fordítása
```bash
javac -d bin -cp bin -sourcepath src\main\java src\main\java\com\casino\view\CrashGame.java
```

## 📦 Maven Futtatás (Opcionális)

Ha Maven telepítve van:

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.casino.Main"
```

Vagy Spring Boot módban:
```bash
mvn spring-boot:run
```

## 🐛 Hibaelhárítás

### "cannot find symbol" hiba
```bash
# Töröld a bin mappát és fordítsd újra
rmdir /s /q bin
mkdir bin
javac -d bin -sourcepath src\main\java src\main\java\com\casino\Main.java
```

### "NoClassDefFoundError"
```bash
# Ellenőrizd a classpath-t
java -cp bin com.casino.Main
```

### UI nem jelenik meg
```bash
# Ellenőrizd hogy a Swing támogatott-e
java -version
# Java 8 vagy újabb kell
```

## 📝 Package Struktúra

```
com.casino
├── Main.java                        # Entry point
├── CasinoApplication.java           # Spring Boot main
├── model
│   └── User.java                    # User entitás
├── service
│   ├── UserService.java             # User műveletek
│   └── CrashGameService.java        # Crash logika
├── controller
│   └── AuthController.java          # Auth API
└── view
    ├── CasinoLoginFrame.java        # Login UI
    ├── MainMenuFrame.java           # Főmenü
    ├── BlackjackGame.java           # Blackjack UI
    ├── RouletteGame.java            # Rulett UI
    ├── CrashGame.java               # Crash UI
    └── LottoGame.java               # Lotto UI
```

## 🎯 Gyors Parancsok

### Windows (PowerShell)
```powershell
# Fordítás + Futtatás
cd C:\Users\ASUS\Desktop\casino
javac -d bin -sourcepath src\main\java src\main\java\com\casino\Main.java; java -cp bin com.casino.Main
```

### Windows (CMD)
```cmd
cd C:\Users\ASUS\Desktop\casino
javac -d bin -sourcepath src\main\java src\main\java\com\casino\Main.java && java -cp bin com.casino.Main
```

## 📊 Követelmények

- **Java**: 8 vagy újabb
- **OS**: Windows, Linux, macOS
- **RAM**: Min. 512MB
- **Képernyő**: Min. 1024x768

## 🔐 Teszt Felhasználók

| Username | Password | Balance |
|----------|----------|---------|
| user1    | jelszo123| $1000   |

Új felhasználót a regisztrációs felületen tudsz létrehozni.

## 📚 További Dokumentáció

- `README.md` - Projekt áttekintés
- `SPRING_BOOT_MVC.md` - MVC architektúra
- `STRUCTURE_CLEANUP.md` - Átrendezés története
- `MVC_STRUCTURE.md` - MVC példák

---

**Készítette**: Gypsy Feeling Casino Team 🎰
