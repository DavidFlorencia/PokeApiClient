package com.dflorencia.pokeapiclient.domain

import com.dflorencia.pokeapiclient.data.repository.Repository

class GetPokemonListUseCaseImpl(val repository: Repository): GetPokemonListUseCase {
    override fun execute() =
        repository.getPokemonList()
}