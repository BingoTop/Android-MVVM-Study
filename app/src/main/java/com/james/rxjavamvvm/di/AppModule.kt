package com.james.rxjavamvvm.di

import android.content.Context
import com.james.rxjavamvvm.common.Constants
import com.james.rxjavamvvm.data.GetBookDetailApi
import com.james.rxjavamvvm.data.GetBookListApi
import com.james.rxjavamvvm.domain.use_case.book_detail.GetBookDetailUseCase
import com.james.rxjavamvvm.domain.use_case.book_list.GetBookListUseCase
import com.james.rxjavamvvm.presentation.views.search.SearchAdapter
import com.james.rxjavamvvm.data.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofitInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideGetBookListApi(retrofit: Retrofit):GetBookListApi{
        return retrofit.create(GetBookListApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBookRepository(getBookListApi:GetBookListApi,getBookDetailApi: GetBookDetailApi): MainRepositoryImpl {
        return MainRepositoryImpl(getBookListApi,getBookDetailApi)
    }

    @Singleton
    @Provides
    fun provideBookUseCase(repositoryImpl: MainRepositoryImpl): GetBookListUseCase {
        return GetBookListUseCase(repositoryImpl)
    }

    @Singleton
    @Provides
    fun provideGetBookDetailApi(retrofit:Retrofit):GetBookDetailApi{
        return retrofit.create(GetBookDetailApi::class.java)
    }

    @Provides
    fun provideBookDetailUseCase(repositoryImpl: MainRepositoryImpl):GetBookDetailUseCase{
        return GetBookDetailUseCase(repositoryImpl)
    }


    @Singleton
    @Provides
    @Named("searchAdapter")
    fun provideSearchAdapter(
        @ApplicationContext context: Context
    ): SearchAdapter = SearchAdapter(context)

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context) = context



}