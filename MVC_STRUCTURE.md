# MVC Architektúra - Casino Projekt

## 📁 Jelenlegi Struktúra vs MVC

### Jelenlegi (Működő):
```
casino/
├── Main.java
├── User.java
├── UserManager.java
├── CasinoLoginFrame.java
├── MainMenuFrame.java
├── BlackjackGame.java
├── RouletteGame.java
├── CrashGame.java
└── LottoGame.java
```

### MVC Struktúra (Javasolt):
```
casino/
├── Main.java
├── model/
│   ├── User.java              ✓ Kész
│   ├── UserManager.java       ✓ Kész
│   ├── GameModel.java
│   ├── BlackjackModel.java
│   ├── RouletteModel.java
│   ├── CrashModel.java
│   └── LottoModel.java
├── view/
│   ├── LoginView.java
│   ├── MainMenuView.java
│   ├── BlackjackView.java
│   ├── RouletteView.java
│   ├── CrashView.java
│   └── LottoView.java
└── controller/
    ├── LoginController.java   ✓ Kész
    ├── MainMenuController.java
    ├── BlackjackController.java
    ├── RouletteController.java
    ├── CrashController.java
    └── LottoController.java
```

## 🎯 MVC Rétegek Magyarázata

### MODEL (Adatok és üzleti logika)
- **Felelősség**: Adatok tárolása, üzleti szabályok
- **Példa**: 
  - `User.java` - felhasználó adatai
  - `CrashModel.java` - crash point számítás, játék logika
  - `BlackjackModel.java` - kártyák, pontszámítás

### VIEW (Megjelenítés)
- **Felelősség**: UI komponensek, megjelenítés
- **Példa**:
  - `LoginView.java` - bejelentkezési felület
  - `CrashView.java` - crash játék UI (gombok, címkék)
  - Csak megjelenít, NEM tartalmaz üzleti logikát

### CONTROLLER (Közvetítő)
- **Felelősség**: View és Model közötti kommunikáció
- **Példa**:
  - `LoginController.java` - bejelentkezés kezelése
  - `CrashController.java` - tétet helyez, kifizetés kezelése
  - Fogadja a View eseményeit, frissíti a Modelt

## 📊 Példa: Crash Játék MVC-ben

### Jelenlegi (Monolitikus):
```java
public class CrashGame extends JDialog {
    // UI komponensek
    private JButton betButton;
    private JLabel multiplierLabel;
    
    // Üzleti logika
    private double crashPoint;
    private void calculateCrashPoint() { ... }
    
    // Eseménykezelés
    private void placeBet() { ... }
}
```

### MVC Struktúra:
```java
// MODEL - Üzleti logika
public class CrashModel {
    private double crashPoint;
    private double currentMultiplier;
    
    public double calculateCrashPoint() { ... }
    public void updateMultiplier() { ... }
}

// VIEW - UI
public class CrashView extends JDialog {
    private JButton betButton;
    private JLabel multiplierLabel;
    
    public void updateMultiplierDisplay(double mult) {
        multiplierLabel.setText(mult + "x");
    }
}

// CONTROLLER - Közvetítő
public class CrashController {
    private CrashModel model;
    private CrashView view;
    
    public void placeBet(int amount) {
        model.startGame();
        view.updateMultiplierDisplay(model.getCurrentMultiplier());
    }
}
```

## ✅ MVC Előnyei

1. **Szeparáció**: Üzleti logika elkülönül a UI-tól
2. **Tesztelhetőség**: Model külön tesztelhető UI nélkül
3. **Újrafelhasználhatóság**: Model több View-ban is használható
4. **Karbantarthatóság**: Könnyebb módosítani és bővíteni
5. **Csapatmunka**: Párhuzamosan dolgozhatnak rajta többen

## 🚀 Átállás Lépései

### 1. Fázis - Model réteg (✓ Kész)
- [x] User.java áthelyezése
- [x] UserManager.java áthelyezése
- [ ] GameModel osztályok létrehozása

### 2. Fázis - Controller réteg (Részben kész)
- [x] LoginController.java
- [ ] GameController osztályok

### 3. Fázis - View réteg
- [ ] Meglévő Frame osztályok refaktorálása
- [ ] UI logika elkülönítése

### 4. Fázis - Integráció
- [ ] Összekapcsolás
- [ ] Tesztelés
- [ ] Dokumentáció

## 📝 Megjegyzés

A jelenlegi alkalmazás **működik és jól szervezett**.
Az MVC átállítás **opcionális** - csak akkor érdemes, ha:
- Nagyobb projektre bővül
- Több fejlesztő dolgozik rajta
- Unit teszteket akarsz írni
- Más UI-t akarsz (pl. web verzió)

## 🎮 Használat

### Jelenlegi verzió futtatása:
```bash
javac *.java
java Main
```

### MVC verzió futtatása (ha elkészül):
```bash
javac model/*.java controller/*.java view/*.java MainMVC.java
java MainMVC
```
