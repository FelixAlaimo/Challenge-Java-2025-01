# Challenge-Java-2025-01

# De que consiste la app
-	Spring boot app en java 17:
	--> No elegí esta tecnología por ninguna razon en particular, simplemente el IDE que utilicé para este proyecto (Spring Tool Suite 4) ya se encontraba configurado con Java 17. El otro IDE con el que me encuentro mas familiarizado (Eclipse) se encontraba con Java 8 y me pareció mejor usar la version mas actual que tenía disponible en el momento.
-	H2 como base de datos en memoria:
	--> Elegí usar esta base de datos por la consigna que espera que la data que maneja esta base de datos no sea persistente. Los datos persistirán hasta que se baje la app, y al reiniciarse comenzara nuevamente con los defaults.
-	MySql como base persistente:
	--> Elegí esta base de datos por su simplicidad de uso y facilidad de conexión.
-	App containerizada con Docker
-	Se consultan los endpoints a través de Postman

# Consideraciones generales
-	Actualmente la aplicacion cubre los 3 puntos principales del challenge EXCEPTO el requerimiento de calcular el camino mas corto entre 2 puntos. Será trabajado como un NTH al final.
-	Posee mas del 95% de la aplicación testeada. No se encuentra al 100% ya que no se testearon las clases de configuracion (H2RepositoryConfig ni MySQLRepositoryConfig).

# Pre-requisitos / supuestos:
	1-> tener clonado el repo
	2-> Crear una base de datos "ventasDB" en MySQL
	3-> Tener Docker up & running

# Como se compila el codigo fuente:
Estando parados en el root del repo (donde se encuentra el archivo "Dockerfile" y "docker-compose.yml"):
	1-> docker compose down
	2-> docker build --no-cache -t ventas-app:latest .
	3-> docker compose create

# Cómo se ejecuta ejecuta este compilado
	1-> Desde docker, levantar el contenedor "mysql-db" primero (ver en su consola "ready for connections.")
	2-> Startear "ventas-app".

# Cómo puede probarse la aplicación:
	-- Se le puede pegar desde postman o similares, la colección de postman para utilizar presente en el base del repo ("Java Challenge.postman_collection.json").