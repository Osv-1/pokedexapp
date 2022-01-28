package com.example.pokedexapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedexapp.api.PokemonRepository
import com.example.pokedexapp.domain.Pokemon

class PokemonViewModel : ViewModel() {

    var pokemons = MutableLiveData<List<Pokemon?>>()

    init {
        Thread(Runnable {
            loadPokemons()
        }).start()

        loadPokemons()

    }

    private fun loadPokemons() {
        //fazendo a requisição
        val pokemonsApiResult = PokemonRepository.listPokemons()

        //carregando os resultados
        pokemonsApiResult?.results?.let {
            pokemons.postValue(it.map { pokemonResult ->

                //pegando numero do pokemon
                val number =
                    pokemonResult.url.replace("https://pokeapi.co/api/v2/pokemon", "")
                        .replace("/", "").toInt()


                val pokemonApiResult = PokemonRepository.getPokemon(number)

                pokemonApiResult?.let {

                    Pokemon(
                        pokemonApiResult.id,
                        pokemonApiResult.name,
                        pokemonApiResult.types.map { type ->
                            type.type
                        }
                    )


                }


            })


        }

    }

}