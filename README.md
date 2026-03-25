# NovaBank

## Descripción del proyecto
NovaBank es un programa de gestión bancario. La versión actual del proyecto, permite al usuario interactuar con los
métodos básicos de un sistema bancario en memoria.

Sus funcionalidades actuales son las siguientes:
- Registro de clientes, cuentas y movimientos.
- Ingreso, retiro y transacciones.
- Consultas y historial de movimientos.

## Tecnologías Implementadas
- **JDK 17**
- **Apache Maven**
- **JUnit5**
- **Mokito**
- **GIT y GitHub**

## Requisitos del sistema
Para ejecutar el proyecto, necesitarás tener:
- **Java Development Kit (JDK) 17** o superior.
- **Apache Maven 3.6** o superior.

## Estructura del proyecto
Si nos vamos al paquete principal (main/java/org/) el proyecto está estructurado de la siguiente manera:
- **model/**: Donde se encuentran todos los modelos del proyecto.
- **service/**: Es el que contine la lógica de los métodos de cada clase.
- **repositorio/**: Es la "Base de datos" del proyecto. Se encarga de conectar y guardar temporalmente todos los datos.
- **main/**: La clase principal del proyecto. Es la encargada de implementar todos los métodos del proyecto.
- **java/test/service/**: Este es el paquete donde están alojadas las pruebas.
- **java/resources/**: Es donde está alojado el archivo Schema.sql y el diagrama Entidad-Relación.

### Compilar el programa
Para compilar el programa:
```bash
mvn clean compile
```

### Ejecutar el programa
Para ejecutar el programa:
```bash
mvn exec:java
```

### Ejecutar los tests
Ejecuta los test con el siguiente comando:
```bash
mvn test
```
