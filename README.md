# Challenge-Java-2025-01

# De que consiste la app
-	Spring boot app en java 17
-	H2 como base de datos en memoria
-	MySql como base persistente
-	App containerizada con Docker
-	Se consultan los endpoints a través de Postman

# Consideraciones generales
-	Se cubrieron los 3 puntos principales del challenge EXCEPTO el punto de cubrir el camino mas corto entre 2 puntos.
-	No posee jUnits (pensaba hacerlos rápido al final con pojo tester pero me tope con q no es compatible con java17).
-	No posee interfaz grafica para los requests (se hacen por postman).

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