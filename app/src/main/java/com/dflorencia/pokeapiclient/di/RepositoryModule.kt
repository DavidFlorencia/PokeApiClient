package com.dflorencia.pokeapiclient.di

import com.dflorencia.pokeapiclient.data.api.PokeApiService
import com.dflorencia.pokeapiclient.data.repository.Repository
import com.dflorencia.pokeapiclient.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * modulo para inyeccion de dependencias del repositorio
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    /**
     * numero de elementos que se cargaran por página
     */
    private val PAGIN_SIZE = 35

    @Singleton
    @Provides
    fun provideRepository(
        pokeApiService: PokeApiService
    ): Repository {
        return RepositoryImpl(PAGIN_SIZE,pokeApiService)
    }
}