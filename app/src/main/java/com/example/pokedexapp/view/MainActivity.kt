package com.example.pokedexapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapp.R
import com.example.pokedexapp.api.PokemonRepository
import com.example.pokedexapp.domain.Pokemon
import com.example.pokedexapp.domain.PokemonType
import com.example.pokedexapp.model.PokemonResult
import com.example.pokedexapp.model.PokemonsApiResult

class MainActivity : AppCompatActivity() {
    //recyclerview como uma variavel / associação
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //carregando a recyclerview
        recyclerView = findViewById(R.id.rvPokemons)

//        val charmander = Pokemon(
//            "https://assets.pokemon.com/assets/cms2/img/pokedex/full/004.png",
//            4,
//            "Charmander",
//            listOf(
//                PokemonType("Fire")
//            )
//        )
//        val pokemons = listOf(charmander, charmander, charmander, charmander, charmander)

        //thread para carregar pokemons
        Thread(Runnable {
            loadPokemons()

        }).start()

    }

    private fun loadPokemons() {
        //fazendo a requisição
        val pokemonsApiResult = PokemonRepository.listPokemons()

        //carregando os resultados
        pokemonsApiResult?.results?.let {
            val pokemons: List<Pokemon?> = it.map { pokemonResult ->

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


            }


            val layoutManager = LinearLayoutManager(this)

            //.post para ser executado dentro da main thread
            recyclerView.post {
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = PokemonAdapter(pokemons)

            }

        }


    }


}