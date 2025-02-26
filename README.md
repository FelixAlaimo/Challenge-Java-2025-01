# Challenge-Java-2025-01

# De que consiste la app
-	Spring boot app en java 17:
	--> No elegí esta tecnología por ninguna razon en particular, simplemente el IDE que utilicé para este proyecto (Spring Tool Suite 4) ya se encontraba configurado con Java 17. El otro IDE con el que me encuentro mas familiarizado (Eclipse) se encontraba con Java 8 y me pareció mejor usar la version mas actual que tenía disponible en el momento.
-	Implementé Redis como base de datos en memoria para el manejo del cache:
	--> Elegí esta tecnología ya que se usa frecuentemente y tiene una excelente performance.
-	MySql como base persistente:
	--> Elegí esta base de datos por su simplicidad de uso y facilidad de conexión.
-	App containerizada con Docker
-	Se consultan los endpoints a través de Postman. La app cuenta con documentacion Swagger para informacion mas detallada.

# Pre-requisitos / supuestos:
	1-> tener clonado el repo
	2-> Crear una base de datos "ventasDB" en MySQL
	3-> Tener Docker up & running
	4-> En caso de levantar la app desde un IDE, tener un servidor de redis levantado en local.

# Como se compila el codigo fuente:
Estando parados en el root del repo (donde se encuentra el archivo "Dockerfile" y "docker-compose.yml"):
	1-> docker compose down
	2-> docker build --no-cache -t ventas-app:latest .
	3-> docker compose create

# Cómo se ejecuta ejecuta este compilado
	1-> Desde docker, levantar el contenedor "mysql-db" primero (ver en su consola "ready for connections.")
	2-> Startear "ventas-app".

# Cómo puede probarse la aplicación:
	-- Se le puede pegar desde postman o similares, la colección de postman para utilizar presente en el base del repo ("Java Challenge.postman_collection.json"). Ver http://localhost:{port}/swagger-ui/index.htm para informacion mas detallada.