package com.dflorencia.pokeapiclient.data.model

data class PokemonList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonInfo>
){
    data class PokemonInfo(
        val name: String,
        val url:String
    )
}
