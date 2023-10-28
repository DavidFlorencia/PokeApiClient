package com.dflorencia.pokeapiclient.domain

import com.dflorencia.pokeapiclient.data.model.Pokemon
import com.dflorencia.pokeapiclient.data.model.Resource

interface GetPokemonByNameUseCase {
    suspend fun execute(name: String): Resource<Pokemon>
}