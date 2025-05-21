# ChaTop-estate-back-end

## Description

API de l'application Estate, permettant de mettre en relation des utilisateurs pour de la location de biens immobiliers, avec une nécessité d'avoir un compte et d'être connecté afin d'accéder aux fonctionnalités de l'application autre que la page de connexion ou la page de création d'un compte.

## Installation du projet 
 ### 1. Cloner le dépôt :
  ```git clone https://github.com/Hugo-Robert-1/ChaTop-estate-back-end.git ```
 ### 2. Se placer dans le dossier du projet :
  ```cd ChaTop-estate-back-end```
 ### 3. Installer les dépendances du projet :
  - Via Windows, powershell : `` .\mvnw clean install ``
  - Via Linux/MacOs, bash ;  `` ./mvnw clean install ``


## Installer une base de données MySQL en local
 1. **Installer MySQL** : Assurez-vous que MySQL est installé sur votre machine. Si ce n'est pas le cas, vous pouvez le télécharger et l'installer via [MySQL Downloads](https://dev.mysql.com/downloads/) en suivant le programme d'installation.

 2. **Créer la base de données** : Connectez-vous à MySQL et créez la base de données `chatop_estate`.

    ```sql
    CREATE DATABASE chatop_estate;
    ```
 
 3. **Mise en place des tables de la base de données** : Pour ce projet, les tables seront créées automatiquement au démarage de l'application 

## Mise en place des variables d'environnement

Plusieurs variables d'environnement sont à définir :
 - Connexion à une base de donnée MySQL : DB_USER, DB_PASSWORD, DB_URL
 - Port de connexion à l'API : SERVER_PORT
 - Chemin absolu du dossier où seront stocker les images en local : FILE_SAVE_PATH ( exemple : C:/Users/hugo/uploads/)
 - URL publique permettant d’accéder aux fichiers uploadés depuis l’extérieur via HTTP : FILE_BASE_URL ( exemple : http://localhost:3001/uploads/ pour un port 3001)
 - Temps de validité en seconde d'un token JWT pour l'authentification :  JWT_EXPIRATION_TIME
 
La mise en place de ces variables peuvent être faites de différentes manières : 
 - Pour un usage **strictement local** (pour des raisons évidentes de sécurité), il est possible de directement remplacer dans le fichier à l'adresse suivante : src\main\resources\application.properties les propriétés ${...} par les valeurs souhaitées.
 - Via votre éditeur de code (Eclipse ou Spring tool suite par exemple), il est possible de définir directement les variables d'environnement qui seront utilisées lorsqu'un projet est lancé. Veuillez vous rapprocher de la documentation de votre éditeur pour savoir comment les mettre en place.
 - Pour un usage en production, utiliser les variables d'environnement système. Pour cela, créer un fichier .env à la racine du projet, contenant les variables d'environnement à définir. En fonction de votre système d'exploitation, utiliser le script suivant pour charger les variables :
    - Sous Windows PowerShell :
      ```powershell Get-Content .env | Where-Object { $_ -match '=' -and $_ -notmatch '^\s*#' -and $_.Trim() -ne '' } | ForEach-Object { $name, $value = $_ -split '=', 2 [System.Environment]::SetEnvironmentVariable($name.Trim(), $value.Trim(), "Process") } ```

    - Sous Linux/macOS :
     ``export $(cat .env | xargs)``


## Générer le couple de clés RSA 
 L'authentification est gérée via l'utilisation d'une clé publique et une clé privée respectant le chiffrement RSA2048. 
 - La clée publique doit avoir comme nom de fichier public.pem et la clée privée doit avoir comme nom de fichier private.pem.
 - Elles doivent être stockées dans le dossier situé à l'addresse : src/main/resources/keys

 Voici un script permettant de générer ces deux clées au bon format via openssl : \
 ``openssl genpkey -algorithm RSA -out private.pem -pkeyopt rsa_keygen_bits:2048``\
 ``openssl pkey -in private.pem -pubout -out public.pem``

## Démarrer le projet
 Dans le dossier du projet :
 - Via Windows, powershell : `` .\mvnw spring-boot:run ``
 - Via Linux/MacOs, bash ;  `` ./mvnw spring-boot:run ``
## Documentation Swagger de l'API 
 L'API a été documenté avec Swagger, la documentation est accessible à l'adresse suivante : http://localhost:3001/swagger-ui/index.html (3001 ou le port que vous avez choisi dans votre variable d'environnement). \
 Pour intéragir avec certaines routes, il sera nécessaire d'être authentifié, il faut donc récupérer un token jwt valide.