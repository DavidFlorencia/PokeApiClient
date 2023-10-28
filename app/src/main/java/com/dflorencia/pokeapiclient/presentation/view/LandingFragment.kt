package com.dflorencia.pokeapiclient.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dflorencia.pokeapiclient.R
import com.dflorencia.pokeapiclient.databinding.FragmentLandingBinding
import com.dflorencia.pokeapiclient.presentation.Utils
import com.dflorencia.pokeapiclient.presentation.adapter.PokemonAdapter
import com.dflorencia.pokeapiclient.presentation.viewmodel.LandingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LandingFragment : Fragment() {

    @Inject
    lateinit var adapter: PokemonAdapter

    @Inject
    lateinit var utils: Utils

    private lateinit var binding: FragmentLandingBinding
    private val viewModel: LandingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupListener()
        attachObservers()
    }

    private fun initViews(){
        binding.rvPokemon.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
            adapter = this@LandingFragment.adapter
        }
    }

    private fun setupListener() {
        binding.apply {
            toggleButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
                if (isChecked){
                    when (checkedId){
                        R.id.showListView -> {
                            rvPokemon.layoutManager =
                                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                        }
                        R.id.showGridView -> {
                            rvPokemon.layoutManager =
                                GridLayoutManager(activity, 2)
                        }
                    }
                }
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    query?.let {
                        viewModel.search(it)
                    }
                    return true
                }
            })
        }

        adapter.setOnItemClickListener {
            nextScreen(it.name)
        }
    }

    private fun attachObservers() {
        /**
         * observador sobre listas de pokemons se
         * actualiza cada que se carga una página extra
         * o se aplica un filtro
         */
        lifecycleScope.launch {
            viewModel.mediatorLiveData.observe(viewLifecycleOwner, Observer {
                lifecycleScope.launch {
                    it?.let { result ->
                        adapter.submitData(result)
                    }
                }
            })
        }
    }

    private fun nextScreen(pokemonName: String) {
        findNavController().navigate(
            LandingFragmentDirections.actionLandingFragmentToDetailFragment(pokemonName)
        )
    }

    override fun onResume() {
        notNetworkScreen(!utils.isNetworkAvailable())
        super.onResume()
    }

    private fun notNetworkScreen(show: Boolean) {
        binding.ivProgressBar.isGone = !show
        binding.container.isGone = show
    }
}