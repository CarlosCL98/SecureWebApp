# Secure Web App

Esta es una aplicación distribuida segura en todos sus frentes. Esta permite un acceso seguro desde el browser a la aplicación, garantizando autenticación, autorización e integridad de usuarios. Se comunica con otro servicio de manera remota y también garantiza los criterios anteriores entre los servicios. Nadie puede invocar los servicios si no está autorizado.

## Documentación

### Escalabilidad de la Aplicación

Actualmente la aplicación no es escalable, pues para añadir más funcionalidades u otras conexiones a servidores es necesario modificar esta aplicación principal pues es quien contiene el formulario y quien hace el llamado al servidor de la fecha.

Para escalar la arquitectura de seguridad e incorporar nuevos servicios, extendería la arquitectura a una de microservicios, de manera que esta aplicación se comunique mediante APIs a otros servicios y no se tenga que modificar la funcionalidad de la aplicación principal, por el contrario sólo se tendrían que hacer las conexiones para consumir dichos servicios. 

### Informe

El informe y la arquitectura se pueden ver en el siguiente documento [Arquitectura](InformeDeArquitectura.pdf)

## Creador

*Carlos Andrés Medina Rivas*

*Estudiante de Ingeniería de Sistemas de la Escuela Colombiana de Ingeniería Julio Garavito.*

## Licencia

> GNU GENERAL PUBLIC LICENSE - Version 3, 29 June 2007

Para ver más, leer el archivo [LICENSE.md](LICENSE.md) ubicado en la raíz de este repositorio.