package com.dflorencia.pokeapiclient.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dflorencia.pokeapiclient.data.model.Pokemon
import com.dflorencia.pokeapiclient.data.model.PokemonList.PokemonInfo
import com.dflorencia.pokeapiclient.data.model.Resource

interface Repository {
    fun getPokemonList(): LiveData<PagingData<PokemonInfo>>
    suspend fun getPokemon(name: String): Resource<Pokemon>
}