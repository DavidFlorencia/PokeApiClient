package com.dflorencia.pokeapiclient.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dflorencia.pokeapiclient.data.api.PokeApiService
import com.dflorencia.pokeapiclient.data.model.PokemonList
import retrofit2.HttpException
import java.io.IOException

class PokemonPagingSource(
    private val paginSize: Int,
    private val pokeApiService: PokeApiService
):
    PagingSource<Int, PokemonList.PokemonInfo>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonList.PokemonInfo> {
        val page = params.key ?: 0

        return try {
            val response = pokeApiService.getPokemonList(offset = paginSize * page, limit = paginSize)
            LoadResult.Page(
                response.results,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonList.PokemonInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}