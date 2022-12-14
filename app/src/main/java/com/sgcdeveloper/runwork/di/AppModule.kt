package com.sgcdeveloper.runwork.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.sgcdeveloper.runwork.core.DefaultDispatchersProvider
import com.sgcdeveloper.runwork.core.DispatchersProvider
import com.sgcdeveloper.runwork.data.AppDatabase
import com.sgcdeveloper.runwork.data.repository.AppRepositoryImpl
import com.sgcdeveloper.runwork.data.repository.SharedPreferencesStore
import com.sgcdeveloper.runwork.data.repository.WorkoutRepositoryImpl
import com.sgcdeveloper.runwork.domain.repository.AppRepository
import com.sgcdeveloper.runwork.domain.repository.WorkoutRepository
import com.sgcdeveloper.runwork.presentation.navigation.NavigationEventsHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatchersProvider {
        return DefaultDispatchersProvider()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWorkoutRepository(appDatabase: AppDatabase): WorkoutRepository {
        return WorkoutRepositoryImpl(appDatabase)
    }

    @Singleton
    @Provides
    fun provideAppRepository(sharedPreferencesStore: SharedPreferencesStore): AppRepository {
        return AppRepositoryImpl(sharedPreferencesStore)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferencesStore {
        return SharedPreferencesStore(context)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideNavigationEventsHandler(@ApplicationContext context: Context): NavigationEventsHandler {
        return NavigationEventsHandler(context)
    }
}