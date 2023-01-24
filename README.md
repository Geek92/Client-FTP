# Tree FTP

developpement d'un client ftp en java.
Patrick Frank Tchossiewe
23/01/2023

## introduction

ce logiciel nous permets grace à la simulation des commandes [USER, PASS, LIST, CWD, CDUP](https://fr.wikipedia.org/wiki/Liste_des_commandes_ftp) de la [RFC 959](http://abcdrfc.free.fr/rfc-vf/pdf/rfc959.pdf) (_File Transfer Protocol_), d'afficher l'arborescence du système de fichiers d'un serveur passé en paramètre.

## Architecture
le projet est composé de deux classes:
- une classe **ServerConnection** qui contient les méthodes permettants de se connecter au serveur et  d'obtenir les différentes informations.
- une classe **TreeFTP** qui est le point de departs de notre application.

## Fonctionnalités implementées

 l'application  :
 -  permets de se connecter à un serveur distant passé en parametre
 - supporte le mode passif
- lister le contenu d'un repertoire distant

l'application ne permets pas d'afficher l'arborescence d'un système de fichiers.

##comment compiler et executer l'application

pour compiler le programme il faut:

- cloner le dépôt git
- acceder au repertoire
- executer la commande mvn clean (optionnel)
- executer la commande mvn package

pour lancer l'application il faut:
- acceder au repertoire src/TreeFTP/target
- executer la commamde  java -jar Tree-FTP-1.0.jar adresse_du_serveur nom_d_utilisateur mot_de_passe
