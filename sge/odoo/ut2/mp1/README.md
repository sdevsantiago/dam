# Despliegue de Odoo con Docker Compose

## Prerrequisitos

- Docker instalado
- Docker Compose instalado
```bash
sudo pacman -S docker docker-compose
```

## Pasos de Configuración

1. **Crear el archivo [docker-compose.yml](./docker-compose.yml)**

2. **Desplegar el stack:**
```bash
docker-compose up -d
```
![](img/docker-compose-up.png)

3. **Acceder a Odoo:**
- Abrir el navegador y navegar a `http://localhost:8069`
- Crear la base de datos
![](img/odoo-creation.png)
- Crear los usuarios:
![](img/odoo-create-user.png)
![](img/odoo-users.png)
- Añadir los módulos:
  - Calendario:
  ![](img/modules/odoo-module-calendar.png)
  - CRM:
  ![](img/modules/odoo-module-crm.png)
  - Lista de tareas:
  ![](img/modules/odoo-module-todo.png)
  - Web:
  ![](img/modules/odoo-module-website.png)

## Comandos de Gestión

- **Detener servicios:** `docker compose down`
- **Ver logs:** `docker compose logs`
- **Actualizar imágenes:** `docker compose pull && docker compose up -d`
