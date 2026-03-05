# 🔐 Carnet de Contacts Sécurisé — Module de Cryptographie Java

![Java](https://img.shields.io/badge/Java-17+-orange)
![Architecture](https://img.shields.io/badge/Architecture-SOLID-blue)
![Design Pattern](https://img.shields.io/badge/Pattern-Strategy-green)
![Status](https://img.shields.io/badge/Status-Work%20in%20Progress-yellow)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

Projet pédagogique illustrant la conception d’un **module de cryptographie extensible en Java** dans le cadre d’un **carnet de contacts sécurisé**.

---

# 📑 Sommaire

- [Présentation](#présentation)
- [Architecture du projet](#architecture-du-projet)
- [Interface Cipher](#interface-cipher)
- [Chiffrement par série arithmétique](#chiffrement-par-série-arithmétique)
- [Gestion des clés](#gestion-des-clés)
- [Pattern Strategy pour l'encodage](#pattern-strategy-pour-lencodage)
- [Diagramme UML](#diagramme-uml)
- [Exemple d'utilisation](#exemple-dutilisation)
- [Preuve de réversibilité](#preuve-de-réversibilité)
- [Comparaison des algorithmes](#comparaison-des-algorithmes)
- [Objectifs pédagogiques](#objectifs-pédagogiques)
- [Évolutions possibles](#évolutions-possibles)
- [Public cible](#public-cible)
- [Licence](#licence)

---

# 📌 Présentation

Ce module permet d’expérimenter plusieurs **algorithmes de chiffrement classiques** :

- Chiffrement de **César**
- Chiffrement de **Vigenère**
- Chiffrement basé sur une **série arithmétique**

Le projet met l’accent sur :

- la **qualité de l’architecture**
- la **réutilisabilité du code**
- la **séparation des responsabilités**

---

# 🧱 Architecture du projet

Le cœur du système repose sur une interface commune à tous les algorithmes :

```java
Cipher
```

Les implémentations concrètes utilisent :

une stratégie d'encodage

une clé spécifique à l’algorithme


Schéma d’architecture

```mermaid
flowchart LR

A[Application] --> B[Cipher Interface]

B --> C[CesarCipherService]
B --> D[VigenereCipherService]
B --> E[ArithmeticSerieCipherService]

E --> F[ArithmeticKeys]

C --> G[EncodingStrategy]
D --> G
E --> G

G --> H[AsciiPrintableEncoding]
G --> I[UnicodeEncoding]
G --> J[CustomAlphabetEncoding]
```
🔐 Interface Cipher

L’interface définit le contrat commun pour tous les algorithmes.
```java
public interface Cipher {
    String encrypt(String message, Object... key);
    String decrypt(String message, Object... key);
}
```

L’utilisation d’un paramètre variadique (Object... key) permet :

✔ différents types de clés
✔ plusieurs paramètres de chiffrement
✔ une architecture extensible

🧠 Chiffrement par série arithmétique

La série arithmétique utilisée :
```math

Un = a+n×d
```

où :

| Paramètre | Description           |
| --------- | --------------------- |
| a         | offset initial        |
| d         | raison de la série    |
| n         | position du caractère |


Transformation des caractères
Chiffrement
```math

Cn = (kn + Un )mod N
```
Déchiffrement
```math
kn =(Cn − Un) mod N
```
Chaque caractère subit un décalage dépendant de sa position.

🔑 Gestion des clés

La classe ArithmeticKeys encapsule le couple (a,d) :

 + immutabilité

 + validation mathématique

 + messages d’erreurs explicites

⚠️ Cas interdit :
```java
if(Math.floorMod(a, domaineSize)==0 && d==0)
```

🧩 Pattern Strategy pour l'encodage

Le projet utilise le pattern Strategy pour gérer différents encodages :

  + ASCII

  + Unicode

  + Alphabet personnalisé

Permet de découpler l’algorithme de chiffrement du domaine des caractères.

📊 Diagramme UML
```mermaid
classDiagram

class Cipher {
    <<interface>>
    +encrypt(message, key)
    +decrypt(message, key)
}

class ArithmeticSerieCipherService {
    -long a
    -long d
    -EncodingStrategy encodingStrategy
    +encrypt()
    +decrypt()
}

class ArithmeticKeys {
    -long a
    -long d
    +getA()
    +getD()
}

class EncodingStrategy {
    <<interface>>
    +toIndex()
    +toChar()
    +normalize()
    +isMessageValid()
}

class UnicodeEncoding
class AsciiPrintableEncoding
class CustomAlphabetEncoding

Cipher <|.. ArithmeticSerieCipherService
EncodingStrategy <|.. UnicodeEncoding
EncodingStrategy <|.. AsciiPrintableEncoding
EncodingStrategy <|.. CustomAlphabetEncoding

ArithmeticSerieCipherService --> EncodingStrategy
ArithmeticSerieCipherService --> ArithmeticKeys

```

💻 Exemple d'utilisation

```java
EncodingStrategy encoding = new UnicodeEncoding();

ArithmeticKeys keys = new ArithmeticKeys(5000000, 1000, encoding.domaineSize());

Cipher cipher = new ArithmeticSerieCipherService(encoding);

String message = "Bonjour et bienvenue sur ma chaine CodeEnJava";

String encrypted = cipher.encrypt(message, keys);
String decrypted = cipher.decrypt(encrypted, keys);

System.out.println(message.equals(decrypted)); // true
```

🧠 Preuve de réversibilité

Chiffrement :
```Math

Cn = (kn + Un ) mod N

```

Déchiffrement :

```Math

kn = (Cn − Un ) mod N
```
Démonstration :
```Math

kn = ((kn + Un) − Un ) mod N = kn
	​
```

Diagramme :

```mermaid
flowchart LR
A[Caractere original] --> B[Index k]
B --> C[Calcul serie arithmetique]
C --> D[Application du modulo N]
D --> E[Index chiffre]
E --> F[Caractere chiffre]

F --> G[Soustraction serie arithmetique]
G --> H[Application du modulo N]
H --> I[Index original]
I --> J[Caractere original]
```

🚀 Comparaison des algorithmes
César

```mermaid
flowchart LR
A[Message clair] --> B[+ a]
B --> C[Message chiffré]
C --> D[- a]
D --> E[Message original]
```

Vigenère

```mermaid
flowchart LR
A[Message clair] --> B[+ clé cyclique]
B --> C[Message chiffré]
C --> D[- clé cyclique]
D --> E[Message original]
```

Série Arithmétique

```mermaid
flowchart LR
A[Message clair] --> B[Ajout serie arithmetique]
B --> C[Message chiffre]
C --> D[Soustraction serie arithmetique]
D --> E[Message original]
```

| Algorithme         | Décalage      | Dépendance           | Pédagogie |
| ------------------ | ------------- | -------------------- | --------- |
| César              | Constant      | Aucune               | Facile    |
| Vigenère           | Variable      | Clé répétée          | Moyenne   |
| Série Arithmétique | Positionnelle | Position + paramètre | Élevée    |


🎯 Objectifs pédagogiques

Concevoir une architecture modulaire

## Fonctionnalités

- Appliquer les principes SOLID
- Utiliser le pattern Strategy
- Implémenter des algorithmes de chiffrement
- Valider mathématiquement les transformations


🚀 Évolutions possibles

## Fonctionnalités

- Nouveaux algorithmes de chiffrement
- Analyse de la bijectivité
- Validation mathématique plus complète
- Intégration totale dans le carnet de contacts sécurisé

👨‍💻 Public cible

## Fonctionnalités
- Étudiants en informatique
- Développeurs Java débutants
- Personnes en reconversion vers le développement
- Formateurs en programmation

📚 Licence

Projet pédagogique — utilisation libre pour apprentissage et démonstration.
