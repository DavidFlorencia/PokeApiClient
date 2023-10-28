package com.dflorencia.pokeapiclient.di

import com.dflorencia.pokeapiclient.data.repository.Repository
import com.dflorencia.pokeapiclient.domain.GetPokemonByNameCaseImpl
import com.dflorencia.pokeapiclient.domain.GetPokemonByNameUseCase
import com.dflorencia.pokeapiclient.domain.GetPokemonListUseCase
import com.dflorencia.pokeapiclient.domain.GetPokemonListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * modulo para inyeccion de dependencias de los casos de uso
 */
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetPokemonList(repository: Repository): GetPokemonListUseCase {
        return GetPokemonListUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideGetPokemonByNameUseCase(repository: Repository): GetPokemonByNameUseCase {
        return GetPokemonByNameCaseImpl(repository)
    }
}