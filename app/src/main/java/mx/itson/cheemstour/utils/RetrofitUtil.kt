package mx.itson.cheemstour.utils

import com.google.gson.GsonBuilder
import mx.itson.cheemstour.interfaces.CheemsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtil {

    fun getApi() : CheemsAPI{
        val gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder().baseUrl("http://10.232.19.123:5000/").addConverterFactory(
            GsonConverterFactory.create(gson)).build()

        return retrofit.create(CheemsAPI::class.java)
    }

}