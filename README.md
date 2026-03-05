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
