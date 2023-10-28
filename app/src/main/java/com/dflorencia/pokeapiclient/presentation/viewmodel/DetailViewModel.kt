package com.dflorencia.pokemon.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dflorencia.pokeapiclient.data.model.Pokemon
import com.dflorencia.pokeapiclient.data.model.Resource
import com.dflorencia.pokeapiclient.domain.GetPokemonByNameUseCase
import com.dflorencia.pokeapiclient.presentation.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val utils: Utils,
    private val getPokemonByNameUseCase: GetPokemonByNameUseCase
): ViewModel() {
    /**
     * liva data que contiene consulta a pokemon seleccionado
     */
    private val _pokemon = MutableLiveData<Resource<Pokemon>>()
    val pokemon: LiveData<Resource<Pokemon>>
        get() = _pokemon

    /**
     * función que invoca el caso de uso para obtener la información del pokemon seleccionado
     */
    fun getPokemonByName(name: String) = viewModelScope.launch {
        _pokemon.postValue(Resource.Loading())
        try {
            if (utils.isNetworkAvailable()) {
                val response = getPokemonByNameUseCase.execute(name)
                _pokemon.postValue(response)
            } else {
                _pokemon.postValue(Resource.Error("No internet connection"))
            }
        }catch(e: Exception){
            _pokemon.postValue(Resource.Error(e.message.toString()))
        }
    }
}