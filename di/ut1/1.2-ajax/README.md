# WhatsApp 2 — Mini chat en PHP + jQuery
*Sergio de Santiago Redondo - 3WEM*

Resumen
-------
Proyecto de ejemplo que implementa un chat sencillo usando PHP en backend (API REST básica que lee/escribe JSON en disco) y jQuery en frontend. La app almacena mensajes por sala en ficheros JSON dentro de la carpeta `messages/` (excluida del repositorio).

Estructura principal
--------------------
- [public/index.html](public/index.html) — Interfaz web cliente.
- [public/static/scripts/client.js](public/static/scripts/client.js) — Lógica cliente (prompt usuario/sala, envío y recepción de mensajes). Contiene funciones como [`sendMessage`](public/static/scripts/client.js), [`getCurrentTimestamp`](public/static/scripts/client.js) y [`getCurrentTimestampSeconds`](public/static/scripts/client.js).
- [public/static/style/messages.css](public/static/style/messages.css) — Estilos de la UI.
- [api/postMessage.php](api/postMessage.php) — API para publicar mensajes (recibe POST y guarda en JSON).
- [api/getMessages.php](api/getMessages.php) — API para obtener mensajes nuevos desde un timestamp.
- [api/deleteMessage.php](api/deleteMessage.php) — API para marcar mensajes como borrados.
- [api/editMessage.php](api/editMessage.php) — API para editar mensajes.
- [messages/](messages/) — Carpeta donde se almacenan los JSON por sala (almacenamiento local). Está en `.gitignore` ([.gitignore](.gitignore)).
- [routes/router.php](routes/router.php) — Router usado por el servidor PHP embebido para exponer las rutas `/api/*`.
- [Makefile](Makefile) — Tarea `server` para levantar el servidor PHP embebido apuntando al directorio `public/`.

Flujo de datos
--------------
1. El cliente pide al usuario la sala y nombre de usuario (ver [public/static/scripts/client.js](public/static/scripts/client.js)).
2. Al enviar un mensaje se llama a [`sendMessage`](public/static/scripts/client.js) que hace un POST a [api/postMessage.php](api/postMessage.php) con: `chatRoom`, `username`, `message`, `timestamp`.
3. [api/postMessage.php](api/postMessage.php) crea/abre el fichero correspondiente en `messages/<chatRoom>-messages.json`, añade un objeto mensaje con `id` (generado por `uniqid()`), `username`, `message`, `timestamp`, y lo escribe en disco.
4. El cliente consulta periódicamente (setInterval) la API [api/getMessages.php](api/getMessages.php) para obtener mensajes nuevos desde un `timestamp` y actualiza la vista (ver [public/static/scripts/client.js](public/static/scripts/client.js) para `getMessages`).
5. Ediciones y borrados se realizan mediante [api/editMessage.php](api/editMessage.php) y [api/deleteMessage.php](api/deleteMessage.php) respectivamente; esos endpoints modifican el JSON (marcan `edited_timestamp` o `deleted_timestamp`).

Cómo ejecutar
-------------
- Levantar servidor de desarrollo (requiere permisos de root por el Makefile):
  - Ejecutar: make server
  - Internamente usa: `php --server <IP>:<PORT> -t public routes/router.php` (ver [Makefile](Makefile) y [routes/router.php](routes/router.php)).
- Abrir en el navegador: [http://localhost](http://localhost)

Puntos importantes y limitaciones
---------------------------------
- Almacenamiento: ficheros JSON por sala en [messages/](messages/). La carpeta está ignorada por Git ([.gitignore](.gitignore)).

Archivos y símbolos clave (referencias)
--------------------------------------
- Frontend:
  - [public/index.html](public/index.html)
  - [public/static/scripts/client.js](public/static/scripts/client.js) — funciones: [`sendMessage`](public/static/scripts/client.js), [`getCurrentTimestamp`](public/static/scripts/client.js), [`getCurrentTimestampMilliseconds`](public/static/scripts/client.js), [`getCurrentTimestampSeconds`](public/static/scripts/client.js)
  - [public/static/style/messages.css](public/static/style/messages.css)
- Backend / API:
  - [api/postMessage.php](api/postMessage.php)
  - [api/getMessages.php](api/getMessages.php)
  - [api/editMessage.php](api/editMessage.php)
  - [api/deleteMessage.php](api/deleteMessage.php)
- Infra / util:
  - [routes/router.php](routes/router.php)
  - [Makefile](Makefile)
  - [messages/](messages/) (datos en tiempo de ejecución, gitignored: [.gitignore](.gitignore))
