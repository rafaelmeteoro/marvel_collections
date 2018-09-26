package br.com.rafael.marvelcollections.di.modules

import br.com.rafael.data.api.Api
import br.com.rafael.marvelcollections.extensions.md5
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
class NetworkModule(private val baseUrl: String,
                    private val publicApiKey: String,
                    private val privateApiKey: String) {

    @Singleton
    @Provides
    fun provideInterceptors(): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()

        val keyInterceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val timestamp = Calendar.getInstance().timeInMillis.toString()

            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", publicApiKey)
                    .addQueryParameter("hash", (timestamp + privateApiKey + publicApiKey).md5())
                    .addQueryParameter("ts", timestamp)
                    .build()

            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder.build()

            return@Interceptor chain.proceed(request)
        }

        interceptors.add(keyInterceptor)
        return interceptors
    }

    @Singleton
    @Provides
    fun provideRetrofit(interceptors: ArrayList<Interceptor>): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
        if (!interceptors.isEmpty()) {
            interceptors.forEach { interceptor ->
                clientBuilder.addInterceptor(interceptor)
            }
        }

        val loggin = HttpLoggingInterceptor()
        loggin.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggin)

        return Retrofit.Builder()
                .client(clientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}