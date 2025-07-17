# Servicio SOAP - Pokemon

## Descripción General

Este proyecto es un microservicio construido con Spring Boot que expone endpoints SOAP para la integración de datos de Pokémon.
Utiliza clientes Feign para consumir APIs externas REST, PostgreSQL para persistencia y Gradle para automatización de compilación.

## Características

- **Endpoint SOAP para consultas de Pokémon (`PokemonEndpoint`)**
- **Integración con API REST de Pokémon** mediante Feign Client
- **Manejo centralizado de excepciones**
- **Registro de respuestas con JPA**
- **Caché para mejorar el rendimiento**
- **Configuración flexible vía `application.yml` cambiar el hostname si se ejecuta en local o en docker.**
- **Docker = postgres**
- **Local = localhost**



## Principios SOLID
Este proyecto sigue los principios SOLID para garantizar un código mantenible, escalable y robusto:
- S: Responsabilidad Única — Cada clase tiene una responsabilidad clara (por ejemplo, endpoints, servicios, repositorios)

## Tecnologías

- Java 21+
- Spring Boot
- Spring Web Services (SOAP)
- Feign
- PostgreSQL
- Gradle

## Estructura de Directorios

```
src/main/java/com/bankaya/soap_service/   # Código fuente
src/main/resources/                       # Configuraciones y archivos XSD
src/test/java/com/bankaya/soap_service/   # Pruebas unitarias e integrales
```

## Diagrama de Componentes
<img width="613" height="461" alt="Image" src="https://github.com/user-attachments/assets/ebafd161-c68d-4bdf-8c89-1e9e84a5a16f" />

## Primeros Pasos

### Prerrequisitos

- Java 21+
- Docker (para la base de datos)
- Gradle

### Instalación

1. **Clona el repositorio**

    ```bash
    git clone https://github.com/JulioJohan/pokemon-api-soap.git
    cd pokemon-api-soap
    ```

2. **Levanta PostgreSQL con Docker**

    ```bash
    docker-compose up -d
    ```

3. **Configura la aplicación**

   Edita `src/main/resources/application.yml` con los parámetros de base de datos y API.

4. **Compila y ejecuta**

    ```bash
    ./gradlew build
    ./gradlew bootRun
    ```

## Pruebas

Ejecuta todas las pruebas:

```bash
./gradlew test
```

## Uso

- Accede al WSDL en: [http://localhost:8080/ws/pokemon.wsdl](http://localhost:8080/ws/pokemon.wsdl)
- Ejemplo de solicitud/respuesta SOAP: ver `src/main/resources/pokemon.xsd`
