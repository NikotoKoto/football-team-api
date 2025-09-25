# Football Team API

## Conception

### Architecture du projet 
Le choix de mon architecture s'est orienté vers une architecture en couche, celle que j'utilise la plupart du temps dans mes projets Spring Boot.
Cela permet de diviser les responsabilités et de rendre le code beaucoup plus clair, stable et maintenable.
- **Controller** : gère les requêtes HTTP et renvoie les réponses en JSON  
- **Service** : contient la logique métier  
- **Repository** : communique avec la base via JPA  
- **Entity** : modèle persistant (tables de la BDD)  
- **DTO** : objets d’échange pour ne pas exposer directement les entités  

### Diagramme de classe 
<img width="727" height="304" alt="Diagramme de classe" src="https://github.com/user-attachments/assets/5459eb66-928c-4a4f-b952-cd6f69f5c287" />

### Diagramme de séquence

#### POST
POST /equipes : permet la création d’une nouvelle équipe 
<img width="830" height="240" alt="Diagramme de sequenceCreate" src="https://github.com/user-attachments/assets/4549b948-896a-4f48-ba0d-2fd1eca289bc" />

#### GET
GET /equipes : permet la récupération paginée des équipes avec leurs joueurs.
<img width="833" height="310" alt="Diagramme de Sequence Get" src="https://github.com/user-attachments/assets/9b4ddf7c-f485-4a48-b813-8aba1ec90811" />

### Choix techniques

- **Spring Boot web** : API rest via @RestController
- **Spring Data JPA** : Liaison entre backend et Bdd
- **PostgreSQL** : Base de donnée robuste
- **Tests unitaires** : Jubit5 et Mockito
- **Swagger**: documentation interactive de l’API
- **Désactivation temporaire de Spring Security pour tester facilement avec Postman**

## Guide d'installation

### Prérequis

- **Docker**
- **Docker Compose**

### Etape d'installation 

#### Cloner le projet 
```
git clone https://github.com/<ton-utilisateur>/football-team-api.git
cd football-team-api
```

#### Lancer la bdd et l'API avec Docker Compose
```
docker-compose up -d
```


## Tests

Les tests unitaires couvrent: 
-	La création, mise à jour, récupération et suppression d’équipes
- La création et la gestion des joueurs par équipe

Lancer les tests: 
```
./mvnw test
```

## Documentation de l'API

### Collection Postman
[![Run in Postman](https://run.pstmn.io/button.svg)](https://.postman.co/workspace/Personal-Workspace~40a62766-5517-4c11-b9cf-d5aef9a1085e/request/29348541-0bd4190b-77ce-4eab-8c50-c40f108b0119?action=share&creator=29348541)

### Swagger UI
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


