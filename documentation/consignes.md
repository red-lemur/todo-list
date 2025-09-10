# Test technique Full Stack

Le candidat devra réaliser une application comportant :
- une API RESTful permettant de gérer une liste de tâches à faire ;
- une application permettant d'intéragir avec l'API.

Le code produit devra être publié sur un repository Github ou un équivalent.

## Partie 1 : l'API

A partir de cette API, on doit pouvoir :
- récupérer la liste de toutes les tâches ;
- récupérer les tâches à effectuer ;
- récupérer une tâche par son identifiant ;
- ajouter des tâches ;
- changer le statut d'une tâche.

Une tâche est représentée par les propriétés suivantes :
- id : l'identifiant de la tâche ;
- label : l'intitulé de la tâche ;
- description : une petite description de la tâche ;
- completed : indique si la tâche est effectuée ou non.

> Il n'est pas nécessaire d'avoir une base de données. On pourra charger une liste de données en mémoire.

### Stack techniques à utiliser :
- Spring Boot ;
- Maven ;
- Java ou Kotlin au choix.

### Points d'attention :
- tests unitaires ;
- respect des bonnes pratiques REST ;
- s\'il reste du temps, ne pas hésiter à proposer de nouvelles fonctionnalités (documentation, pagination, sécurité...).

## Partie 2 : l'application

L'application devra donner la possibilité :
- d\'afficher la liste des tâches ;
- d\'afficher le détail d'une tâche ;
- de filtrer les tâches en fonction de leur statut ;
- de modifier le statut d'une tâche.

### Stack techniques à utiliser :
- Angular ;
- possibilité d'utiliser des librairies de composants ou de style type Material, Taiga UI, Tailwind ou autre ;
- possibilité d'utiliser une librairie de gestion d'état type NgRx ou autre.

### Points d'attention :
- l'ensemble des routes de l'API doit être exploité dans l'application ;
- tests unitaires ;
- Clean Code.