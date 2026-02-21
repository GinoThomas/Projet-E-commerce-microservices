# --- ÉTAPE 1 : Compilation (Build) ---
# On utilise une image Maven avec JDK 17 pour compiler
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# On copie le fichier de configuration Maven (pom.xml)
COPY pom.xml .
# On télécharge les dépendances (mise en cache pour gagner du temps)
RUN mvn dependency:go-offline

# On copie le code source et on compile le projet
COPY src ./src
RUN mvn clean package -DskipTests

# --- ÉTAPE 2 : Exécution (Runtime) ---
# On utilise une image JRE (Java Runtime) beaucoup plus légère
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# On récupère le fichier .jar généré à l'étape précédente
# Le nom du JAR est souvent défini dans votre pom.xml
COPY --from=build /app/target/*.jar app.jar

# On expose le port interne (8080 par défaut pour Spring Boot)
EXPOSE 8081

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]