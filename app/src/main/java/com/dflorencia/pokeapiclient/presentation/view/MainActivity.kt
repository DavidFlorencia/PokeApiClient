package com.dflorencia.pokeapiclient.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dflorencia.pokeapiclient.R
import com.dflorencia.pokeapiclient.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}