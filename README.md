# GeoRisk Habitat

> Assurance habitation intelligente : tarifer, anticiper et apprendre du risque d'inondation.

Mini-projet **Architecture Web Distribuée (AWD)** · ESPRIT · Année universitaire 2026-2027

## Membres du groupe

| Étudiant | Nom | Microservice |
|---|---|---|
| Étudiant 1 | _à compléter_ | MS1 `property-service` |
| Étudiant 2 | _à compléter_ | MS2 `policy-service` |
| Étudiant 3 | _à compléter_ | MS3 `pricing-service` |
| Étudiant 4 | _à compléter_ | MS4 `claims-service` |
| Groupe | tous les membres | MS5 `risk-engine-service` (microservice commun) |

**Suivi des tâches :** _lien du tableau GitHub Projects à ajouter ici_

## Le sujet

**Problème.** L'assurance habitation classique fixe sa prime selon de grandes catégories (type de logement, région). Le risque réel d'une zone n'est pas mesuré, et l'assureur n'apprend le sinistre qu'après coup : la tarification est peu précise et aucune prévention n'est possible.

**Solution.** GeoRisk Habitat repose sur trois idées :

- **Tarifer** : la prime dépend d'un score de risque de la zone et des caractéristiques du bien.
- **Anticiper** : un moteur de risque lit les prévisions météo, calcule la probabilité d'inondation par zone et émet une alerte ; les contrats concernés sont signalés et des sinistres sont pré-ouverts.
- **Apprendre** : chaque sinistre réel ou fausse alerte est enregistré comme résultat pour évaluer le score.

**Périmètre de la V1 :** un seul aléa (l'inondation) et 10 zones de démonstration.

**Acteurs :** propriétaire, assureur, expert sinistre, administrateur.

## Architecture

Une architecture microservices : un microservice par étudiant avec sa propre base, un microservice commun, et une infrastructure partagée.

```
Frontend (Angular)
      │
API Gateway (Spring Cloud Gateway)  ── Keycloak (authentification, JWT)
      │
 ┌────┴──────────┬──────────────┬──────────────┬────────────────────┐
 MS1 Property   MS2 Policy    MS3 Pricing    MS4 Claims         MS5 RiskEngine
 (H2)           (H2)          (H2)           (MySQL)            (Python, PostgreSQL)

 Infrastructure : Eureka (découverte) · Config Server (configuration) · RabbitMQ (événements) · Docker Compose
```

- **Communication synchrone** : appels REST entre services avec OpenFeign (adresses résolues par Eureka).
- **Communication asynchrone** : événements publiés sur RabbitMQ (par exemple `HazardAlertIssued`, `ClaimSettled`).

Le schéma d'architecture complet est dans `Documentation/Diagrams/`.

## Les microservices

| Service | Responsabilité | Technologies | Base de données | Port |
|---|---|---|---|---|
| `property-service` (MS1) | Propriétaires, biens, zone de risque | Spring Boot | H2 | 8081 |
| `policy-service` (MS2) | Contrats, garanties, alertes sur les contrats | Spring Boot | H2 | 8082 |
| `pricing-service` (MS3) | Produits, coefficients, calcul de prime, devis | Spring Boot | H2 | 8083 |
| `claims-service` (MS4) | Sinistres, expertise, indemnisation simulée | Spring Boot | MySQL | 8084 |
| `risk-engine-service` (MS5) | Zones, météo, score de risque, alertes | Python, FastAPI | PostgreSQL | 8085 |

Infrastructure : `api-gateway` (8080), `eureka-server` (8761), `config-server` (8888), Keycloak (8180), RabbitMQ (5672 / 15672).

## Structure du dépôt

```
GeoRisk-4Actuariat-g6/
├── Backend/                 un projet par microservice + l'infrastructure
│   ├── property-service/
│   ├── policy-service/
│   ├── pricing-service/
│   ├── claims-service/
│   ├── risk-engine-service/
│   ├── api-gateway/
│   ├── eureka-server/
│   └── config-server/
├── Frontend/                application web (Angular)
├── Documentation/
│   ├── Presentation/        support de présentation (séance 4)
│   └── Diagrams/            architecture globale, cas d'utilisation, diagrammes de classes
└── README.md
```

## Lancement

> À compléter au fil de l'avancement du projet (docker-compose prévu en séance 9).

**Prérequis :** Java, Maven, Docker.

Pour lancer un microservice seul (par exemple `property-service`) :

```bash
cd Backend/property-service
./mvnw spring-boot:run
```

La documentation Swagger de chaque service est disponible sur `http://localhost:<port>/swagger-ui/index.html`.

## Organisation Git

- Branches : `main` (version stable), `develop` (intégration), `feature/<service>` (une par microservice).
- Chaque membre travaille dans le dossier de son microservice et commit avec son propre compte.
- Suivi des tâches : tableau GitHub Projects (lien en haut de ce fichier).

## Avancement

- [x] Validation 1 : conception, architecture, structure du dépôt
- [ ] Gateway, Config Server, Eureka et squelettes des services
- [ ] Communication synchrone (OpenFeign)
- [ ] Communication asynchrone (RabbitMQ)
- [ ] Sécurité (Keycloak)
- [ ] Dockerisation et frontend
