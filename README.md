🐾 Mascotas Bello

Sistema web de reporte, búsqueda y geolocalización de mascotas perdidas, encontradas y en adopción

"Estado" (https://img.shields.io/badge/Estado-Anteproyecto-orange)
"Lenguaje" (https://img.shields.io/badge/Lenguaje-Java-blue)
"Framework" (https://img.shields.io/badge/Framework-Vaadin-blue)
"Ubicación" (https://img.shields.io/badge/Ubicación-Bello%2C%20Antioquia-green)
"Versión" (https://img.shields.io/badge/Versión-1.0.0-lightgrey)

Proyecto: Evidencia 2 — Desarrollo Web con Vaadin
Institución: Cesde
Autor: Luis David Romero Chaverra
       David Colorado Gomez
       Thamara Taborda

---

1. Resumen ejecutivo

Mascotas Bello es una propuesta de aplicación web diseñada para centralizar los reportes de mascotas perdidas, encontradas y en adopción en el municipio de Bello, Antioquia.

Actualmente, la información sobre animales perdidos o disponibles para adopción puede encontrarse dispersa en redes sociales, grupos de mensajería y publicaciones de refugios. Esta situación dificulta la búsqueda, reduce la visibilidad de los reportes y complica el contacto entre las personas que buscan una mascota y quienes tienen información sobre ella.

Como solución, se propone desarrollar una aplicación web utilizando Java y Vaadin, que permita publicar reportes con fotografías, descripciones, ubicación geográfica y datos de contacto autorizados. También contará con un mapa interactivo, filtros de búsqueda, registro de avistamientos y un panel administrativo.

El valor principal del sistema es facilitar el reencuentro de las mascotas con sus familias, mejorar la difusión de los casos y brindar mayor visibilidad a los animales que necesitan un hogar.

2. Descripción de la problemática

En el municipio de Bello, la información sobre mascotas perdidas, encontradas y en adopción puede estar distribuida en diferentes plataformas digitales. Las publicaciones pueden perder visibilidad rápidamente y no siempre contienen fotografías claras, ubicaciones precisas o información de contacto suficiente.

Esta dispersión dificulta que los ciudadanos encuentren reportes relevantes, comuniquen avistamientos y conozcan los animales disponibles para adopción.

Por esta razón, se identifica la necesidad de una plataforma centralizada que permita registrar, consultar y administrar los reportes de manera organizada.

Pregunta problema

¿Cómo facilitar la búsqueda, difusión y seguimiento de mascotas perdidas, encontradas y en adopción en Bello, Antioquia, mediante una aplicación web con geolocalización?

3. Objetivos

Objetivo general

Desarrollar una aplicación web utilizando Java y Vaadin que permita registrar, consultar y administrar reportes de mascotas perdidas, encontradas y en adopción en el municipio de Bello, incorporando herramientas de búsqueda y visualización geográfica.

Objetivos específicos

1. Implementar formularios para registrar mascotas con fotografías, descripción, estado, fecha, ubicación y datos de contacto.
2. Integrar un mapa interactivo para visualizar la ubicación de los reportes.
3. Incorporar filtros por barrio, tipo de mascota, tamaño, estado y fecha.
4. Permitir a los usuarios registrar avistamientos relacionados con mascotas reportadas.
5. Desarrollar un panel administrativo para revisar, actualizar y moderar publicaciones.
6. Proponer un sistema de alertas de proximidad para identificar nuevos reportes en zonas cercanas.

4. Alcance del sistema

La aplicación estará orientada a ciudadanos, propietarios de mascotas, personas que encuentren animales, refugios y administradores de la plataforma.

Funcionalidades principales

- Registro e inicio de sesión de usuarios.
- Publicación de mascotas perdidas, encontradas y en adopción.
- Carga de hasta tres fotografías por reporte.
- Registro de descripción, tipo, tamaño, barrio y ubicación.
- Visualización de reportes en un mapa interactivo.
- Búsqueda y filtrado de publicaciones.
- Registro de avistamientos.
- Contacto con la persona responsable del reporte mediante WhatsApp u otro medio autorizado.
- Administración y moderación de publicaciones.
- Posibilidad futura de generar alertas por proximidad.

Las alertas automáticas y otras funciones avanzadas estarán sujetas a su implementación durante el desarrollo.

5. Arquitectura del sistema

La aplicación se organizará en capas para separar la interfaz, la lógica del negocio y el acceso a los datos.

flowchart TD
    U[Usuarios y refugios] --> I[Interfaz web Vaadin]
    A[Administrador] --> I
    I --> S[Capa de servicios]
    S --> M[Gestión de mascotas]
    S --> R[Gestión de reportes]
    S --> V[Gestión de avistamientos]
    S --> B[Búsqueda y filtros]
    M --> D[Acceso a datos]
    R --> D
    V --> D
    B --> D
    D --> DB[(Base de datos)]
    I --> L[Mapa interactivo Leaflet]

Componentes de la arquitectura

- Capa de presentación: contiene las vistas, formularios, menús y tablas desarrollados con Vaadin.
- Capa de servicios: administra las reglas de negocio, validaciones y operaciones del sistema.
- Capa de persistencia: permite guardar y consultar la información en la base de datos.
- Componente geográfico: utiliza Leaflet para mostrar el mapa y los marcadores de ubicación.
- Capa de seguridad: controla los permisos de los usuarios y protege la información personal.

Este diagrama representa la arquitectura propuesta y deberá actualizarse según la implementación final.

6. Módulos principales

Módulo| Descripción| Componentes previstos
Inicio de sesión| Permite el acceso de usuarios registrados.| Formularios Vaadin
Registro de usuarios| Permite registrar nuevos usuarios.| Campos de entrada y validaciones
Reportar mascota| Registra información y fotografías.| "Upload", "ComboBox", "TextArea"
Mapa principal| Muestra las mascotas reportadas.| Leaflet y marcadores
Muro de reportes| Permite consultar publicaciones.| Tarjetas, filtros y botones
Avistamientos| Registra información sobre mascotas vistas.| Formularios y ubicación
Panel administrativo| Permite moderar publicaciones.| "Grid" y formularios
Búsqueda| Filtra los reportes disponibles.| Campos de búsqueda y filtros

7. Mapa y geolocalización

El mapa interactivo permitirá identificar la ubicación de los reportes en Bello, Antioquia.

Los marcadores tendrán una convención visual según el estado de la mascota:

- 🔴 Rojo: mascota perdida.
- 🟢 Verde: mascota encontrada.
- 🔵 Azul: mascota en adopción.

El usuario podrá consultar la información de cada marcador para conocer la descripción y los datos autorizados del reporte.

La integración se plantea mediante Leaflet y un componente compatible con la versión de Vaadin utilizada.

8. Modelo de datos

La base de datos propuesta permitirá organizar la información de usuarios, mascotas, reportes, fotografías y avistamientos.

Tabla: usuarios

Campo| Descripción
"id_usuario"| Clave primaria del usuario
"nombre"| Nombre completo
"correo"| Correo electrónico
"telefono"| Número de contacto
"rol"| Tipo de usuario
"fecha_registro"| Fecha de creación

Tabla: mascotas

Campo| Descripción
"id_mascota"| Clave primaria de la mascota
"nombre"| Nombre de la mascota, si se conoce
"tipo"| Perro, gato u otro
"raza"| Raza, si se conoce
"tamano"| Tamaño de la mascota
"descripcion"| Características físicas
"id_usuario"| Clave foránea del responsable

Tabla: reportes

Campo| Descripción
"id_reporte"| Clave primaria del reporte
"id_mascota"| Clave foránea de la mascota
"id_usuario"| Clave foránea del autor del reporte
"estado"| Perdida, encontrada o en adopción
"barrio"| Barrio donde se reporta
"latitud"| Coordenada geográfica
"longitud"| Coordenada geográfica
"fecha_reporte"| Fecha de publicación
"contacto_publico"| Medio de contacto autorizado
"estado_publicacion"| Pendiente, activo, cerrado o moderado

Tabla: fotos_reporte

Campo| Descripción
"id_foto"| Clave primaria de la fotografía
"id_reporte"| Clave foránea del reporte
"url_foto"| Ubicación del archivo fotográfico

Tabla: avistamientos

Campo| Descripción
"id_avistamiento"| Clave primaria del avistamiento
"id_reporte"| Clave foránea del reporte relacionado
"id_usuario"| Clave foránea del usuario que reporta
"descripcion"| Información del avistamiento
"barrio"| Barrio del avistamiento
"latitud"| Latitud del lugar
"longitud"| Longitud del lugar
"fecha_avistamiento"| Fecha y hora del avistamiento

Relaciones principales

- Un usuario puede registrar varias mascotas.
- Una mascota puede tener varios reportes a lo largo del tiempo.
- Un reporte puede tener varias fotografías.
- Un reporte puede recibir varios avistamientos.
- Un usuario puede registrar varios avistamientos.

9. Stack tecnológico

Tecnología| Función
Java| Lenguaje principal del backend
Vaadin| Desarrollo de la interfaz web
Spring Boot| Configuración y ejecución, si se utiliza
Base de datos relacional| Almacenamiento de la información
Leaflet| Visualización del mapa
Git| Control de versiones
GitHub| Repositorio del proyecto
Maven o Gradle| Gestión de dependencias, según la configuración

Las versiones definitivas deberán registrarse según las herramientas instaladas y la configuración real del proyecto.

No se deben incluir dependencias que todavía no se hayan agregado.

10. Guía de configuración y ejecución

Requisitos previos

- Java compatible con la versión del proyecto.
- Visual Studio Code o un IDE compatible con Java.
- Maven o Gradle, según la configuración.
- Base de datos configurada.
- Git instalado.
- Acceso al repositorio del proyecto.

Pasos de instalación

1. Descargar o clonar el repositorio desde GitHub.
2. Abrir la carpeta del proyecto en Visual Studio Code.
3. Revisar el archivo "pom.xml" si se utiliza Maven.
4. Configurar la conexión a la base de datos.
5. Verificar las dependencias de Vaadin y Leaflet.
6. Ejecutar la aplicación según la configuración del proyecto.
7. Abrir en el navegador la dirección local indicada en la consola.
8. Probar el registro, los reportes, el mapa y las búsquedas.

Las credenciales de la base de datos y otros datos sensibles deben configurarse de forma segura y no deben publicarse en el repositorio.

11. Flujo de trabajo y control de versiones

El proyecto utilizará Git y GitHub para administrar el código fuente, registrar los cambios y facilitar la colaboración.

Flujo de trabajo

1. Analizar los requerimientos.
2. Diseñar la arquitectura y el modelo de datos.
3. Desarrollar los módulos por separado.
4. Probar las funcionalidades implementadas.
5. Registrar los cambios mediante commits.
6. Integrar los avances en la rama principal.
7. Actualizar la documentación.


12. Equipo de trabajo y créditos

Autor principal: Luis David Romero Chaverra
Documento: 1019054366
Institución: Cesde
Evidencia: Desarrollo Web con Vaadin

Integrante| Responsabilidad
Luis David Romero Chaverra| Interfaz principal y documentacion
[Thamara Taborda]| [Registro y administracion de mascotas]
[David Colorado]| [Busqueda, base de datos y geolocalizacion]

13. Licencia

Proyecto académico desarrollado en el contexto de la evidencia de Desarrollo Web con Vaadin de Cesde.

La licencia de distribución queda pendiente de definición por parte del autor o del equipo de trabajo.