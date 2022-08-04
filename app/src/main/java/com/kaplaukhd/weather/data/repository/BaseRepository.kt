package com.kaplaukhd.weather.data.repository

import com.kaplaukhd.weather.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository() {
    suspend fun <T> apiCall(request: suspend () -> Response<T>): Result<T> =
        withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = request()
                if (response.isSuccessful) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error("error")
                }
            } catch (e: Exception) {
                Result.Error(e.toString())
            } catch (e: IOException) {
                Result.Error(e.toString())
            } catch (e: HttpException) {
                Result.Error(e.toString())
            }
        }
}