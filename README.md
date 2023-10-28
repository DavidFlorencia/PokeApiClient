# PokeApiClient

[PokeApiClient.apk](/PokeApiClient-debug.apk)

Esta es una aplicación de muestra que consume el api https://pokeapi.co/api/v2/

Esta desarrollada con la arquitectura de software MVVM + Clean: se divide en 3 capas presentación,
dominio y data; que a su vez cuentan con las subcapas modelo, vista y modelo de vista. 
Se utilizan 2 casos de uso, uno para la consulta de pokemon en forma de lista y otro para el consumo individual.

En este proyecto se utilizan las siguientes librerías:

* Navigation Graph y SafeArgs: Para la navegación y transferencia de datos entre fragments.
* ViewBinding: Para el enlace entre los elementos de diseño y el código ejecutable.
* ViewModel: Para la implementación del patrón observador.
* Retrofit: Para la comunicación con los servicios web de TvMaze.
* Gson: Para el formateo de los datos obtenidos del API.
* Hilt: Para la inyección de dependencias.
* Glide: Para la carga de imagenes desde internet.

Una característica a destacar de este desarrollo es la implementación de un Recycler View paginado con fuente remota y el filtrado simultaneo de los resultados obtenidos.
