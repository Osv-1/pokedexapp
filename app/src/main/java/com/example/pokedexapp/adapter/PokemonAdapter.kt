package com.example.pokedexapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.Pokemon
import com.example.pokedexapp.utils.TypeColor
import com.example.pokedexapp.utils.TypeSelector

class PokemonAdapter(
    private val items: List<Pokemon?>,
    private val context: Context
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindView(item)
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        fun bindView(item: Pokemon?) = with(itemView) {
            val ivPokemon = findViewById<ImageView>(R.id.ivPokemon)
            val tvNumber = findViewById<TextView>(R.id.tvNumber)
            val tvName = findViewById<TextView>(R.id.tvName)
            val tvType1 = findViewById<TextView>(R.id.tvType1)
            val tvType2 = findViewById<TextView>(R.id.tvType2)

            //TODO: load image with Glide

            item?.let {

                Glide.with(itemView.context).load(it.imageurl).into(ivPokemon)

                TypeColor.typeColor(context, item.types[0].name.capitalize())
                    ?.let { color -> ivPokemon.setBackgroundResource(color) }

                tvNumber.text = "Nº ${item.formattedNumber}"
                tvName.text = item.formattedName.capitalize()
                tvType1.background =
                    TypeSelector.typeSelector(context, item.types[0].name.capitalize())


                if (item.types.size > 1) {
                    tvType2.visibility = View.VISIBLE
                    tvType2.background =
                        TypeSelector.typeSelector(context, item.types[1].name.capitalize())
                } else {
                    tvType2.visibility = View.GONE
                }

            }


        }

    }

}