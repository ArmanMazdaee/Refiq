package com.eth.refiq.di

import CoroutineDispatcherProvider
import com.eth.refiq.MainViewModel
import com.eth.refiq.data.Api
import com.eth.refiq.data.RemotePostRepository
import com.eth.refiq.data.RemoteTopicRepository
import com.eth.refiq.data.SharedPrefLocalDataStorage
import com.eth.refiq.data.Web3JRepository
import com.eth.refiq.domain.LocalDataStorage
import com.eth.refiq.domain.PostRepository
import com.eth.refiq.domain.TopicRepository
import com.eth.refiq.domain.Web3Repository
import com.eth.refiq.ui.searchtopic.SearchTopicViewModel
import com.eth.refiq.ui.topic.TopicViewModel
import com.eth.refiq.ui.wallet.WalletViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<Api> {
        get<Retrofit>().create(Api::class.java)
    }
    single<TopicRepository> { RemoteTopicRepository(get()) }
    single<PostRepository> { RemotePostRepository() }
    single<CoroutineDispatcherProvider> {
        AppDispatcherProvider()
    }
    single<LocalDataStorage> { SharedPrefLocalDataStorage(androidApplication()) }

    single<Web3Repository> { Web3JRepository(androidApplication(), get(), get()) }


    viewModel {
        MainViewModel()
    }

    viewModel {
        TopicViewModel(get(), get(), get())
    }
    viewModel { SearchTopicViewModel(get(), get()) }

    viewModel { WalletViewModel(get(), get()) }


    single {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .followRedirects(true)
            .addInterceptor(logging)
            .addInterceptor(AuthTokenInterceptor())
            .build()
    }
    single<AuthTokenInterceptor> { AuthTokenInterceptor() }
    single {
        Retrofit.Builder()

            .baseUrl(API_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    single<Gson> {
        GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .create()
    }
}
