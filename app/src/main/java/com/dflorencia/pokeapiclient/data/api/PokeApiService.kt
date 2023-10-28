package com.dflorencia.pokeapiclient.data.api

import com.dflorencia.pokeapiclient.data.model.Pokemon
import com.dflorencia.pokeapiclient.data.model.PokemonList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Response<Pokemon>
}