package com.babakmhz.githubuserfindercompose.di

import com.babakmhz.githubuserfindercompose.BuildConfig
import com.babakmhz.githubuserfindercompose.data.RepositoryHelper
import com.babakmhz.githubuserfindercompose.data.RepositoryImpl
import com.babakmhz.githubuserfindercompose.data.network.ApiHelper
import com.babakmhz.githubuserfindercompose.data.network.ApiHelperImpl
import com.babakmhz.githubuserfindercompose.data.network.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) = if (BuildConfig.DEBUG) {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepositoryHelper(repositoryImpl: RepositoryImpl) : RepositoryHelper = repositoryImpl

    @Provides
    @Singleton
    fun provideApiHelper(apiHelperImpl: ApiHelperImpl):ApiHelper = apiHelperImpl


}