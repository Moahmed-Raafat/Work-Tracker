package com.example.worktracker.di

import android.annotation.SuppressLint
import com.example.worktracker.common.Constants
import com.example.worktracker.common.ServiceAPI
import com.example.worktracker.contributors.data.repository.ContributorsRepositoryImpl
import com.example.worktracker.contributors.domain.repository.ContributorsRepository
import com.example.worktracker.worktypes.data.repository.WorkTypesRepositoryImpl
import com.example.worktracker.worktypes.domain.repository.WorkTypesRepository
import com.example.worktracker.statuses.data.repository.StatusesRepositoryImpl
import com.example.worktracker.statuses.domain.repository.StatusesRepository
import com.example.worktracker.priorities.data.repository.PrioritiesRepositoryImpl
import com.example.worktracker.priorities.domain.repository.PrioritiesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule
{
    //Networking
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor
    {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(interceptor : HttpLoggingInterceptor): OkHttpClient
    {

        val trustAllCerts = arrayOf<TrustManager>(
            @SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {

                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )

        //disabling sll certificates
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null,trustAllCerts, SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
            .addInterceptor(interceptor = interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier(HostnameVerifier { hostname, session -> return@HostnameVerifier true })
            .build()
    }

    @Provides
    @Singleton
    fun provideServiceAPI(client : OkHttpClient) : ServiceAPI
    {
        val url = Constants.BASE_URL

        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServiceAPI::class.java)

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //repositories

    @Provides
    @Singleton
    fun provideContributorsRepository(serviceAPI: ServiceAPI): ContributorsRepository {
        return ContributorsRepositoryImpl(serviceAPI)
    }

    @Provides
    @Singleton
    fun provideWorkTypesRepository(serviceAPI: ServiceAPI): WorkTypesRepository {
        return WorkTypesRepositoryImpl(serviceAPI)
    }

    @Provides
    @Singleton
    fun provideStatusesRepository(serviceAPI: ServiceAPI): StatusesRepository {
        return StatusesRepositoryImpl(serviceAPI)
    }

    @Provides
    @Singleton
    fun providePrioritiesRepository(serviceAPI: ServiceAPI): PrioritiesRepository {
        return PrioritiesRepositoryImpl(serviceAPI)
    }
}