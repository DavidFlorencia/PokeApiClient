package com.dflorencia.pokeapiclient.data.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val abilities: List<PokemonAbilitie>,
    val sprites: Sprites?
){
    data class Sprites(
        @SerializedName("front_default")
        val frontDefault: String?,
        @SerializedName("back_default")
        val backDefault: String?
    )

    data class PokemonType(
        val type: Type
    )

    data class Type(
        val name: String
    )

    data class PokemonAbilitie(
        val ability: Ability
    )

    data class Ability(
        val name: String
    )
}