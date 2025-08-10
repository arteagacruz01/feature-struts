# Persona CRUD Struts 2 + Oracle

Proyecto base con Struts 2 que implementa CRUD de `PERSONA` usando **procedimientos del paquete `PKG_PERSONA`** para crear, actualizar, consultar por id y eliminar.

## Requisitos
- JDK 8+
- Maven 3.8+
- Oracle reachable (12c+). Crea usuario y privilegios con tu DBA.

## Preparación de BD
Ejecuta `sql/create_persona.sql` en el **esquema** donde se desplegará la app.

## Configuración de conexión
Edita `src/main/resources/db.properties`:
```
db.url=jdbc:oracle:thin:@//localhost:1521/ORCLPDB1
db.user=GORAPR
db.password=tu_password
```

## Compilar y ejecutar
```
mvn clean package
```
Despliega el `target/persona-crud-struts2.war` en Tomcat/Jetty/Payara.

## Rutas
- `GET /persona`      → listado
- `GET /persona-create-input` → formulario alta
- `POST /persona-create`      → inserta (SP)
- `GET /persona-edit-input?idPersona={id}` → cargar para editar (SP select)
- `POST /persona-update`      → actualiza (SP)
- `GET /persona-delete?idPersona={id}`     → elimina (SP)

## Notas
- El listado general usa `SELECT` directo; el resto de operaciones usan SP del paquete.
- En los métodos DAO se hace `cn.commit()` intencional para simplificar pruebas.
  En productivo considera manejar transacciones en una capa de servicios y/o usar JTA.
