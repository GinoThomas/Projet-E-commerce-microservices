@echo off
echo ===============================
echo  DEMARRAGE WEBSITE E-COMMERCE
echo ===============================

REM Aller dans le dossier où est le script
cd /d %~dp0

REM Vérifier si Docker est installé
docker --version >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo Erreur : Docker n'est pas installé ou pas dans le PATH.
    pause
    exit /b 1
)

REM Arrêt et suppression des conteneurs Docker Compose
echo 🧹 Arrêt des conteneurs Docker Compose...
docker compose down --remove-orphans

REM Arrêt et suppression de tous les conteneurs Docker résiduels
for /f "tokens=*" %%i in ('docker ps -q') do docker stop %%i
for /f "tokens=*" %%i in ('docker ps -a -q') do docker rm %%i

REM Construction et lancement des conteneurs
echo 🚀 Build & lancement des conteneurs...
docker compose up -d --build

REM Afficher l'état des conteneurs
echo 📊 État des services :
docker compose ps

echo.
echo ✅ Démarrage terminé
echo ➡ Eureka : http://localhost:8761
echo ➡ Site   : http://localhost:8081
pause
