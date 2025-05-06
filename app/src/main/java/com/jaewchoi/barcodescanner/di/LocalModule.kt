package com.jaewchoi.barcodescanner.di

import android.content.Context
import com.jaewchoi.barcodescanner.data.repository.SettingRepository
import com.jaewchoi.barcodescanner.data.source.local.SheetsSettingStorage
import com.jaewchoi.barcodescanner.domain.usecase.ClearSheetsSettingsUseCase
import com.jaewchoi.barcodescanner.domain.usecase.FetchSheetsSettingsUseCase
import com.jaewchoi.barcodescanner.domain.usecase.SaveSheetsSettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    fun provideSheetsSettingStorage(@ApplicationContext context: Context) =
        SheetsSettingStorage(context)

    @Provides
    fun provideSettingsRepository(storage: SheetsSettingStorage) =
        SettingRepository(storage)

    @Provides
    fun provideSaveSheetsSettingsUseCase(repository: SettingRepository) =
        SaveSheetsSettingsUseCase(repository)

    @Provides
    fun provideFetchSheetsSettingsUseCase(repository: SettingRepository) =
        FetchSheetsSettingsUseCase(repository)

    @Provides
    fun provideClearSheetsSettingsUseCase(repository: SettingRepository) =
        ClearSheetsSettingsUseCase(repository)
}