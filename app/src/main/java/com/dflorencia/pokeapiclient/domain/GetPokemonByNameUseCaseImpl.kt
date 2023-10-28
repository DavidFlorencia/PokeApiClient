package com.dflorencia.pokeapiclient.domain

import com.dflorencia.pokeapiclient.data.repository.Repository

class GetPokemonByNameCaseImpl(val repository: Repository): GetPokemonByNameUseCase {
    override suspend fun execute(name: String) = repository.getPokemon(name)
}