# Falabella stock

Falabella stock aplicación gestión productos

## Installation

Para la instalación debe tener docker configurado luego ejecutar el siguiente comando en la carpeta raíz del proyecto

Para windows
```bash
$ execute-stock.bat

```
Para linux/mac
```bash
$ ./execute-stock.sh
```

## Arquitectura backend

- Base de datos relacional postgresql
- Framework spring mvc
- Modelo de datos [products] -< [images]
- Para las pruebas unitarias se empleó JUnit/Mockito
- Se empleo Gradle como herramienta de compilación

## Arquitectura front
- Framework angular en su versión 10
- Se siguió la guía de estilos de angular
- Framework pruebas unitarias karma jasmine
- Framework css bootstrap
- Preprocesador de estilos Sass
