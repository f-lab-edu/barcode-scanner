package com.jaewchoi.barcodescanner.di

import android.content.Context
import androidx.room.Room
import com.jaewchoi.barcodescanner.data.repository.ScanHistoryRepository
import com.jaewchoi.barcodescanner.data.source.local.AppDatabase
import com.jaewchoi.barcodescanner.data.source.local.ScanHistoryDao
import com.jaewchoi.barcodescanner.domain.usecase.ClearHistoryUseCase
import com.jaewchoi.barcodescanner.domain.usecase.DeleteHistoryUseCase
import com.jaewchoi.barcodescanner.domain.usecase.FetchHistoryUseCase
import com.jaewchoi.barcodescanner.domain.usecase.SaveHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideScanHistoryDao(database: AppDatabase): ScanHistoryDao {
        return database.scanHistoryDao()
    }

    @Provides
    fun provideScanHistoryRepository(dao: ScanHistoryDao): ScanHistoryRepository {
        return ScanHistoryRepository(dao)
    }

    @Provides
    fun provideSaveHistoryUseCase(repository: ScanHistoryRepository) =
        SaveHistoryUseCase(repository)

    @Provides
    fun provideFetchHistoryUseCase(repository: ScanHistoryRepository) =
        FetchHistoryUseCase(repository)

    @Provides
    fun provideDeleteHistoryUseCase(repository: ScanHistoryRepository) =
        DeleteHistoryUseCase(repository)

    @Provides
    fun provideClearHistoryUseCase(repository: ScanHistoryRepository) =
        ClearHistoryUseCase(repository)
}