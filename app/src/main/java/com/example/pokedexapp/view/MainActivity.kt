package com.example.pokedexapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Chronometer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexapp.R
import com.example.pokedexapp.api.PokemonRepository
import com.example.pokedexapp.domain.Pokemon
import com.example.pokedexapp.domain.PokemonType
import com.example.pokedexapp.model.PokemonResult
import com.example.pokedexapp.model.PokemonsApiResult
import com.example.pokedexapp.viewmodel.PokemonViewModel
import com.example.pokedexapp.viewmodel.PokemonViewModelFactory

class MainActivity : AppCompatActivity() {
    //recyclerview como uma variavel / associação
    lateinit var recyclerView: RecyclerView

    //inicialização do viewModel
   private val viewModel by lazy {//by lazy para inicilializar uma variavel que depende de activity
        ViewModelProvider(this, PokemonViewModelFactory())
            .get(PokemonViewModel::class.java)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //carregando a recyclerview
        recyclerView = findViewById(R.id.rvPokemons)

        //thread para carregar pokemons
        viewModel.pokemons.observe(this, Observer {
            loadRecyclerView(it)

        })
    }


    //load recyclerview, se eu ja tiver uma lista de pokemon evita fluxo da api
    private fun loadRecyclerView(pokemons : List<Pokemon?>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PokemonAdapter(pokemons)


    }
}
