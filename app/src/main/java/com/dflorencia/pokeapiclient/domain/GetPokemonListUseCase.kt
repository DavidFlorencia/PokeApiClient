package com.dflorencia.pokeapiclient.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dflorencia.pokeapiclient.data.model.PokemonList
import com.dflorencia.pokeapiclient.data.model.Resource

interface GetPokemonListUseCase {
    fun execute(): LiveData<PagingData<PokemonList.PokemonInfo>>
}