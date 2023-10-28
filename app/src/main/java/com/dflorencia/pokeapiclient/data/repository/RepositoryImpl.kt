package com.dflorencia.pokeapiclient.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dflorencia.pokeapiclient.data.api.PokeApiService
import com.dflorencia.pokeapiclient.data.model.PokemonList
import com.dflorencia.pokeapiclient.data.model.Resource
import retrofit2.Response

class RepositoryImpl(
    private val pageSize: Int,
    private val pokeApiService: PokeApiService
): Repository {
    override suspend fun getPokemon(name: String) =
        responseToResource(pokeApiService.getPokemon(name))

    override fun getPokemonList(): LiveData<PagingData<PokemonList.PokemonInfo>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = true),
            pagingSourceFactory = { PokemonPagingSource(pageSize,pokeApiService) }
        ).liveData
    }

    /**
     * metodo que convierte respuesta del servicio en modelo de datos
     */
    fun <T> responseToResource(response: Response<T>): Resource<T> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}