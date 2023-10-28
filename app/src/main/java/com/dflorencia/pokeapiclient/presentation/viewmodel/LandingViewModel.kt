package com.dflorencia.pokeapiclient.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.dflorencia.pokeapiclient.data.model.PokemonList.PokemonInfo
import com.dflorencia.pokeapiclient.domain.GetPokemonListUseCase
import com.dflorencia.pokeapiclient.presentation.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    getPokemonListUseCase: GetPokemonListUseCase
): ViewModel() {

    /**
     * almacena data original por posible filtrado
     */
    private val originalData = getPokemonListUseCase
        .execute()
        .cachedIn(viewModelScope)

    private val queryLiveData = MutableLiveData<String>()

    /**
     * se crea e inicializa mediador para observar los cambios
     * cuando se cargan nuevos pokemon o se realiza una busqueda
     */
    val mediatorLiveData = MediatorLiveData<PagingData<PokemonInfo>?>()
    init {
        mediatorLiveData.addSource(originalData){ originalData ->
            val query = queryLiveData.value

            val filteredData = if (query.isNullOrBlank()) {
                originalData
            } else {
                filterPagingData(originalData, query)
            }
            mediatorLiveData.value = filteredData
        }

        mediatorLiveData.addSource(queryLiveData) { query ->
            val pagingData = originalData.value

            val filteredData = if (query.isNullOrBlank()) {
                pagingData
            } else {
                pagingData?.let { filterPagingData(it, query) }
            }
            mediatorLiveData.value = filteredData
        }
    }

    private fun filterPagingData(original: PagingData<PokemonInfo>, query: String): PagingData<PokemonInfo> {
        return original.filter { item ->
            item.name.contains(query, ignoreCase = true)
        }
    }

    /**
     * funcion que se debe ejecutar cuando se realiza una busqueda
     */
    fun search(query: String){
        queryLiveData.value = query
    }
}