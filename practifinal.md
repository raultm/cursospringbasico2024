Ejercicio - Validaciones - Enunciado
1. Proyecto
Podéis crear uno nuevo con el template
Podéis usar con el que hemos estado trabajando durante el curso
2. Definir OpenAPI
Estás desarrollando una API para una biblioteca, se te pide que en el endpoint para registrar
socios/miembros sea obligatorio los campos email y edad, el email es importante para mandar
notificaciones y la edad para poder gestionar los préstamos.
En el archivo OpenAPI usa las funcionalidades que tiene para realizar las validaciones
campos obligatorios
email con formato email
año nacimiento formato fecha YYYY-mm-dd
3. Comprobar que OpenAPI lanza excepciones
Hacer uso del endpoint e ir comprobando si hay que hacer ajustes
Conocer configuracion de generacion OpenAPI
En el punto 6 averiguaremos cómo es el mecanismo.
4. Gestionar Excepción lanzada
Descubrir que excepción lanza, una vez lo sepamos averiguar como podemos gestionar
excepciones en Springboot y dar una respuesta formateada al usuario, al menos indicando los
campos y si es posible la causa.
{
"nacimiento": "no puede ser nulo"
"email": "no tiene formato"
,
}
Ayuda (Solución oculta)
@ControllerAdvice
5. Poner mensajes de error en español
Por defecto los mensajes de las validaciones vienen en inglés, buscar como ponerlos en
español
6. Anotaciones de Validaciones
Encontrar las anotaciones que usamos para hacer funcionar
Las que activan la comprobación de validaciones (Dos conjuntamente)
Para que campo sea obligatorio
Formato Correo