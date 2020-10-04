package co.zemogaapp.di

import android.content.Context
import android.util.Log
import co.zemogaapp.service.APIService
import co.zemogaapp.utils.isOnline
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by diego.urrea on 10/3/2020.
 */
@Module(includes = [AppModule::class])
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Provides
    fun provideAPI(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)

    @Provides
    fun provideCache(cacheFile: File): Cache = Cache(cacheFile, 10 * 1024 * 1024)

    @Provides
    fun provideFile(context: Context): File = File(context.cacheDir, "okhttp_cache")

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.v("LoggingInterceptor", message)
            }
        })

        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor, cache: Cache, context: Context): OkHttpClient {
        val onlineInterceptor = Interceptor { chain ->
            var request = chain.request()
            request = if (isOnline(context)) {
                request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build()
            } else {
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                    .build()
            }
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(onlineInterceptor)
            .build()
    }

}
