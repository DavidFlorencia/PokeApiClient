package com.dflorencia.pokeapiclient.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dflorencia.pokeapiclient.data.model.PokemonList.PokemonInfo
import com.dflorencia.pokeapiclient.databinding.ItemPokemonBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonAdapter @Inject constructor(): PagingDataAdapter<PokemonInfo,PokemonAdapter.PokemonViewHolder>(
    object : DiffUtil.ItemCallback<PokemonInfo>(){
        override fun areItemsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem == newItem
        }
    }
) {
    private var onItemClickListener :((PokemonInfo)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonInfo = getItem(position)
        pokemonInfo?.let { holder.bind(it) }
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(pokemon: PokemonInfo){
            binding.txtName.text = pokemon.name.replaceFirstChar {
                it.uppercase()
            }
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(pokemon)
                }
            }
        }
    }

    fun setOnItemClickListener(listener : (PokemonInfo)->Unit){
        onItemClickListener = listener
    }
}