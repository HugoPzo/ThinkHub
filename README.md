# ThinkHub - Configuración del entorno de base de datos con Docker 🐳

Este proyecto utiliza una base de datos **MySQL** ejecutándose en un contenedor Docker para asegurar que todos los 
colaboradores trabajen con el mismo entorno.

````markdown


## 🚀 Requisitos previos

Antes de comenzar, asegúrate de tener lo siguiente instalado en tu sistema:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/) (viene incluido con Docker Desktop)

---

## 📦 Levantar el contenedor MySQL

1. Clona el repositorio (si aún no lo has hecho):

   ```bash
   git clone https://github.com/tu-usuario/thinkhub.git
   cd thinkhub
````

2. Levanta el contenedor con Docker Compose:

   ```bash
   docker-compose up -d
   ```

   Esto creará y ejecutará un contenedor de MySQL con los siguientes datos:

    * Usuario: `root`
    * Contraseña: `12345`
    * Base de datos: `thinkhub`
    * Puerto expuesto: `3306`

3. Verifica que el contenedor esté funcionando:

   ```bash
   docker ps
   ```

   Deberías ver un contenedor llamado `thinkhub-mysql` en ejecución.

---

## ⚙️ Configuración para Spring Boot

En tu archivo `application.properties` (ya configurado):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/thinkhub
spring.datasource.username=root
spring.datasource.password=12345
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
```

---

## 🛑 Apagar el contenedor MySQL

Para detener el contenedor sin eliminar los datos:

```bash
docker-compose down
```

> ⚠️ Esto detiene y elimina el contenedor, pero los datos persisten gracias al volumen `mysql_data`.

---

## 🔄 Reiniciar el contenedor

Si quieres apagarlo y luego volverlo a levantar:

```bash
docker-compose down    # Apagar
docker-compose up -d   # Encender nuevamente
```

---

## 🧹 Eliminar todo (contenedor + datos)

Si deseas eliminar completamente el contenedor **y** los datos (útil en caso de errores graves o para reiniciar todo):

```bash
docker-compose down -v
```

---

## 🐳 Ver logs del contenedor

Puedes ver los logs del contenedor con:

```bash
docker logs -f thinkhub-mysql
```

---

## 📁 Estructura recomendada

```
thinkhub/
├── src/
├── docker-compose.yml
├── README.md
└── ...
```

---

## 📬 ¿Dudas o problemas?

Si tienes problemas con Docker, revisa:

* El puerto `3306` no debe estar ocupado por otro MySQL local.
* Asegúrate de que Docker esté corriendo antes de ejecutar los comandos.


---

# Como entrar a la base de datos?

## ✅ Opción 1: Entrar desde la terminal usando Docker

Puedes conectarte al contenedor directamente y abrir el cliente de MySQL:

```bash
docker exec -it thinkhub-mysql mysql -u root -p
```

* Te pedirá la contraseña: escribe `12345`.
* Luego estarás dentro del cliente MySQL, donde puedes ejecutar comandos como:

```sql
SHOW DATABASES;
USE thinkhub;
SHOW TABLES;
```

Para salir: escribe `exit` y presiona Enter.

---

## ✅ Opción 2: Usar una herramienta gráfica (recomendado para desarrollo)

### 🔹 MySQL Workbench / DBeaver / TablePlus

Puedes conectarte con los siguientes datos:

* **Host:** `localhost`
* **Puerto:** `3306`
* **Usuario:** `root`
* **Contraseña:** `12345`
* **Base de datos:** `thinkhub` (opcional al conectarse)

> Asegúrate de que Docker esté corriendo y que el contenedor esté encendido con `docker-compose up -d`.

---

## ✅ Opción 3: Desde la línea de comandos del sistema (si tienes cliente MySQL instalado)

Si tienes MySQL instalado localmente (fuera de Docker), puedes conectarte al contenedor:

```bash
mysql -h 127.0.0.1 -P 3306 -u root -p
```

Luego escribe la contraseña `12345`.

---

## 🧠 Tip adicional

Para ver el nombre exacto del contenedor por si cambiaste el nombre:

```bash
docker ps
```

Y usa ese nombre en el comando `docker exec`.

---
