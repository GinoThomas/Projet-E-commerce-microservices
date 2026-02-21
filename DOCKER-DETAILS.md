1. Démarrage sous Linux Ubuntu

# Lancer votre terminal de commande
CTRL + ALT + T

# Aller sur le fichier source
cd [lien vers de dossier website-e-commerce] {Documents/website-e-commerce}

# Vérifier qu'un conteneur ne tourne pas en arrière plan
docker compose ps
    Si il y a des conteneurs, faire la commande : docker compose down
docker ps
    Si il y a des conteneurs, faire la commande : docker stop $(docker ps -q) | docker rm $(docker ps -a -q)

# Construire les images et lancer en arrière-plan
docker compose up -d --build

# Vérifier que les images sont bien lancées
docker compose ps

Forme résultat attendu :
NAME                       IMAGE                                         COMMAND               SERVICE                    CREATED         STATUS         PORTS
authentification-service   website-e-commerce-service-authentification   "java -jar app.jar"   service-authentification   8 seconds ago   Up 6 seconds   0.0.0.0:8085->8085/tcp, [::]:8085->8085/tcp
catalogue-service          website-e-commerce-service-catalogue          "java -jar app.jar"   service-catalogue          8 seconds ago   Up 6 seconds   0.0.0.0:8081->8081/tcp, [::]:8081->8081/tcp
eureka-server              website-e-commerce-service-eureka             "java -jar app.jar"   service-eureka             8 seconds ago   Up 7 seconds   0.0.0.0:8761->8761/tcp, [::]:8761->8761/tcp
paiement-service           website-e-commerce-service-paiement           "java -jar app.jar"   service-paiement           8 seconds ago   Up 6 seconds   0.0.0.0:8083->8083/tcp, [::]:8083->8083/tcp
panier-service             website-e-commerce-service-panier             "java -jar app.jar"   service-panier             8 seconds ago   Up 6 seconds   0.0.0.0:8082->8082/tcp, [::]:8082->8082/tcp
suivi-stat-service         website-e-commerce-service-stat               "java -jar app.jar"   service-stat               8 seconds ago   Up 6 seconds   0.0.0.0:8084->8084/tcp, [::]:8084->8084/tcp

# Vérifier que le site marche

Eureka : Allez sur http://localhost:8761. Attendez que les 5 services soient enregistrés (statut UP).

Navigateur : Allez sur http://localhost:8081.


2. Démarrage sous Windows

# Lancer votre IDE

# Ouvrir le fichier website-e-commerce

# Ouvrir un nouveau terminal

# Vérifier qu'un conteneur ne tourne pas en arrière plan
docker compose ps
    Si il y a des conteneurs, faire la commande : docker compose down
docker ps
    Si il y a des conteneurs, faire la commande : docker stop $(docker ps -q) | docker rm $(docker ps -a -q)

# Construire les images et lancer en arrière-plan
docker compose up -d --build

# Vérifier que les images sont bien lancées
docker compose ps

Forme résultat attendu :
NAME                       IMAGE                                         COMMAND               SERVICE                    CREATED         STATUS         PORTS
authentification-service   website-e-commerce-service-authentification   "java -jar app.jar"   service-authentification   8 seconds ago   Up 6 seconds   0.0.0.0:8085->8085/tcp, [::]:8085->8085/tcp
catalogue-service          website-e-commerce-service-catalogue          "java -jar app.jar"   service-catalogue          8 seconds ago   Up 6 seconds   0.0.0.0:8081->8081/tcp, [::]:8081->8081/tcp
eureka-server              website-e-commerce-service-eureka             "java -jar app.jar"   service-eureka             8 seconds ago   Up 7 seconds   0.0.0.0:8761->8761/tcp, [::]:8761->8761/tcp
paiement-service           website-e-commerce-service-paiement           "java -jar app.jar"   service-paiement           8 seconds ago   Up 6 seconds   0.0.0.0:8083->8083/tcp, [::]:8083->8083/tcp
panier-service             website-e-commerce-service-panier             "java -jar app.jar"   service-panier             8 seconds ago   Up 6 seconds   0.0.0.0:8082->8082/tcp, [::]:8082->8082/tcp
suivi-stat-service         website-e-commerce-service-stat               "java -jar app.jar"   service-stat               8 seconds ago   Up 6 seconds   0.0.0.0:8084->8084/tcp, [::]:8084->8084/tcp

# Vérifier que le site marche

Eureka : Allez sur http://localhost:8761. Attendez que les 5 services soient enregistrés (statut UP).

Navigateur : Allez sur http://localhost:8081.

3. Propriétés

Java version : 17
Spring boot version : 4.0.1
Configuration : YAML

