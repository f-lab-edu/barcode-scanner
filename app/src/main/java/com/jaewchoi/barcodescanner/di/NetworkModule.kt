package com.jaewchoi.barcodescanner.di

import com.jaewchoi.barcodescanner.data.repository.SheetsRepository
import com.jaewchoi.barcodescanner.data.repository.UserRepository
import com.jaewchoi.barcodescanner.data.source.local.SheetsSettingStorage
import com.jaewchoi.barcodescanner.domain.usecase.FetchGoogleUserUseCase
import com.jaewchoi.barcodescanner.domain.usecase.FetchRecordUseCase
import com.jaewchoi.barcodescanner.data.source.network.RecordApi
import com.jaewchoi.barcodescanner.data.source.network.TokenAuthenticator
import com.jaewchoi.barcodescanner.data.source.network.UserInfoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideProductApi(): RecordApi {
        return Retrofit.Builder()
            .baseUrl(SHEET_RECORD_URI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecordApi::class.java)
    }

    @Provides
    fun provideSheetsRepository(
        api: RecordApi,
        sheetsStorage: SheetsSettingStorage,
    ): SheetsRepository {
        return SheetsRepository(api, sheetsStorage)
    }

    @Provides
    fun provideFetchRecordUseCase(repository: SheetsRepository) =
        FetchRecordUseCase(repository)

    private const val SHEET_RECORD_URI = "https://searchrecord-pg73pmoduq-uc.a.run.app/"
}
