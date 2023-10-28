package com.dflorencia.pokeapiclient.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dflorencia.pokeapiclient.R
import com.dflorencia.pokeapiclient.data.model.Pokemon
import com.dflorencia.pokeapiclient.data.model.Resource
import com.dflorencia.pokeapiclient.databinding.FragmentDetailBinding
import com.dflorencia.pokemon.presentation.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        loadData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        attachObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadData() {
        val args = DetailFragmentArgs.fromBundle(requireArguments())
        viewModel.getPokemonByName(args.pokemonName)
    }

    private fun attachObservers() {
        lifecycleScope.launch {
            viewModel.pokemon.observe(viewLifecycleOwner, Observer { response ->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {pokemon ->
                            updateUI(pokemon)
                        }
                    }
                    is Resource.Error -> {
                        showErrorScreen()
                        response.message?.let {
                            Snackbar
                                .make(
                                    binding.root,
                                    "An error occurred : $it",
                                    Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            })
        }
    }

    private fun updateUI(pokemon: Pokemon) {
        binding.apply {
            imgSpriteFront.let {
                Glide.with(it.context)
                    .load(pokemon.sprites?.frontDefault)
                    .into(it)
            }

            imgSpriteBack.let {
                Glide.with(it.context)
                    .load(pokemon.sprites?.backDefault)
                    .into(it)
            }

            txtName.text = getString(
                R.string.name_format,
                pokemon.id.toString(),
                pokemon.name.replaceFirstChar { it.uppercase() }
            )

            /**
             * el poke api devuelve la altura en decimetros,
             * se divide entre 10 para mostrar en m
             */
            txtHeight.text = getString(
                R.string.height_format,
                (pokemon.height/10.0).toString()
            )

            /**
             * el poke api devuelve el peso en hectogramos,
             * se divide entre 10 para mostrar en gramos
             */
            txtWeight.text = getString(
                R.string.weight_format,
                (pokemon.weight/10.0).toString()
            )

            /**
             * unir tipos en string separado por comas
             */
            txtTypes.text = pokemon.types.joinToString(", ") {
                it.type.name
            }

            /**
             * unir habilidades en string separado por comas
             */
            txtAbilities.text = pokemon.abilities.joinToString(", ") {
                it.ability.name
            }
        }
    }

    private fun showErrorScreen() {
        binding.statusContainer.isGone = false
        binding.ivProgressBar.setImageResource(R.drawable.ic_connection_error)
    }

    private fun showProgressBar() {
        binding.statusContainer.isGone = false
    }

    private fun hideProgressBar() {
        binding.statusContainer.isGone = true
    }
}