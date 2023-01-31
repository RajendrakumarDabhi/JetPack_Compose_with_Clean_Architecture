package com.example.composecleanarchitectureweatherapp.helpers

import com.example.composecleanarchitectureweatherapp.domain.BaseResponse
import retrofit2.Response

class NetworkUtils {
    companion object {
        fun <T> getFormatedResponse(resp:Response<T>): BaseResponse<T> {
            return if(resp.isSuccessful){
                BaseResponse.Success(resp.body())
            }else{
                BaseResponse.Error("Api Error:Something went wrong")
            }
        }
    }
}