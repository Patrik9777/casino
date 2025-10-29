# 🎰 Gypsy Feeling Casino

Egy teljes funkcionalitású casino alkalmazás Java Swing-ben, 4 különböző játékkal és valódi kaszinó algoritmusokkal.

## 🎮 Játékok

### 🎴 Blackjack
- Klasszikus 21-es játék
- Lapot kérhetsz vagy megállhatsz
- Az osztó 17-ig húz automatikusan
- 2:1 kifizetés nyerés esetén

### 🎲 Rulett
- Európai rulett 37 számmal (0-36)
- Tételhetsz számra vagy színre (piros/fekete)
- Animált rulett kerék
- Számon 36:1, színen 2:1 kifizetés

### 💥 Crash
- Valós idejű szorzó növekedés
- Provably Fair algoritmus (97% RTP, 3% house edge)
- Start/Stop mechanizmus
- Crash history az utolsó 5 játékról
- ~3% esély instant bust-ra (1.00x)

### 🎟️ Lotto (5/45)
- Válassz 5 számot 1-45 között
- Véletlen szám generátor
- Nyeremények:
  - 5 találat: 1000x (JACKPOT!)
  - 4 találat: 50x
  - 3 találat: 10x
  - 2 találat: 2x

## ✨ Funkciók

- 🔐 **Login/Regisztráció rendszer**
- 👤 **Profil kezelés** - becenév módosítás
- 💰 **Befizetés** - kártyaszám (max 20 digit), kuponkódok (GYPSY10, MARIJO50, LAKATOS_NARUTO250)
- 💸 **Kifizetés**
- 📊 **Egyenleg követés**
- 🎨 **Modern, sötét téma arany akcentusokkal**
- ⚡ **Smooth animációk és effektek**

## 🎲 Crash Algoritmus

A Crash játék **provably fair** algoritmust használ, ami megegyezik a valódi online kaszinókban használt módszerrel:

- **97% RTP** (Return to Player)
- **3% House Edge** (kaszinó előny)
- **Instant Bust**: ~3% esély, hogy 1.00x-nél crashel
- **Matematikai formula**: `99 / (99 - (random * 100))`

Ez biztosítja, hogy:
- Alacsony szorzók (1.5x-3x) gyakoribbak
- Magas szorzók (10x+) ritkábbak
- Hosszú távon a kaszinó 3%-ot nyer

## 🚀 Indítás

```bash
# Fordítás
javac *.java

# Futtatás
java Main
```

## 🔑 Teszt felhasználó

- **Felhasználónév**: user1
- **Jelszó**: jelszo123
- **Kezdő egyenleg**: $1000

## 📋 Követelmények

- Java 8 vagy újabb
- Windows OS (tesztelve Windows-on)

## 🎨 Technológiák

- **Java Swing** - GUI framework
- **AWT Graphics2D** - Custom rendering, animációk
- **Timer** - Játék loop és animációk
- **Provably Fair RNG** - Crash algoritmus

## 📝 Fájlok

- `Main.java` - Belépési pont
- `CasinoLoginFrame.java` - Login/regisztráció UI
- `MainMenuFrame.java` - Főmenü és játék navigáció
- `BlackjackGame.java` - Blackjack játék
- `RouletteGame.java` - Rulett játék animált kerékkel
- `CrashGame.java` - Crash játék provably fair algoritmussal
- `LottoGame.java` - Lotto játék
- `User.java` - User model
- `UserManager.java` - User kezelés

## 🎯 Jövőbeli fejlesztések

- [ ] Adatbázis integráció (perzisztens adatok)
- [ ] Több játék (Poker, Slots, stb.)
- [ ] Statisztikák és history
- [ ] Hangeffektek
- [ ] Multiplayer support
- [ ] Leaderboard

## 📄 Licenc

Ez egy oktatási/hobbi projekt.

---

**Készítette**: Gypsy Feeling Casino Team 🎰
**Casino név**: GYPSY FEELING
