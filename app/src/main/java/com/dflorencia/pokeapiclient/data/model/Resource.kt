package com.dflorencia.pokeapiclient.data.model

/**
 * clase utilizada para validar estado de respuesta de solicitud a servicio web
 * @param data modelo de datos T que contiene la información que retorna el consumo
 * cuando es exitoso
 * @param message string que contiene mensaje de error proporcionado
 * cuando el consumo falla
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
