package com.example.pokedexapp.domain

data class Pokemon(
    val number: Int,
    val name: String,
    val types: List<PokemonType>
) {

    val formattedName = name.lowercase()
    val formattedNumber = number.toString().padStart(3, '0')

    val imageurl = "https://img.pokemondb.net/sprites/lets-go-pikachu-eevee/normal/$formattedName.png"

}

