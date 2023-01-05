
# MICROSERVICIO PATRONES DE TOLERANCIA AL FALLO

Este servicio tiene el ejemplo de configuración con la librería resilience4j para springboot para la implementación 
de los patrones de control contra fallos.

* Retry
* Circuit breaker
* Rate limit
* Bulkhead

# CONTENERIZACIÓN DEL SERVICIO

Este servicio se encuentra dockerizado por lo cual se desea crear una imagen para correr un contenedor se debe ejecutar
los siguiente comando:

* docker build -t "faultolerancepattenrs" .
* docker run -d -p 9000:8080 faultolerancepattenrs

Nota: 9000 es una sugerencia, puede usarse el puerto deseado.

