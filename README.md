# Architecture Microservices - Application e-commerce

## 1. Présentation générale

Ce projet implémente une application e-commerce basée sur une architecture microservices.
Chaque service est indépendant, expose une API REST et possède sa logique métier propre.

Les services communiquent exclusivement via HTTP (JSON)


## 2. Lancement du site (sous Windows)

Ouvrez docker deskstop
Dans le dossier "website-e-commerce", lancez l'éxecutable "start-containers.exe"
Cela montera automatiquement le site, cela peut prendre quelques minutes.
Le processus est terminé quand vous verrez "Démarrage terminé" ainsi que de URL.
Copiez l'url du site: http://localhost:8081 et entrez le dans la barre de recherche
de votre navigateur. Il ne trouvera pas tout de suite, laissez lui le temps de cherchez
puis actualisé la page. Au bout d'un moment le site devrait s'afficher.


## 3. 1er utilisation du site

Au chargement de la page web, vous pouvez créer un compte, pour ensuite vous identifier.
Cela va permettre de garder en mémoire votre panier et votre solde. 
Choisissez les produits que vous voulez avec leur quantité. 
Ajoutez de l'argent à votre solde, et allez voir votre panier.
Vous pouvez, à ce moment, encore modifier la quantité ou retirer un produit totalement, 
ce qui modifiera le total en direct. Payez votre panier, vos transactions s'afficheront 
en bas du site.

Il est possible de vous connecter en mode admin (user: admin@admin.com, password: admin)
Ce compte vous permet d'avoir la vision d'un utilisateur mais avec la possibilité de 
modifier les stocks. 
La gestion de la rupture de stock a été ajouté, dès qu'un stock arrive à 0, il devient 
en rupture de stock.


## 4. Liste des services

|----------------------------------------------------------------------------|
|     Service       | Port | Responsabilité                                  |
|----------------------------------------------------------------------------|
| catalogue-service | 8081 | Gestion des produits et du stock                |
| panier-service    | 8082 | Gestion du panier utilisateur                   |
| paiement-service  | 8083 | Gestion du solde et des paiements               |
| stats-service     | 8084 | Enregistrement des transactions et statistiques |
| auth-service      | 8085 | Authentification des utilisateurs               |
| Eureka	    | 8761 | Monitoring des microservices		     |
|----------------------------------------------------------------------------|
 

## 5. Responsabilité par service
### 5.1 Catalogue Service

	- Stocke la listes des produits
	- Fournit les informations produit (nom, prix, image, stock)
	- Gère les opérations de stock (augmentation, diminution)
	- Sert les images statiques des produits

	Endpoint principaux:
	- GET /api/catalogue/produits
	- PUT /api/catalogue/produits/{id}/diminuer-stock

### 5.2 Panier Service

	- Un panier par utilisateur
	- Ajout, modification et suppression de produits
	- Calcul du total du panier
	Aucune logique de paiement ou de stock

### 5.3  Paiement Service

	- Un compte par utilisateur avec un solde
	- Crédit du compte
	- Débit lors d'un achat
	- Validation d'un paiement

	Après chaque opération :
	- Notification du service statistiques en mode asynchrone grâce à RabbitMQ
	- Mise à jour du stock via le catalogue

### 5.4 Stats Service

	- Enregistre les transactions (ACHAT / CREDIT)
	- Fournit des statistiques globales des utilisateurs
	- Aucune interaction directe avec le frontend pour les écritures

### 5.5 Auth Service

	- Création de compte
	- Authentification utilisateur
	- Retourne les informations utilisateur nécessaires au frontend
	- Gestion d'un rôle administrateur simple

### 5.6 Eureka

	- Permet le monitoring des microservices
