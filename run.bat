@echo off
cd /d C:\Users\ASUS\Desktop\casino
echo Forditas...
javac -d bin -sourcepath src\main\java src\main\java\com\casino\Main.java
if %errorlevel% neq 0 (
    echo Forditas sikertelen!
    pause
    exit /b 1
)
echo Forditas kesz!
echo.
echo Inditas...
java -cp bin com.casino.Main
if %errorlevel% neq 0 (
    echo Hiba tortent!
    pause
)
