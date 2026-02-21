#!/bin/bash

echo "==============================="
echo " DÉMARRAGE WEBSITE E-COMMERCE "
echo "==============================="

# Aller dans le dossier du projet (là où est le script)
cd "$(dirname "$0")" || exit 1

echo "Vérification Docker Compose..."
docker compose ps

echo "Arrêt des conteneurs Docker Compose..."
docker compose down --remove-orphans

echo "Arrêt des conteneurs Docker restants..."
docker stop $(docker ps -q) 2>/dev/null
docker rm $(docker ps -a -q) 2>/dev/null

echo "Build & lancement des conteneurs..."
docker compose up -d --build

echo "État des conteneurs :"
docker compose ps

echo "Démarrage terminé"
echo "➡ Eureka : http://localhost:8761"
echo "➡ Site : http://localhost:8081"
