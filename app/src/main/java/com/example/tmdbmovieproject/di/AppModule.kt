package com.example.tmdbmovieproject.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tmdbmovieproject.data.api.MovieApi
import com.example.tmdbmovieproject.data.api.Urls
import com.example.tmdbmovieproject.data.repo.MovieRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideOkHttpClient(
    ): OkHttpClient {
        val headerInterceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization",Urls.ACCESS_TOKEN)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor).also {
                it.addInterceptor(
                    HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    ))
            }
            .connectTimeout(Duration.ofSeconds(Urls.TIME_OUT))
            .readTimeout(Duration.ofSeconds(Urls.TIME_OUT))
            .writeTimeout(Duration.ofSeconds(Urls.TIME_OUT))
            .retryOnConnectionFailure(true)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getMovieApi(retrofit: Retrofit):MovieApi{
        return retrofit.create(MovieApi::class.java)
    }
    @Provides
    @Singleton
    fun getTaskRepo(movieApi: MovieApi):MovieRepo{
        return MovieRepo(movieApi)
    }
//


}
