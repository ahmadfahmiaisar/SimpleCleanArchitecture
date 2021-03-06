package com.example.preworkshopdsc.data.repository

import com.example.preworkshopdsc.data.dispatcher.DispatcherProvider
import com.example.preworkshopdsc.data.mapper.MovieMapper
import com.example.preworkshopdsc.data.mapper.TvOnTheAirMapper
import com.example.preworkshopdsc.data.source.MovieRemoteDataSource
import com.example.preworkshopdsc.data.vo.Result
import com.example.preworkshopdsc.domain.entity.NowPlaying
import com.example.preworkshopdsc.domain.entity.TvOnTheAir
import com.example.preworkshopdsc.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val tvOnTheAirMapper: TvOnTheAirMapper
) : MovieRepository {
    override suspend fun getMovieNowPlaying(): Result<List<NowPlaying>> {
        val apiResult = remoteDataSource.getMovieNowPlaying(dispatcher.io)
        return when (apiResult) {
            is Result.Success -> Result.Success(movieMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }

    override suspend fun getTvOnTheAir(): Result<List<TvOnTheAir>> {
        val apiResult = remoteDataSource.getTvOnTheAir(dispatcher.io)
        return when (apiResult) {
            is Result.Success -> Result.Success(tvOnTheAirMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }
}