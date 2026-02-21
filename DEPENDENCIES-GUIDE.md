# Guide des Dépendances Maven pour chaque Microservice

## 📦 Dépendances communes à TOUS les microservices

Lors de la création de chaque projet sur **Spring Initializr** (https://start.spring.io/), sélectionne :
- **Project** : Maven
- **Language** : Java
- **Spring Boot** : 2.7.x ou 3.x (selon ta préférence)
- **Java** : 17

---

## 1️⃣ eureka-server

### Dépendances à ajouter sur Spring Initializr :
- ✅ **Eureka Server** (sous Spring Cloud Discovery)

### pom.xml - Dépendances spécifiques :
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

---

## 2️⃣ catalogue-service

### Dépendances à ajouter sur Spring Initializr :
- ✅ **Spring Web**
- ✅ **Eureka Discovery Client** (sous Spring Cloud Discovery)

### pom.xml - Dépendances spécifiques :
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

---

## 3️⃣ panier-service

### Dépendances à ajouter sur Spring Initializr :
- ✅ **Spring Web**
- ✅ **Eureka Discovery Client** (sous Spring Cloud Discovery)

### pom.xml - Dépendances spécifiques :
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

---

## 4️⃣ paiement-service

### Dépendances à ajouter sur Spring Initializr :
- ✅ **Spring Web**
- ✅ **Eureka Discovery Client** (sous Spring Cloud Discovery)

### pom.xml - Dépendances spécifiques :
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

---

## 5️⃣ suivi-stat-service

### Dépendances à ajouter sur Spring Initializr :
- ✅ **Spring Web**
- ✅ **Eureka Discovery Client** (sous Spring Cloud Discovery)

### pom.xml - Dépendances spécifiques :
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

---

## ⚠️ IMPORTANT : Gestion des versions Spring Cloud

Ajoute ce bloc dans **TOUS** les fichiers `pom.xml` pour gérer les versions Spring Cloud :

```xml
<properties>
    <java.version>17</java.version>
    <spring-cloud.version>2021.0.5</spring-cloud.version>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

**Note** : Si tu utilises Spring Boot 3.x, utilise `spring-cloud.version` = `2022.0.0` ou plus récent.

---

## 📂 Structure des fichiers pour chaque projet

### Exemple pour **catalogue-service** :

```
catalogue-service/
├── src/
│   ├── main/
│   │   ├── java/com/example/catalogueservice/
│   │   │   ├── CatalogueServiceApplication.java
│   │   │   ├── controller/
│   │   │   │   └── CatalogueController.java
│   │   │   ├── model/
│   │   │   │   └── Produit.java
│   │   │   └── service/
│   │   │       └── CatalogueService.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── static/  ← PLACE LE FRONT-END ICI (index.html, style.css, app.js)
│   └── test/
└── pom.xml
```

---

## 🚀 Ordre de démarrage des services

1. **eureka-server** (port 8761)
2. **catalogue-service** (port 8081)
3. **panier-service** (port 8082)
4. **paiement-service** (port 8083)
5. **suivi-stat-service** (port 8084)

Une fois tous les services lancés, accède au front-end via : **http://localhost:8081**

---

## ✅ Vérification

- Eureka Dashboard : http://localhost:8761
- Les 4 microservices doivent apparaître enregistrés dans Eureka
- Front-end : http://localhost:8081/index.html

---

## 📝 Notes importantes

1. **Front-end** : Place les fichiers `index.html`, `style.css` et `app.js` dans `catalogue-service/src/main/resources/static/`
2. **CORS** : Les controllers ont déjà `@CrossOrigin(origins = "*")` pour éviter les problèmes CORS
3. **RestTemplate** : Le bean est configuré dans `paiement-service` pour la communication REST
4. **Pas de BDD externe** : Toutes les données sont en mémoire (HashMap, ArrayList)
5. **Java 17** : Assure-toi d'avoir Java 17 installé (`java -version`)

---

## 🐛 Troubleshooting

Si un service ne démarre pas :
1. Vérifie que le port n'est pas déjà utilisé
2. Vérifie que Eureka est bien démarré en premier
3. Vérifie les logs dans la console
4. Vérifie que les packages correspondent bien aux noms de fichiers

Si le front-end ne se charge pas :
1. Vérifie que les fichiers sont dans `catalogue-service/src/main/resources/static/`
2. Relance `catalogue-service` après avoir ajouté les fichiers
3. Vérifie la console du navigateur pour les erreurs CORS