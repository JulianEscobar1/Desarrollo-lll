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
- `YugiohApiClient` hace llamadas para traer cartas; si la API falla, la app puede lanzar excepciones.
- 
Diseño (explicación corta)
--------------------------
La estructura es simple: la lógica del juego está en `GameController` (reparte cartas, resuelve una ronda y lleva la puntuación). `model.Card` es la entidad que representa una carta, y `api.YugiohApiClient` encapsula la obtención de datos externos. La vista está en `com.yugioh.view.Init` y usa Swing para mostrar cartas, permitir selecciones (ATK/DEF) y mostrar un registro de lo que pasa.

Integrantes:
- Angie Juliana Jaramillo Salazar - 202160376-2724
- Julian Andres Escobar Tangarife - 202160362-2724

Capturas de pantalla:

- Inicio de la aplicación.
<img width="1021" height="671" alt="image" src="https://github.com/user-attachments/assets/f39afa74-fff4-4113-ab78-ba8887e3dfba" />

- Cartas cargadas. Nota: Tarda alrededor de 10 segundos en cargar las cartas, dado que debe filtrar solo las cartas de tipo 'Monster'.
  <img width="1068" height="640" alt="image" src="https://github.com/user-attachments/assets/10f8724d-a94f-4f99-bafb-62e5bfc06c82" />

- Caso en el que ambos jugadores deciden defender. Se anuncia en el log y se da una ronda más.
  <img width="1069" height="675" alt="image" src="https://github.com/user-attachments/assets/6ca8d8b4-ac06-499a-a01e-6cec85aa32e9" />

- Se anuncia el ganador de la partida.
  <img width="1068" height="697" alt="image" src="https://github.com/user-attachments/assets/a726278d-eafe-46d0-b121-c92d7e85f573" />


  
