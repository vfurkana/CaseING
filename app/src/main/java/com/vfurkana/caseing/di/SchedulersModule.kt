package com.vfurkana.caseing.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Qualifier


@Module
@InstallIn(ApplicationComponent::class)
object SchedulersModule {

    @Provides
    @IOScheduler
    fun provideIOScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @MainScheduler
    fun provideMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @TrampolineScheduler
    fun provideTrampolineScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainScheduler

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IOScheduler

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TrampolineScheduler