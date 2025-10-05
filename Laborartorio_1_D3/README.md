Laboratorio_1_D3 — YuGiOh Duel
=================================

Breve descripción
-----------------
Proyecto de laboratorio en Java que muestra una pequeña interfaz con Swing para jugar rondas entre un jugador y un bot. Las cartas se obtienen desde `YugiohApiClient` y la lógica de juego está en `GameController`.

Cómo ejecutar (Windows)
--------------------------------
Lo más cómodo es abrir el proyecto en tu IDE (IntelliJ IDEA o Eclipse) y ejecutar la clase `com.yugioh.view.Init` (método `main`).

Notas rápidas
- El JAR `lib/json-20230227.jar` es necesario en el classpath; si no lo tienes, ponlo o ajusta el classpath. 
- `YugiohApiClient` hace llamadas para traer cartas; si la API falla, la app puede lanzar excepciones. Para pruebas sin red, puedes modificar temporalmente `GameController` para devolver cartas fijas o crear un stub.

Diseño (explicación corta)
--------------------------
La estructura es simple: la lógica del juego está en `GameController` (reparte cartas, resuelve una ronda y lleva la puntuación). `model.Card` es la entidad que representa una carta, y `api.YugiohApiClient` encapsula la obtención de datos externos. La vista está en `com.yugioh.view.Init` y usa Swing para mostrar cartas, permitir selecciones (ATK/DEF) y mostrar un registro de lo que pasa.

Separando así la lógica de la vista resulta fácil cambiar cómo funcionan las reglas (en `GameController`) sin tocar la interfaz. Si quieres probar cambios rápidos, es cómodo sustituir el `YugiohApiClient` por datos locales para no depender de la red.

Problemas comunes y soluciones
-----------------------------
- ClassNotFoundException / NoClassDefFoundError: revisa que el JAR esté en `lib/` y que uses el classpath correcto.
- Errores al cargar imágenes: revisa la conexión y que las URLs sean válidas. Si trabajas sin internet, usa cartas locales.

Créditos
--------
Trabajo de laboratorio — hecho como ejercicio para la asignatura.

Fecha: 2025-10-05
