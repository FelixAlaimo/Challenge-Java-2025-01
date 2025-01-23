# Challenge-Java-2025-01

Para levantar la app:
Pre-requisito: tener clonado el repo (o la carpeta target con el jar adentro)
1-> Abrir la terminar en el root del proyecto
2-> correr el comando "docker compose down" para limpiar por si ya se corrio antes.
3-> correr el comando "docker build --no-cache -t ventas-app:latest ."
4-> correr el comando "docker compose create" para crear los conenedores.
5-> levantar en docker el contenedor de mysql-db.
6-> Una vez que el contenedor este "ready for connections" (darle 15 segundos o monitorear la consola), startear el contenedor "ventas-app"