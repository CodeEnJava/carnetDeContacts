📒 Carnet de Contacts Sécurisé — Java 17

Projet pédagogique complet en Java visant à construire un carnet de contacts sécurisé, avec chiffrement des données, sauvegarde sur disque, CRUD et interfaces utilisateur.

Ce dépôt accompagne une série vidéo disponible sur YouTube (chaîne CodeEnJava).

🎯 Objectifs pédagogiques

Ce projet permet d’apprendre à :

Structurer un projet Java proprement

Implémenter un système de chiffrement (César, XOR, etc.)

Manipuler des fichiers (lecture / écriture sécurisée)

Mettre en place un CRUD complet

Séparer logique métier / services / IHM

Créer une interface console

Créer une interface graphique avec Swing

Niveau visé : débutant → intermédiaire (Bac+2)

🧱 Architecture du projet
src/
 ├── cipher/          → Interfaces et implémentations de chiffrement
 ├── model/           → Contact, Adresse
 ├── service/         → ContactService (CRUD)
 ├── persistence/     → Sauvegarde / lecture disque
 ├── ui/console/      → Version console
 ├── ui/swing/        → Version graphique Swing
 └── Main.java

🔐 Fonctionnalités
✅ Chiffrement

Interface Cipher

Implémentation César (première version)

Extensions possibles : XOR, Vigenère, etc.

✅ Gestion des contacts (CRUD)

Ajouter un contact

Lire tous les contacts

Modifier un contact

Supprimer un contact

✅ Persistance

Sauvegarde des données chiffrées sur disque

Lecture + reconstruction des objets

✅ Interfaces

Menu console interactif

Interface graphique Swing

📦 Dépendances

Le projet utilise les bibliothèques suivantes :

jIO-25.06.jar

jStructure-25.06.02.jar

jClearScreen.jar

👉 À placer dans un dossier /lib et à ajouter au classpath.

▶️ Lancer le projet
Prérequis

Java 17+

IDE (IntelliJ, Eclipse ou VS Code recommandé)

Étapes

Cloner le dépôt :

git clone https://github.com/TON_COMPTE/carnet-contacts-java.git


Importer dans ton IDE

Ajouter les JAR dans le classpath

Lancer Main.java

🧪 Exemple console
1 - Ajouter un contact
2 - Afficher les contacts
3 - Modifier un contact
4 - Supprimer un contact
0 - Quitter

🚧 Évolutions possibles

Tests unitaires (JUnit)

Hash + salt

Injection de dépendances

Mini container maison

Export CSV

Recherche multicritère

UI améliorée

👨‍🏫 Usage pédagogique

Ce projet peut servir :

✅ TP fil rouge
✅ support de formation
✅ démonstration d’architecture
✅ base de projet étudiant

📜 Licence

Projet libre à usage pédagogique.

✨ Auteur

CodeEnJava
Formation Java orientée projet réel.
