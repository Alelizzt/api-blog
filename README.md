## Blog System

Api REST para administrar un sistema de blog

## Despliegue del proyecto

```bash
git clone https://github.com/Alelizzt/api-blog.git
cd api-blog

```
### Servicio de base de datos
Para lograr ejecutar el proyecto con éxito, se proporciona un contenedor con la base de datos Mysql inicializada, por el contrario, si no desea utilizar contenedores, puede crear usted mismo la base de datos con el nombre "**blog_system**" y configurar acorde al fichero **application.properties** .

```bash
docker-compose -f docker-compose.yml up -d
```

### Ejecución del proyecto
```bash
cd blog-system
mvn spring-boot:run
```

## Documentación del API
Una vez ejecutado el proyecto, revisar: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


## Importante (Usuario administrador)
El proyecto no contempla la creación de usuarios administradores mediante la api, para resolver esto se ha facilitado un script sql para ejecutar una vez arrancado el proyecto springboot, éste creara un usuario "admin" con la credencial por defecto "password". Si desea utilizar otras credenciales, se facilita una utilidad en :

```
api-blog/blog-system/src/main/java/com/system/blog/util

/PasswordEncoderGenerator.java

```

Puede realizar la inserción con su administrador de base de datos favorito, en caso de utilizar docker utiliza las credenciales de **application.properties** junto a los siguientes comandos.

```bash
docker cp admin-config.sql mysql-blog-server:/admin-config.sql
docker exec -it mysql-blog-server mysql -u root -p
```

## Roadmap

- [ ] Integrar un FrontEnd sencillo.
- [ ] Refactorización del código.
